package br.com.project.pix.exception;

public class CopyPropertyException extends RuntimeException {
    private static final long serialVersionUID = 5943580024080543985L;

    public CopyPropertyException(String msg, ReflectiveOperationException e) {
        super(msg, e);
    }
}
