package com.gambling.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ScratchGameExceptionTest {

    @Test
    void testScratchGameExceptionWithMessage() {
        String errorMessage = "This is a test error message";
        ScratchGameException exception = new ScratchGameException(errorMessage);

        assertNotNull(exception);
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testScratchGameExceptionWithCause() {
        Throwable cause = new IllegalArgumentException("Illegal argument");

        ScratchGameException exception = new ScratchGameException(cause);

        assertNotNull(exception);
        assertEquals(cause, exception.getCause());
        assertEquals("java.lang.IllegalArgumentException: Illegal argument", exception.getCause().toString());
    }

    @Test
    void testScratchGameExceptionWithMessageAndCause() {
        String errorMessage = "This is a test error message";
        Throwable cause = new IllegalArgumentException("Illegal argument");

        ScratchGameException exception = new ScratchGameException(errorMessage);
        ScratchGameException exceptionWithCause = new ScratchGameException(cause);

        assertNotNull(exception);
        assertNotNull(exceptionWithCause);
        assertEquals(errorMessage, exception.getMessage());
        assertEquals(cause, exceptionWithCause.getCause());
    }
}