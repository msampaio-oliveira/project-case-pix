package br.com.project.pix.exception;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public class ErrorListDTO {

    @JsonAlias({ "erros", "errors"})
    private List<ErrorDTO> errors;

    public List<ErrorDTO> getErrors() {
        return errors;
    }

    public void setErrors(final List<ErrorDTO> errors) {
        this.errors = errors;
    }
}
