package br.com.project.pix.service.helper;

import br.com.project.pix.config.ProjectionConfigTest;
import br.com.project.pix.dto.projection.PixLimitMaxKeyValueProjection;
import br.com.project.pix.exception.validations.AccountCannotContainCharacterException;
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
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static br.com.project.pix.helper.Helper.createEmptyHolderLastNameAccountUserDetails;
import static br.com.project.pix.helper.Helper.createPixAccountNumberInvalidAccountUserDetails;
import static br.com.project.pix.helper.Helper.createPixAccountNumberInvalidCharacterAccountUserDetails;
import static br.com.project.pix.helper.Helper.createPixAccountTypeInvalidAccountUserDetails;
import static br.com.project.pix.helper.Helper.createPixAccountUserDetailsProjection;
import static br.com.project.pix.helper.Helper.createPixAgencyCountInvalidAccountUserDetails;
import static br.com.project.pix.helper.Helper.createPixAgencyCountInvalidCharacterAccountUserDetails;
import static br.com.project.pix.helper.Helper.createPixAleatoryAccountUserDetails;
import static br.com.project.pix.helper.Helper.createPixAleatoryInvalidAccountUserDetails;
import static br.com.project.pix.helper.Helper.createPixCelularAccountUserDetails;
import static br.com.project.pix.helper.Helper.createPixCnpjAccountUserDetails;
import static br.com.project.pix.helper.Helper.createPixCnpjInvalidAccountUserDetails;
import static br.com.project.pix.helper.Helper.createPixCpfAccountUserDetails;
import static br.com.project.pix.helper.Helper.createPixCpfInvalidAccountUserDetails;
import static br.com.project.pix.helper.Helper.createPixEmailAccountUserDetails;
import static br.com.project.pix.helper.Helper.createPixEmailInvalidAccountUserDetails;
import static br.com.project.pix.helper.Helper.createPixHolderLastNameInvalidAccountUserDetails;
import static br.com.project.pix.helper.Helper.createPixHolderNameInvalidAccountUserDetails;
import static br.com.project.pix.helper.Helper.createPixInvalidPhoneAccountUserDetails;
import static br.com.project.pix.helper.Helper.createPixLimitMaxKeyLegalPersonValueDTO;
import static br.com.project.pix.helper.Helper.createPixLimitMaxKeyNaturalPersonValueDTO;
import static br.com.project.pix.helper.Helper.createPixPersonTypeInvalidAccountUserDetails;
import static br.com.project.pix.helper.Helper.createPixUnknomnAccountUserDetails;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ProjectionConfigTest.class})
public class ValidationsServiceTest {

    @Autowired
    private ProjectionFactory projectionFactory;

    @InjectMocks
    private ValidationsService validationsServiceMock;

    @Mock
    private PixAccountUserDetailsRepository pixAccountUserDetailsRepository;


    private PixAccountUserDetails pixAccountUserDetails;

    @Before
    public void setup() {
        ReflectionTestUtils.setField(validationsServiceMock, "limitMaxKeyValueLegalPerson", 20);
        ReflectionTestUtils.setField(validationsServiceMock, "limitMaxKeyValueNaturalPerson", 5);
        ReflectionTestUtils.setField(validationsServiceMock, "accountKeyAcceptedCurrent", "corrente");
        ReflectionTestUtils.setField(validationsServiceMock, "accountKeyAcceptedSavings", "poupança");


    }

    @Test
    public void shouldValidateCreatePhonePixAccountUserDetails() {

        //when
        pixAccountUserDetails = createPixCelularAccountUserDetails();

        validationsServiceMock.validateCreatePixAccountUserDetails(pixAccountUserDetails, pixAccountUserDetailsRepository);

        verify(pixAccountUserDetailsRepository, times(1)).findByNumberKeyPix(any(), any());

    }

    @Test
    public void shouldValidateCreateEmptyHolderLastNamePixAccountUserDetails() {

        //when
        pixAccountUserDetails = createEmptyHolderLastNameAccountUserDetails();

        validationsServiceMock.validateCreatePixAccountUserDetails(pixAccountUserDetails, pixAccountUserDetailsRepository);

        verify(pixAccountUserDetailsRepository, times(1)).findByNumberKeyPix(any(), any());

    }

    @Test
    public void shouldKeyValueAlreadyExistsException() {

        //when
        when(pixAccountUserDetailsRepository.findByKeyValue(any())).thenReturn(Optional.of(createPixAccountUserDetailsProjection(projectionFactory)));
        pixAccountUserDetails = createPixCelularAccountUserDetails();

        assertThrows(KeyValueAlreadyExistsException.class, () -> validationsServiceMock.validateCreatePixAccountUserDetails(pixAccountUserDetails, pixAccountUserDetailsRepository));
    }

    @Test
    public void shouldValidateCreateEmailPixAccountUserDetails() {

        //when
        pixAccountUserDetails = createPixEmailAccountUserDetails();

        validationsServiceMock.validateCreatePixAccountUserDetails(pixAccountUserDetails, pixAccountUserDetailsRepository);

        verify(pixAccountUserDetailsRepository, times(1)).findByNumberKeyPix(any(), any());

    }

    @Test
    public void shouldValidateCreateCpfPixAccountUserDetails() {

        //when
        pixAccountUserDetails = createPixCpfAccountUserDetails();

        validationsServiceMock.validateCreatePixAccountUserDetails(pixAccountUserDetails, pixAccountUserDetailsRepository);

        verify(pixAccountUserDetailsRepository, times(1)).findByNumberKeyPix(any(), any());

    }

    @Test
    public void shouldValidateCreateCnpjPixAccountUserDetails() {

        //when
        pixAccountUserDetails = createPixCnpjAccountUserDetails();

        validationsServiceMock.validateCreatePixAccountUserDetails(pixAccountUserDetails, pixAccountUserDetailsRepository);

        verify(pixAccountUserDetailsRepository, times(1)).findByNumberKeyPix(any(), any());

    }

    @Test
    public void shouldValidateCreateAleatoryPixAccountUserDetails() {

        //when
        pixAccountUserDetails = createPixAleatoryAccountUserDetails();

        validationsServiceMock.validateCreatePixAccountUserDetails(pixAccountUserDetails, pixAccountUserDetailsRepository);

        verify(pixAccountUserDetailsRepository, times(1)).findByNumberKeyPix(any(), any());

    }

    @Test
    public void shouldValidateUpdatePhonePixAccountUserDetails() {

        //when
        pixAccountUserDetails = createPixCelularAccountUserDetails();

        validationsServiceMock.validateUpdatePixAccountUserDetails(pixAccountUserDetails, pixAccountUserDetailsRepository);

        verify(pixAccountUserDetailsRepository, times(0)).findByNumberKeyPix(any(), any());

    }

    @Test
    public void shouldValidateUpdateEmailPixAccountUserDetails() {

        //when
        pixAccountUserDetails = createPixEmailAccountUserDetails();

        validationsServiceMock.validateUpdatePixAccountUserDetails(pixAccountUserDetails, pixAccountUserDetailsRepository);

        verify(pixAccountUserDetailsRepository, times(0)).findByNumberKeyPix(any(), any());

    }

    @Test
    public void shouldValidateUpdateCpfPixAccountUserDetails() {

        //when
        pixAccountUserDetails = createPixCpfAccountUserDetails();

        validationsServiceMock.validateUpdatePixAccountUserDetails(pixAccountUserDetails, pixAccountUserDetailsRepository);

        verify(pixAccountUserDetailsRepository, times(0)).findByNumberKeyPix(any(), any());

    }

    @Test
    public void shouldValidateUpdateCnpjPixAccountUserDetails() {

        //when
        pixAccountUserDetails = createPixCnpjAccountUserDetails();

        validationsServiceMock.validateUpdatePixAccountUserDetails(pixAccountUserDetails, pixAccountUserDetailsRepository);

        verify(pixAccountUserDetailsRepository, times(0)).findByNumberKeyPix(any(), any());

    }

    @Test
    public void shouldValidateUpdateAleatoryPixAccountUserDetails() {

        //when
        pixAccountUserDetails = createPixAleatoryAccountUserDetails();

        validationsServiceMock.validateUpdatePixAccountUserDetails(pixAccountUserDetails, pixAccountUserDetailsRepository);

        verify(pixAccountUserDetailsRepository, times(0)).findByNumberKeyPix(any(), any());

    }

    @Test
    @DisplayName("Deve retornar uma exception KeyPixUnknownException ao passar uma chave pix desconhecida")
    public void shouldReturnKeyInactiveException() {

        //when
        pixAccountUserDetails = createPixUnknomnAccountUserDetails();

        assertThrows(KeyPixUnknownException.class, () -> validationsServiceMock.validateCreatePixAccountUserDetails(pixAccountUserDetails, pixAccountUserDetailsRepository));
    }

    @Test
    @DisplayName("Deve retornar uma exception PersonTypeException ao passar um tipo de pessoa inválido")
    public void shouldReturnPersonTypeException() {

        //when
        pixAccountUserDetails = createPixPersonTypeInvalidAccountUserDetails();

        assertThrows(PersonTypeException.class, () -> validationsServiceMock.validateCreatePixAccountUserDetails(pixAccountUserDetails, pixAccountUserDetailsRepository));
    }

    @Test
    @DisplayName("Deve retornar uma exception AccountTypeException ao passar um tipo de conta inválida")
    public void shouldReturnAccountTypeException() {

        //when
        pixAccountUserDetails = createPixAccountTypeInvalidAccountUserDetails();

        assertThrows(AccountTypeException.class, () -> validationsServiceMock.validateCreatePixAccountUserDetails(pixAccountUserDetails, pixAccountUserDetailsRepository));
    }

    @Test
    @DisplayName("Deve retornar uma exception InvalidPhoneException ao passar um celular inválido")
    public void shouldReturnInvalidPhoneException() {

        //when
        pixAccountUserDetails = createPixInvalidPhoneAccountUserDetails();

        assertThrows(InvalidPhoneException.class, () -> validationsServiceMock.validateCreatePixAccountUserDetails(pixAccountUserDetails, pixAccountUserDetailsRepository));
    }

    @Test
    @DisplayName("Deve retornar uma exception LimitMaxKeyValueNaturalPersonException por exceder o número de chaves para pessoas físicas")
    public void shouldReturnLimitMaxKeyValueNaturalPersonException() {

        //when
        pixAccountUserDetails = createPixCelularAccountUserDetails();
        Optional<PixLimitMaxKeyValueProjection> pixLimitMaxKeyValueDTO = Optional.of(createPixLimitMaxKeyNaturalPersonValueDTO(projectionFactory));
        when(pixAccountUserDetailsRepository.findByNumberKeyPix(any(), any())).thenReturn(pixLimitMaxKeyValueDTO);

        assertThrows(LimitMaxKeyValueNaturalPersonException.class, () -> validationsServiceMock.validateCreatePixAccountUserDetails(pixAccountUserDetails, pixAccountUserDetailsRepository));
    }

    @Test
    @DisplayName("Deve retornar uma exception LimitMaxKeyValueNaturalPersonException por exceder o número de chaves para pessoas juridica")
    public void shouldReturnLimitMaxKeyValueLegalPersonException() {

        //when
        pixAccountUserDetails = createPixCelularAccountUserDetails();
        Optional<PixLimitMaxKeyValueProjection> pixLimitMaxKeyValueDTO = Optional.of(createPixLimitMaxKeyLegalPersonValueDTO(projectionFactory));
        when(pixAccountUserDetailsRepository.findByNumberKeyPix(any(), any())).thenReturn(pixLimitMaxKeyValueDTO);

        assertThrows(LimitMaxKeyValueLegalPersonException.class, () -> validationsServiceMock.validateCreatePixAccountUserDetails(pixAccountUserDetails, pixAccountUserDetailsRepository));
    }

    @Test
    @DisplayName("Deve retornar uma exception AgencyExceedValueNumberException por exceder o número de digitos da agencia")
    public void shouldReturnAgencyExceedValueNumberException() {

        //when
        pixAccountUserDetails = createPixAgencyCountInvalidAccountUserDetails();

        assertThrows(AgencyExceedValueNumberException.class, () -> validationsServiceMock.validateCreatePixAccountUserDetails(pixAccountUserDetails, pixAccountUserDetailsRepository));
    }

    @Test
    @DisplayName("Deve retornar uma exception AgencyCannotContainCharacterException por conter caracteres na agencia")
    public void shouldReturnAgencyCannotContainCharacterException() {

        //when
        pixAccountUserDetails = createPixAgencyCountInvalidCharacterAccountUserDetails();

        assertThrows(AgencyCannotContainCharacterException.class, () -> validationsServiceMock.validateCreatePixAccountUserDetails(pixAccountUserDetails, pixAccountUserDetailsRepository));
    }

    @Test
    @DisplayName("Deve retornar uma exception AccountExceedValueNumberException por exceder o número de digitos da conta")
    public void shouldReturnAccountExceedValueNumberException() {

        //when
        pixAccountUserDetails = createPixAccountNumberInvalidAccountUserDetails();

        assertThrows(AccountExceedValueNumberException.class, () -> validationsServiceMock.validateCreatePixAccountUserDetails(pixAccountUserDetails, pixAccountUserDetailsRepository));
    }

    @Test
    @DisplayName("Deve retornar uma exception AccountCannotContainCharacterException por conter caracteres na conta")
    public void shouldReturnAccountCannotContainCharacterException() {

        //when
        pixAccountUserDetails = createPixAccountNumberInvalidCharacterAccountUserDetails();

        assertThrows(AccountCannotContainCharacterException.class, () -> validationsServiceMock.validateCreatePixAccountUserDetails(pixAccountUserDetails, pixAccountUserDetailsRepository));
    }

    @Test
    @DisplayName("Deve retornar uma exception InvalidCPFException por passar um CPF inválido")
    public void shouldInvalidCPFException() {

        //when
        pixAccountUserDetails = createPixCpfInvalidAccountUserDetails();

        assertThrows(InvalidCPFException.class, () -> validationsServiceMock.validateCreatePixAccountUserDetails(pixAccountUserDetails, pixAccountUserDetailsRepository));
    }

    @Test
    @DisplayName("Deve retornar uma exception InvalidCNPJException por passar um CNPJ inválido")
    public void shouldInvalidCNPJException() {

        //when
        pixAccountUserDetails = createPixCnpjInvalidAccountUserDetails();

        assertThrows(InvalidCNPJException.class, () -> validationsServiceMock.validateCreatePixAccountUserDetails(pixAccountUserDetails, pixAccountUserDetailsRepository));
    }

    @Test
    @DisplayName("Deve retornar uma exception AccountHolderNameException por passar um Nome maior do que o suportado pelo banco")
    public void shouldReturnAccountHolderNameException() {

        //when
        pixAccountUserDetails = createPixHolderNameInvalidAccountUserDetails();

        assertThrows(AccountHolderNameException.class, () -> validationsServiceMock.validateCreatePixAccountUserDetails(pixAccountUserDetails, pixAccountUserDetailsRepository));
    }

    @Test
    @DisplayName("Deve retornar uma exception AccountHolderLastNameException por passar um Sobre Nome maior do que o suportado pelo banco")
    public void shouldReturnAccountHolderLastNameException() {

        //when
        pixAccountUserDetails = createPixHolderLastNameInvalidAccountUserDetails();

        assertThrows(AccountHolderLastNameException.class, () -> validationsServiceMock.validateCreatePixAccountUserDetails(pixAccountUserDetails, pixAccountUserDetailsRepository));
    }

    @Test
    @DisplayName("Deve retornar uma exception InvalidKeyAleatoryException por passar uma chave aleatória inválida")
    public void shouldReturnInvalidKeyAleatoryException() {

        //when
        pixAccountUserDetails = createPixAleatoryInvalidAccountUserDetails();

        assertThrows(InvalidKeyAleatoryException.class, () -> validationsServiceMock.validateCreatePixAccountUserDetails(pixAccountUserDetails, pixAccountUserDetailsRepository));
    }

    @Test
    @DisplayName("Deve retornar uma exception InvalidKeyAleatoryException por passar uma chave email inválida")
    public void shouldReturnInvalidEmailException() {

        //when
        pixAccountUserDetails = createPixEmailInvalidAccountUserDetails();

        assertThrows(InvalidEmailException.class, () -> validationsServiceMock.validateCreatePixAccountUserDetails(pixAccountUserDetails, pixAccountUserDetailsRepository));
    }
}
