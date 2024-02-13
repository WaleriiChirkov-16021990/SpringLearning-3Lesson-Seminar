package ru.gb.springdemo.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

class BadRequestExceptionDiffblueTest {
    /**
     * Method under test: {@link BadRequestException#BadRequestException()}
     */
    @Test
    void testNewBadRequestException() {
        // Arrange and Act
        BadRequestException actualBadRequestException = new BadRequestException();

        // Assert
        assertNull(actualBadRequestException.getLocalizedMessage());
        assertNull(actualBadRequestException.getMessage());
        assertNull(actualBadRequestException.getCause());
        assertEquals(0, actualBadRequestException.getSuppressed().length);
    }

    /**
     * Method under test: {@link BadRequestException#BadRequestException(String)}
     */
    @Test
    void testNewBadRequestException2() {
        // Arrange and Act
        BadRequestException actualBadRequestException = new BadRequestException("An error occurred");

        // Assert
        assertEquals("An error occurred", actualBadRequestException.getLocalizedMessage());
        assertEquals("An error occurred", actualBadRequestException.getMessage());
        assertNull(actualBadRequestException.getCause());
        assertEquals(0, actualBadRequestException.getSuppressed().length);
    }

    /**
     * Method under test:
     * {@link BadRequestException#BadRequestException(String, Throwable)}
     */
    @Test
    void testNewBadRequestException3() {
        // Arrange
        Throwable cause = new Throwable();

        // Act
        BadRequestException actualBadRequestException = new BadRequestException("An error occurred", cause);

        // Assert
        assertEquals("An error occurred", actualBadRequestException.getLocalizedMessage());
        assertEquals("An error occurred", actualBadRequestException.getMessage());
        Throwable cause2 = actualBadRequestException.getCause();
        assertNull(cause2.getLocalizedMessage());
        assertNull(cause2.getMessage());
        assertNull(cause2.getCause());
        Throwable[] suppressed = actualBadRequestException.getSuppressed();
        assertEquals(0, suppressed.length);
        assertSame(cause, cause2);
        assertSame(suppressed, cause2.getSuppressed());
    }

    /**
     * Method under test:
     * {@link BadRequestException#BadRequestException(String, Throwable, boolean, boolean)}
     */
    @Test
    void testNewBadRequestException4() {
        // Arrange
        Throwable cause = new Throwable();

        // Act
        BadRequestException actualBadRequestException = new BadRequestException("An error occurred", cause, true, true);

        // Assert
        assertEquals("An error occurred", actualBadRequestException.getLocalizedMessage());
        assertEquals("An error occurred", actualBadRequestException.getMessage());
        Throwable cause2 = actualBadRequestException.getCause();
        assertNull(cause2.getLocalizedMessage());
        assertNull(cause2.getMessage());
        assertNull(cause2.getCause());
        Throwable[] suppressed = actualBadRequestException.getSuppressed();
        assertEquals(0, suppressed.length);
        assertSame(cause, cause2);
        assertSame(suppressed, cause2.getSuppressed());
    }

    /**
     * Method under test: {@link BadRequestException#BadRequestException(Throwable)}
     */
    @Test
    void testNewBadRequestException5() {
        // Arrange
        Throwable cause = new Throwable();

        // Act
        BadRequestException actualBadRequestException = new BadRequestException(cause);

        // Assert
        assertEquals("java.lang.Throwable", actualBadRequestException.getLocalizedMessage());
        assertEquals("java.lang.Throwable", actualBadRequestException.getMessage());
        Throwable cause2 = actualBadRequestException.getCause();
        assertNull(cause2.getLocalizedMessage());
        assertNull(cause2.getMessage());
        assertNull(cause2.getCause());
        Throwable[] suppressed = actualBadRequestException.getSuppressed();
        assertEquals(0, suppressed.length);
        assertSame(cause, cause2);
        assertSame(suppressed, cause2.getSuppressed());
    }
}
