package net.smartcosmos.userdetails.domain.rest;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.*;

import static org.junit.Assert.*;

import static net.smartcosmos.userdetails.domain.rest.AuthenticateRequest.CREDENTIALS;
import static net.smartcosmos.userdetails.domain.rest.AuthenticateRequest.NAME;

public class AuthenticateRequestTest {

    private Validator validator = Validation.buildDefaultValidatorFactory()
        .getValidator();

    // region toString

    @Test
    public void thatToStringIgnoresCredentials() {

        final String passwordValue = "somePassword";

        AuthenticateRequest request = AuthenticateRequest.builder()
            .name("username")
            .credentials(passwordValue)
            .build();

        String requestString = request.toString();

        assertFalse(requestString.contains(CREDENTIALS));
        assertFalse(requestString.contains(passwordValue));
    }

    // endregion

    // region Validation

    @Test
    public void thatValidationSucceeds() {

        AuthenticateRequest request = AuthenticateRequest.builder()
            .name("username")
            .credentials("password")
            .build();

        Set<ConstraintViolation<AuthenticateRequest>> violations = validator.validate(request);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void thatValidationFailsIfCredentialsIsNull() {

        AuthenticateRequest request = AuthenticateRequest.builder()
            .name("username")
            .credentials(null)
            .build();

        Set<ConstraintViolation<AuthenticateRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());

        ConstraintViolation<AuthenticateRequest> violation = violations.iterator()
            .next();

        assertEquals(CREDENTIALS,
                     violation.getPropertyPath()
                         .toString());
        assertEquals("{org.hibernate.validator.constraints.NotEmpty.message}", violation.getMessageTemplate());
    }

    @Test
    public void thatValidationFailsIfCredentialsIsEmpty() {

        AuthenticateRequest request = AuthenticateRequest.builder()
            .name("username")
            .credentials("")
            .build();

        Set<ConstraintViolation<AuthenticateRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());

        ConstraintViolation<AuthenticateRequest> violation = violations.iterator()
            .next();

        assertEquals(CREDENTIALS,
                     violation.getPropertyPath()
                         .toString());
        assertEquals("{org.hibernate.validator.constraints.NotEmpty.message}", violation.getMessageTemplate());
    }

    @Test
    public void thatValidationFailsIfNameIsNull() {

        AuthenticateRequest request = AuthenticateRequest.builder()
            .name(null)
            .credentials("password")
            .build();

        Set<ConstraintViolation<AuthenticateRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());

        ConstraintViolation<AuthenticateRequest> violation = violations.iterator()
            .next();

        assertEquals(NAME,
                     violation.getPropertyPath()
                         .toString());
        assertEquals("{org.hibernate.validator.constraints.NotEmpty.message}", violation.getMessageTemplate());
    }

    @Test
    public void thatValidationFailsIfNameIsEmpty() {

        AuthenticateRequest request = AuthenticateRequest.builder()
            .name("")
            .credentials("password")
            .build();

        Set<ConstraintViolation<AuthenticateRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());

        ConstraintViolation<AuthenticateRequest> violation = violations.iterator()
            .next();

        assertEquals(NAME,
                     violation.getPropertyPath()
                         .toString());
        assertEquals("{org.hibernate.validator.constraints.NotEmpty.message}", violation.getMessageTemplate());
    }

    // endregion
}
