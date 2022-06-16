package br.com.project.pix.exception;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CopyPropertyExceptionTest {

    private static final String EXCEPTION_MSG = "Copy property exception";

    @Test
    public void shouldThrowCopyPropertyExceptionWhenOptionalIsEmpty() {
        try {
            Optional.empty().orElseThrow(() -> new CopyPropertyException(EXCEPTION_MSG, new IllegalAccessException()));
        } catch (CopyPropertyException e) {
            assertTrue(e.getMessage().contains(EXCEPTION_MSG));
        }
    }
}
