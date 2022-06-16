package br.com.project.pix.exception;

import br.com.project.pix.exception.validations.AccountCannotContainCharacterException;
import br.com.project.pix.exception.validations.AccountExceedValueNumberException;
import br.com.project.pix.exception.validations.AccountHolderLastNameException;
import br.com.project.pix.exception.validations.AccountHolderNameException;
import br.com.project.pix.exception.validations.AccountTypeException;
import br.com.project.pix.exception.validations.AgencyCannotContainCharacterException;
import br.com.project.pix.exception.validations.AgencyExceedValueNumberException;
import br.com.project.pix.exception.validations.DataNotFoundException;
import br.com.project.pix.exception.validations.FilterDateException;
import br.com.project.pix.exception.validations.FilterOnlyIdException;
import br.com.project.pix.exception.validations.InvalidCNPJException;
import br.com.project.pix.exception.validations.InvalidCPFException;
import br.com.project.pix.exception.validations.InvalidEmailException;
import br.com.project.pix.exception.validations.InvalidKeyAleatoryException;
import br.com.project.pix.exception.validations.InvalidPhoneException;
import br.com.project.pix.exception.validations.KeyInactiveException;
import br.com.project.pix.exception.validations.KeyPixUnknownException;
import br.com.project.pix.exception.validations.KeyValueAlreadyExistsException;
import br.com.project.pix.exception.validations.LimitMaxKeyValueLegalPersonException;
import br.com.project.pix.exception.validations.LimitMaxKeyValueNaturalPersonException;
import br.com.project.pix.exception.validations.PersonTypeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.Locale;

import static br.com.project.pix.exception.ErrorCodes.ACCOUNT_CANNOT_CONTAIN_CHARACTER_EXCEPTION;
import static br.com.project.pix.exception.ErrorCodes.ACCOUNT_EXCEED_VALUE_NUMBER_EXCEPTION;
import static br.com.project.pix.exception.ErrorCodes.ACCOUNT_HOLDER_LAST_NAME_EXCEPTION;
import static br.com.project.pix.exception.ErrorCodes.ACCOUNT_HOLDER_NAME_EXCEPTION;
import static br.com.project.pix.exception.ErrorCodes.ACCOUNT_TYPE_EXCEPTION;
import static br.com.project.pix.exception.ErrorCodes.AGENCY_CANNOT_CONTAIN_CHARACTER_EXCEPTION;
import static br.com.project.pix.exception.ErrorCodes.AGENCY_EXCEED_VALUE_NUMBER_EXCEPTION;
import static br.com.project.pix.exception.ErrorCodes.DATA_NOT_FOUND_EXCEPTION;
import static br.com.project.pix.exception.ErrorCodes.FILTER_DATE_EXCEPTION;
import static br.com.project.pix.exception.ErrorCodes.FILTER_ONLY_ID_EXCEPTION;
import static br.com.project.pix.exception.ErrorCodes.INVALID_CPF_EXCEPTION;
import static br.com.project.pix.exception.ErrorCodes.INVALID_EMAIL_EXCEPTION;
import static br.com.project.pix.exception.ErrorCodes.INVALID_KEY_ALEATORY_EXCEPTION;
import static br.com.project.pix.exception.ErrorCodes.INVALID_PHONE_EXCEPTION;
import static br.com.project.pix.exception.ErrorCodes.KEY_CNPJ_UNKNOWN;
import static br.com.project.pix.exception.ErrorCodes.KEY_INACTIVE_EXCEPTION;
import static br.com.project.pix.exception.ErrorCodes.KEY_PERSON_EXCEPTION;
import static br.com.project.pix.exception.ErrorCodes.KEY_PIX_UNKNOWN;
import static br.com.project.pix.exception.ErrorCodes.KEY_VALUE_ALREADY_EXISTS;
import static br.com.project.pix.exception.ErrorCodes.LIMIT_MAX_KEY_VALUE_LEGAL_NATURAL_EXCEPTION;
import static br.com.project.pix.exception.ErrorCodes.LIMIT_MAX_KEY_VALUE_LEGAL_PERSON_EXCEPTION;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ResourceExceptionHandlerConfig extends ResponseEntityExceptionHandler {

    private final ApplicationContext applicationContext;


    @ExceptionHandler(value = {KeyValueAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorListDTO unProcessableEntityKeyValueAlreadyExistsException(final KeyValueAlreadyExistsException ex) {
        return getResponseErrorWithMessage(KEY_VALUE_ALREADY_EXISTS);
    }

    @ExceptionHandler(value = {PersonTypeException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorListDTO unProcessableEntityPersonTypeException(final PersonTypeException ex) {
        return getResponseErrorWithMessage(KEY_PERSON_EXCEPTION);
    }

    @ExceptionHandler(value = {LimitMaxKeyValueLegalPersonException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorListDTO unProcessableEntityLimitMaxKeyLegalPersonException(final LimitMaxKeyValueLegalPersonException ex) {
        return getResponseErrorWithMessage(LIMIT_MAX_KEY_VALUE_LEGAL_PERSON_EXCEPTION);
    }

    @ExceptionHandler(value = {LimitMaxKeyValueNaturalPersonException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorListDTO unProcessableEntityLimitMaxKeyValueNaturalPersonException(final LimitMaxKeyValueNaturalPersonException ex) {
        return getResponseErrorWithMessage(LIMIT_MAX_KEY_VALUE_LEGAL_NATURAL_EXCEPTION);
    }

    @ExceptionHandler(value = {InvalidCPFException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorListDTO unProcessableEntityInvalidCPFException(final InvalidCPFException ex) {
        return getResponseErrorWithMessage(INVALID_CPF_EXCEPTION);
    }

    @ExceptionHandler(value = {KeyPixUnknownException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorListDTO unProcessableEntityKeyPixUnknownException(final KeyPixUnknownException ex) {
        return getResponseErrorWithMessage(KEY_PIX_UNKNOWN);
    }

    @ExceptionHandler(value = {InvalidCNPJException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorListDTO unProcessableEntityInvalidCNPJException(final InvalidCNPJException ex) {
        return getResponseErrorWithMessage(KEY_CNPJ_UNKNOWN);
    }

    @ExceptionHandler(value = {InvalidEmailException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorListDTO unProcessableEntityInvalidEmailException(final InvalidEmailException ex) {
        return getResponseErrorWithMessage(INVALID_EMAIL_EXCEPTION);
    }

    @ExceptionHandler(value = {AccountTypeException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorListDTO unProcessableEntityAccountTypeException(final AccountTypeException ex) {
        return getResponseErrorWithMessage(ACCOUNT_TYPE_EXCEPTION);
    }

    @ExceptionHandler(value = {AccountExceedValueNumberException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorListDTO unProcessableEntityAccountExceedValueNumberException(final AccountExceedValueNumberException ex) {
        return getResponseErrorWithMessage(ACCOUNT_EXCEED_VALUE_NUMBER_EXCEPTION);
    }


    @ExceptionHandler(value = {AgencyExceedValueNumberException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorListDTO unProcessableEntityAgencyExceedValueNumberException(final AgencyExceedValueNumberException ex) {
        return getResponseErrorWithMessage(AGENCY_EXCEED_VALUE_NUMBER_EXCEPTION);
    }

    @ExceptionHandler(value = {AgencyCannotContainCharacterException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorListDTO unProcessableEntityAgencyCannotContainCharacterException(final AgencyCannotContainCharacterException ex) {
        return getResponseErrorWithMessage(AGENCY_CANNOT_CONTAIN_CHARACTER_EXCEPTION);
    }

    @ExceptionHandler(value = {AccountCannotContainCharacterException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorListDTO unProcessableEntityAccountCannotContainCharacterException(final AccountCannotContainCharacterException ex) {
        return getResponseErrorWithMessage(ACCOUNT_CANNOT_CONTAIN_CHARACTER_EXCEPTION);
    }

    @ExceptionHandler(value = {InvalidKeyAleatoryException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorListDTO unProcessableEntityInvalidKeyAleatoryException(final InvalidKeyAleatoryException ex) {
        return getResponseErrorWithMessage(INVALID_KEY_ALEATORY_EXCEPTION);
    }

    @ExceptionHandler(value = {InvalidPhoneException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorListDTO unProcessableEntityInvalidPhoneException(final InvalidPhoneException ex) {
        return getResponseErrorWithMessage(INVALID_PHONE_EXCEPTION);
    }

    @ExceptionHandler(value = {DataNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorListDTO unProcessableEntityDataNotFoundException(final DataNotFoundException ex) {
        return getResponseErrorWithMessage(DATA_NOT_FOUND_EXCEPTION);
    }

    @ExceptionHandler(value = {KeyInactiveException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorListDTO unProcessableEntityDataNotFoundException(final KeyInactiveException ex) {
        return getResponseErrorWithMessage(KEY_INACTIVE_EXCEPTION);
    }

    @ExceptionHandler(value = {AccountHolderNameException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorListDTO unProcessableEntityAccountHolderNameException(final AccountHolderNameException ex) {
        return getResponseErrorWithMessage(ACCOUNT_HOLDER_NAME_EXCEPTION);
    }

    @ExceptionHandler(value = {AccountHolderLastNameException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorListDTO unProcessableEntityAccountHolderLastNameException(final AccountHolderLastNameException ex) {
        return getResponseErrorWithMessage(ACCOUNT_HOLDER_LAST_NAME_EXCEPTION);
    }

    @ExceptionHandler(value = {FilterOnlyIdException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorListDTO unProcessableEntityFilterOnlyIdException(final FilterOnlyIdException ex) {
        return getResponseErrorWithMessage(FILTER_ONLY_ID_EXCEPTION);
    }

    @ExceptionHandler(value = {FilterDateException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorListDTO unProcessableEntityFilterDateException(final FilterDateException ex) {
        return getResponseErrorWithMessage(FILTER_DATE_EXCEPTION);
    }

    private ErrorListDTO getResponseErrorWithMessage(String errorCode) {
        ErrorListDTO errorListDTO = new ErrorListDTO();
        errorListDTO.setErrors(Collections.singletonList(
                new ErrorDTO(errorCode, getMessage(errorCode))));
        return errorListDTO;
    }

    private String getMessage(final String errorCode, final String... args) {
        return this.applicationContext.getMessage(errorCode, args, Locale.getDefault());
    }
}
