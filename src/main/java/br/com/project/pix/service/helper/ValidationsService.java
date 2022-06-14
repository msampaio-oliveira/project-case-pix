package br.com.project.pix.service.helper;

import br.com.project.pix.dto.projection.PixLimitMaxKeyValueProjection;
import br.com.project.pix.exception.validations.AccountExceedValueNumberException;
import br.com.project.pix.exception.validations.AccountHolderLastNameException;
import br.com.project.pix.exception.validations.AccountHolderNameException;
import br.com.project.pix.exception.validations.AccountTypeException;
import br.com.project.pix.exception.validations.AgencyCannotContainCharacterException;
import br.com.project.pix.exception.validations.AgencyExceedValueNumberException;
import br.com.project.pix.exception.validations.InvalidCNPJException;
import br.com.project.pix.exception.validations.InvalidCPFException;
import br.com.project.pix.exception.validations.InvalidEmailException;
import br.com.project.pix.exception.validations.InvalidKeyAleatoryException;
import br.com.project.pix.exception.validations.InvalidPhoneException;
import br.com.project.pix.exception.validations.KeyPixUnknownException;
import br.com.project.pix.exception.validations.KeyValueAlreadyExistsException;
import br.com.project.pix.exception.validations.LimitMaxKeyValueLegalPersonException;
import br.com.project.pix.exception.validations.LimitMaxKeyValueNaturalPersonException;
import br.com.project.pix.exception.validations.PersonTypeException;
import br.com.project.pix.model.PixAccountUserDetails;
import br.com.project.pix.repository.PixAccountUserDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static br.com.project.pix.model.TypeKeyAccepted.ALEATORIO;
import static br.com.project.pix.model.TypeKeyAccepted.CELULAR;
import static br.com.project.pix.model.TypeKeyAccepted.CPF;
import static br.com.project.pix.model.TypeKeyAccepted.CPNJ;
import static br.com.project.pix.model.TypeKeyAccepted.EMAIL;

@Slf4j
@Configuration
public class ValidationsService {

    private static final String PERSON_TYPE_REGEX = "([f-jF-J])$";

    private static final String KEY_TYPE_EMAIL_REGEX =
            "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                    + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    private static final String KEY_TYPE_PHONE_REGEX = "([+][0-9])([0-9]{2,3})?([0-9]{2})([0-9]{4,5})([0-9]{4})$";

    private static final String LEGAL_PERSON = "J";

    private static final String NATURAL_PERSON = "F";

    private static final Integer MAX_CHARACTER_ACCOUNT_HOLDER_NAME = 30;

    private static final Integer MAX_CHARACTER_ACCOUNT_HOLDER_LAST_NAME = 45;

    @Value("${limit.max.key.value.legal-person}")
    private Integer limitMaxKeyValueLegalPerson;

    @Value("${limit.max.key.value.natural-person}")
    private Integer limitMaxKeyValueNaturalPerson;

    @Value("${account.key.accepted.current}")
    private String accountKeyAcceptedCurrent;

    @Value("${account.key.accepted.savings}")
    private String accountKeyAcceptedSavings;


    private PixAccountUserDetails pixAccountUserDetails;

    private PixAccountUserDetailsRepository pixAccountUserDetailsRepository;

    public void validateCreatePixAccountUserDetails(PixAccountUserDetails pixAccountUserDetails, PixAccountUserDetailsRepository pixAccountUserDetailsRepository) {
        this.pixAccountUserDetails = pixAccountUserDetails;
        this.pixAccountUserDetailsRepository = pixAccountUserDetailsRepository;

        validateKeyPix();
        validateKeyValue();
        validateAgencyNumber();
        validateAccountNumber();
        validatePersonType();
        validateMaxKeyValue();
        validateAccountType();
        validateAccountHolderNameAndAccountHolderLastLame();
    }

    public void validateUpdatePixAccountUserDetails(PixAccountUserDetails pixAccountUserDetails, PixAccountUserDetailsRepository pixAccountUserDetailsRepository) {
        this.pixAccountUserDetails = pixAccountUserDetails;
        this.pixAccountUserDetailsRepository = pixAccountUserDetailsRepository;

        validateAgencyNumber();
        validateAccountNumber();
        validatePersonType();
        validateAccountType();
        validateAccountHolderNameAndAccountHolderLastLame();
    }

    private void validateKeyPix() {
        switch (pixAccountUserDetails.getKeyType().toUpperCase()) {
            case CPF:
                validateCPF();
                break;
            case CPNJ:
                validateCNPJ();
                break;
            case EMAIL:
                validateEmail();
                break;
            case ALEATORIO:
                validateKeyAleatory();
                break;
            case CELULAR:
                validateKeyPhone();
                break;
            default:
                throw new KeyPixUnknownException();
        }
    }

    private void validateKeyValue() {
        pixAccountUserDetailsRepository.findByKeyValue(pixAccountUserDetails.getKeyValue()).ifPresent(response -> {
            log.error("Key Value [{}] already exists", pixAccountUserDetails.getKeyValue());
            throw new KeyValueAlreadyExistsException();
        });
    }

    private void validatePersonType() {
        if (!pixAccountUserDetails.getPersonType().toString().matches(PERSON_TYPE_REGEX)) {
            log.error("Person Type [{}] invalid", pixAccountUserDetails.getPersonType());
            throw new PersonTypeException();
        }
    }

    private void validateMaxKeyValue() {
        var agencyNumber = Integer.parseInt(pixAccountUserDetails.getAgencyNumber());
        var accountNumber = Integer.parseInt(pixAccountUserDetails.getAccountNumber());
        Optional<PixLimitMaxKeyValueProjection> pixLimitMaxKeyValueResponseDto = pixAccountUserDetailsRepository.findByNumberKeyPix(agencyNumber, accountNumber);

        pixLimitMaxKeyValueResponseDto.ifPresent(pixLimitMaxKeyValueResponse -> {
            if (Objects.equals(pixLimitMaxKeyValueResponse.getPersonType(), NATURAL_PERSON) && pixLimitMaxKeyValueResponse.getCountKeyValue() > limitMaxKeyValueNaturalPerson) {
                log.error("Person Type [{}] reached the limit [{}] of registered pix key", pixLimitMaxKeyValueResponse.getPersonType(), pixLimitMaxKeyValueResponse.getCountKeyValue());
                throw new LimitMaxKeyValueNaturalPersonException();
            } else if (Objects.equals(pixLimitMaxKeyValueResponse.getPersonType(), LEGAL_PERSON) && pixLimitMaxKeyValueResponse.getCountKeyValue() > limitMaxKeyValueLegalPerson) {
                log.error("Person Type [{}] reached the limit [{}] of registered pix key", pixLimitMaxKeyValueResponse.getPersonType(), pixLimitMaxKeyValueResponse.getCountKeyValue());
                throw new LimitMaxKeyValueLegalPersonException();
            }
        });
    }

    private void validateAgencyNumber() {
        if (pixAccountUserDetails.getAgencyNumber().length() > 4) {
            log.error("Agency number [{}] size should not exceed 4", pixAccountUserDetails.getAgencyNumber());
            throw new AgencyExceedValueNumberException();
        }

        try {
            Integer.parseInt(pixAccountUserDetails.getAgencyNumber());
        } catch (Exception ex) {
            log.error("Agency number [{}] must allow numeric values", pixAccountUserDetails.getAgencyNumber());
            throw new AgencyCannotContainCharacterException();
        }
    }

    private void validateAccountNumber() {
        if (pixAccountUserDetails.getAccountNumber().length() > 8) {
            log.error("Account number [{}] size should not exceed 8", pixAccountUserDetails.getAccountNumber());
            throw new AccountExceedValueNumberException();
        }

        try {
            Integer.parseInt(pixAccountUserDetails.getAccountNumber());
        } catch (Exception ex) {
            log.error("Account number [{}] must allow numeric values", pixAccountUserDetails.getAccountNumber());
        }
    }

    private void validateAccountType() {
        if (!pixAccountUserDetails.getAccountType().equalsIgnoreCase(accountKeyAcceptedCurrent.toUpperCase()) && !pixAccountUserDetails.getAccountType().equalsIgnoreCase(accountKeyAcceptedSavings.toUpperCase())) {
            log.error("Account type informed [{}] is invalid", pixAccountUserDetails.getAccountType());
            throw new AccountTypeException();
        }
    }

    private void validateAccountHolderNameAndAccountHolderLastLame() {
        if (pixAccountUserDetails.getAccountHolderName().length() > MAX_CHARACTER_ACCOUNT_HOLDER_NAME) {
            throw new AccountHolderNameException();
        }

        if (pixAccountUserDetails.getAccountHolderLastLame() != null && pixAccountUserDetails.getAccountHolderLastLame().length() > MAX_CHARACTER_ACCOUNT_HOLDER_LAST_NAME) {
            throw new AccountHolderLastNameException();
        }
    }

    private void validateCPF() {
        boolean isCPF = ValidationCPFOrCNPJ.isCPF(pixAccountUserDetails.getKeyValue());

        if (!isCPF) {
            log.error("CPF informed [{}] is invalid", pixAccountUserDetails.getKeyValue());
            throw new InvalidCPFException();
        }
    }

    private void validateCNPJ() {
        boolean isCNPJ = ValidationCPFOrCNPJ.isCNPJ(pixAccountUserDetails.getKeyValue());

        if (!isCNPJ) {
            log.error("CNPJ informed [{}] is invalid", pixAccountUserDetails.getKeyValue());
            throw new InvalidCNPJException();
        }
    }

    private void validateKeyAleatory() {
        if (pixAccountUserDetails.getKeyValue().length() > 36) {
            log.error("Key aleatory informed [{}] is invalid", pixAccountUserDetails.getKeyValue());
            throw new InvalidKeyAleatoryException();
        }
    }

    private void validateEmail() {
        Pattern pattern = Pattern.compile(KEY_TYPE_EMAIL_REGEX);
        Matcher matcher = pattern.matcher(pixAccountUserDetails.getKeyValue());
        if (!matcher.matches()) {
            log.error("Email informed [{}] is invalid", pixAccountUserDetails.getKeyValue());
            throw new InvalidEmailException();
        }
    }

    private void validateKeyPhone() {
        Pattern pattern = Pattern.compile(KEY_TYPE_PHONE_REGEX);
        Matcher matcher = pattern.matcher(pixAccountUserDetails.getKeyValue());
        if (!matcher.matches()) {
            log.error("Phone informed [{}] is invalid", pixAccountUserDetails.getKeyValue());
            throw new InvalidPhoneException();
        }
    }
}
