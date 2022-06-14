package br.com.project.pix.helper;

import br.com.project.pix.dto.PixLimitMaxKeyValueDTO;
import br.com.project.pix.dto.projection.PixAccountUserDetailsProjection;
import br.com.project.pix.dto.projection.PixLimitMaxKeyValueProjection;
import br.com.project.pix.model.PixAccountUserDetails;
import org.springframework.data.projection.ProjectionFactory;

import java.time.LocalDateTime;
import java.util.UUID;

public class Helper {

    public static final Long ID = 83839439L;
    public static final String KEY_TYPE_EMAIL = "EMAIL";
    public static final String KEY_TYPE_CELULAR = "CELULAR";
    public static final String KEY_TYPE_CPF = "CPF";
    public static final String KEY_TYPE_CNPJ = "CNPJ";
    public static final String KEY_TYPE_ALEATORIA = "ALEATORIO";
    public static final String KEY_TYPE_UNKNOWN = "UNKNOWN";
    public static final String KEY_VALUE_EMAIL = "julhofmsampaio@gmail.com";
    public static final String KEY_VALUE_CELULAR = "+5511976548767";
    public static final String KEY_VALUE_CPF = "31205573070";

    public static final String KEY_VALUE_CPF_INVALID = "73543747633";
    public static final String KEY_VALUE_CNPJ = "23224431000137";
    public static final String KEY_VALUE_ALEATORIA = "053280676376367824886";
    public static final String ACCOUNT_TYPE_SAVINGS = "POUPANÇA";
    public static final String ACCOUNT_TYPE_CURRENT = "CORRENTE";
    public static final Character PERSON_NATURAL_TYPE = 'F';
    public static final Character PERSON_LEGAL_TYPE = 'J';
    public static final String ACCOUNT_HOLDER_NAME = "Matheus";
    public static final String AGENCY_ACCOUNT = "0228";
    public static final String AGENCY_ACCOUNT_INVALID = "0228543";

    public static final String ACCOUNT_NUMBER = "938743";

    public static final String ACCOUNT_NUMBER_INVALID = "876548938743";


    public static PixAccountUserDetailsProjection createPixAccountUserDetailsProjection(ProjectionFactory projectionFactory) {
        PixAccountUserDetails pixAccountUserDetails = createPixAccountUserDetails(KEY_TYPE_EMAIL, KEY_VALUE_EMAIL, ACCOUNT_TYPE_SAVINGS, PERSON_LEGAL_TYPE, false, AGENCY_ACCOUNT, ACCOUNT_NUMBER);
        return projectionFactory.createProjection(PixAccountUserDetailsProjection.class, pixAccountUserDetails);
    }

    public static PixAccountUserDetails createPixEmailAccountUserDetails() {
        return createPixAccountUserDetails(KEY_TYPE_EMAIL, KEY_VALUE_EMAIL, ACCOUNT_TYPE_SAVINGS, PERSON_NATURAL_TYPE, false, AGENCY_ACCOUNT, ACCOUNT_NUMBER);
    }

    public static PixAccountUserDetails createPixCpfAccountUserDetails() {
        return createPixAccountUserDetails(KEY_TYPE_CPF, KEY_VALUE_CPF, ACCOUNT_TYPE_SAVINGS, PERSON_NATURAL_TYPE, false, AGENCY_ACCOUNT, ACCOUNT_NUMBER);
    }

    public static PixAccountUserDetails createPixCnpjAccountUserDetails() {
        return createPixAccountUserDetails(KEY_TYPE_CNPJ, KEY_VALUE_CNPJ, ACCOUNT_TYPE_SAVINGS, PERSON_NATURAL_TYPE, false, AGENCY_ACCOUNT, ACCOUNT_NUMBER);
    }

    public static PixAccountUserDetails createPixAleatoryAccountUserDetails() {
        return createPixAccountUserDetails(KEY_TYPE_ALEATORIA, KEY_VALUE_ALEATORIA, ACCOUNT_TYPE_SAVINGS, PERSON_NATURAL_TYPE, false, AGENCY_ACCOUNT, ACCOUNT_NUMBER);
    }

    public static PixAccountUserDetails createPixUnknomnAccountUserDetails() {
        return createPixAccountUserDetails(KEY_TYPE_UNKNOWN, KEY_VALUE_ALEATORIA, ACCOUNT_TYPE_SAVINGS, PERSON_NATURAL_TYPE, false, AGENCY_ACCOUNT, ACCOUNT_NUMBER);
    }

    public static PixAccountUserDetails createPixAccountTypeInvalidAccountUserDetails() {
        return createPixAccountUserDetails(KEY_TYPE_CELULAR, KEY_VALUE_CELULAR, "CONJUNTA", PERSON_NATURAL_TYPE, false, AGENCY_ACCOUNT, ACCOUNT_NUMBER);
    }

    public static PixAccountUserDetails createPixAgencyCountInvalidAccountUserDetails() {
        return createPixAccountUserDetails(KEY_TYPE_CELULAR, KEY_VALUE_CELULAR, ACCOUNT_TYPE_CURRENT, PERSON_NATURAL_TYPE, false, AGENCY_ACCOUNT_INVALID, ACCOUNT_NUMBER);
    }

    public static PixAccountUserDetails createPixAccountNumberInvalidAccountUserDetails() {
        return createPixAccountUserDetails(KEY_TYPE_CELULAR, KEY_VALUE_CELULAR, ACCOUNT_TYPE_CURRENT, PERSON_NATURAL_TYPE, false, AGENCY_ACCOUNT, ACCOUNT_NUMBER_INVALID);
    }

    public static PixAccountUserDetails createPixCpfInvalidAccountUserDetails() {
        return createPixAccountUserDetails(KEY_TYPE_CNPJ, KEY_VALUE_CPF_INVALID, ACCOUNT_TYPE_CURRENT, PERSON_NATURAL_TYPE, false, AGENCY_ACCOUNT, ACCOUNT_NUMBER);
    }

    public static PixAccountUserDetails createPixAgencyCountInvalidCharacterAccountUserDetails() {
        return createPixAccountUserDetails(KEY_TYPE_CELULAR, KEY_VALUE_CELULAR, ACCOUNT_TYPE_CURRENT, PERSON_NATURAL_TYPE, false, "987M", ACCOUNT_NUMBER);
    }

    public static PixAccountUserDetails createPixPersonTypeInvalidAccountUserDetails() {
        return createPixAccountUserDetails(KEY_TYPE_CELULAR, KEY_VALUE_CELULAR, ACCOUNT_TYPE_CURRENT, 'M', false, AGENCY_ACCOUNT, ACCOUNT_NUMBER);
    }

    public static PixAccountUserDetails createPixInvalidPhoneAccountUserDetails() {
        return createPixAccountUserDetails(KEY_TYPE_CELULAR, "11981058950", ACCOUNT_TYPE_SAVINGS, PERSON_NATURAL_TYPE, false, AGENCY_ACCOUNT, ACCOUNT_NUMBER);
    }

    public static PixAccountUserDetails createPixMaxKeyNaturalPersonAccountUserDetails() {
        return createPixAccountUserDetails(KEY_TYPE_CELULAR, KEY_VALUE_CELULAR, ACCOUNT_TYPE_SAVINGS, PERSON_NATURAL_TYPE, false, AGENCY_ACCOUNT, ACCOUNT_NUMBER);
    }

    public static PixAccountUserDetails createPixCelularAccountUserDetails() {
        return createPixAccountUserDetails(KEY_TYPE_CELULAR, KEY_VALUE_CELULAR, ACCOUNT_TYPE_SAVINGS, PERSON_NATURAL_TYPE, false, AGENCY_ACCOUNT, ACCOUNT_NUMBER);
    }

    public static PixAccountUserDetails updatePixEmailAccountUserDetails() {
        return createPixAccountUserDetails(KEY_TYPE_EMAIL, KEY_VALUE_EMAIL, ACCOUNT_TYPE_CURRENT, PERSON_LEGAL_TYPE, false, AGENCY_ACCOUNT, ACCOUNT_NUMBER);
    }

    public static PixAccountUserDetails inactivePixEmailAccountUserDetails() {
        return createPixAccountUserDetails(KEY_TYPE_EMAIL, KEY_VALUE_EMAIL, ACCOUNT_TYPE_CURRENT, PERSON_LEGAL_TYPE, true, AGENCY_ACCOUNT, ACCOUNT_NUMBER);
    }


    public static PixLimitMaxKeyValueProjection createPixLimitMaxKeyLegalPersonValueDTO(ProjectionFactory projectionFactory) {
        PixLimitMaxKeyValueDTO pixAccountUserDetails = createPixLimitMaxKeyValueDTO(33, "J");
        return projectionFactory.createProjection(PixLimitMaxKeyValueProjection.class, pixAccountUserDetails);
    }

    public static PixLimitMaxKeyValueProjection createPixLimitMaxKeyNaturalPersonValueDTO(ProjectionFactory projectionFactory) {
        PixLimitMaxKeyValueDTO pixAccountUserDetails = createPixLimitMaxKeyValueDTO(7, "F");
        return projectionFactory.createProjection(PixLimitMaxKeyValueProjection.class, pixAccountUserDetails);
    }

    private static PixAccountUserDetails createPixAccountUserDetails(String keyType, String keyValue, String accountType, Character personType, boolean setInactiveKey, String agencyAccount, String accountNumber) {
        PixAccountUserDetails pixAccountUserDetails = new PixAccountUserDetails();
        pixAccountUserDetails.setId(UUID.randomUUID().hashCode() & Long.MAX_VALUE);
        pixAccountUserDetails.setKeyType(keyType);
        pixAccountUserDetails.setKeyValue(keyValue);
        pixAccountUserDetails.setAccountType(accountType);
        pixAccountUserDetails.setAgencyNumber(agencyAccount);
        pixAccountUserDetails.setAccountNumber(accountNumber);
        pixAccountUserDetails.setAccountHolderName("João");
        pixAccountUserDetails.setAccountHolderName("Henrique");
        pixAccountUserDetails.setPersonType(personType);
        if (setInactiveKey) {
            pixAccountUserDetails.setInactiveKeyDateTime(LocalDateTime.now());
        }
        return pixAccountUserDetails;
    }

    private static PixLimitMaxKeyValueDTO createPixLimitMaxKeyValueDTO(Integer countKeyValue, String personType) {
        PixLimitMaxKeyValueDTO pixLimitMaxKeyValueDTO = new PixLimitMaxKeyValueDTO();
        pixLimitMaxKeyValueDTO.setCountKeyValue(countKeyValue);
        pixLimitMaxKeyValueDTO.setPersonType(personType);

        return pixLimitMaxKeyValueDTO;
    }

}
