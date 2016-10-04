package net.smartcosmos.userdetails.domain.rest;

import org.junit.*;

import static org.junit.Assert.*;

public class AuthenticateDetailsTest {

    @Test
    public void thatToStringExcludesPassword() {

        final String passwordValue = "passwordValue";

        AuthenticateDetails details = AuthenticateDetails.builder()
            .username("username")
            .grantType("grantType")
            .password(passwordValue)
            .scope("scope")
            .build();

        String detailsString = details.toString();

        assertFalse(detailsString.contains(AuthenticateDetails.PASSWORD));
        assertFalse(detailsString.contains(passwordValue));
    }
}
