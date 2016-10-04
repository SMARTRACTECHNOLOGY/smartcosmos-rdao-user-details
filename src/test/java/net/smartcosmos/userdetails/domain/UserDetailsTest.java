package net.smartcosmos.userdetails.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.*;

import static org.junit.Assert.*;

import static net.smartcosmos.userdetails.domain.UserDetails.AUTHORITIES;
import static net.smartcosmos.userdetails.domain.UserDetails.PASSWORD_HASH;
import static net.smartcosmos.userdetails.domain.UserDetails.TENANT_URN;
import static net.smartcosmos.userdetails.domain.UserDetails.USERNAME;
import static net.smartcosmos.userdetails.domain.UserDetails.USER_URN;

public class UserDetailsTest {

    private Validator validator = Validation.buildDefaultValidatorFactory()
        .getValidator();

    private ObjectMapper mapper = new ObjectMapper();

    // region Default Values

    @Test
    public void thatVersionIsSet() {

        UserDetails userDetails = UserDetails.builder()
            .build();

        assertNotNull(userDetails.getVersion());
        assertEquals(1, userDetails.getVersion());
    }

    @Test
    public void thatTenantUrnHasNoDefault() {

        UserDetails userDetails = UserDetails.builder()
            .build();

        assertNull(userDetails.getTenantUrn());
    }

    @Test
    public void thatUserUrnHasNoDefault() {

        UserDetails userDetails = UserDetails.builder()
            .build();

        assertNull(userDetails.getUserUrn());
    }

    @Test
    public void thatUsernameHasNoDefault() {

        UserDetails userDetails = UserDetails.builder()
            .build();

        assertNull(userDetails.getUsername());
    }

    @Test
    public void thatPasswordHashHasNoDefault() {

        UserDetails userDetails = UserDetails.builder()
            .build();

        assertNull(userDetails.getPasswordHash());
    }

    @Test
    public void thatAuthoritiesDefaultsToEmptySet() {

        UserDetails userDetails = UserDetails.builder()
            .build();

        assertNotNull(userDetails.getAuthorities());
        assertTrue(userDetails.getAuthorities() instanceof Set);
        assertTrue(userDetails.getAuthorities()
                       .isEmpty());
    }

    // endregion

    // region toString

    @Test
    public void thatToStringIgnoresPasswordHash() {

        final String passwordHashValue = "somePasswordHash";

        UserDetails userDetails = UserDetails.builder()
            .username("username")
            .passwordHash(passwordHashValue)
            .build();

        String userDetailsString = userDetails.toString();

        assertFalse(userDetailsString.contains(PASSWORD_HASH));
        assertFalse(userDetailsString.contains(passwordHashValue));
    }

    // endregion

    // region Validation

    @Test
    public void thatValidationSucceeds() {

        UserDetails userDetails = UserDetails.builder()
            .tenantUrn("tenantUrn")
            .userUrn("userUrn")
            .username("username")
            .passwordHash("passwordHash")
            .authorities(new HashSet<>(Arrays.asList("someAuthority", "someOtherAuthority")))
            .build();

        Set<ConstraintViolation<UserDetails>> violations = validator.validate(userDetails);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void thatValidationSucceedsIfPasswordHashIsEmpty() {

        UserDetails userDetails = UserDetails.builder()
            .tenantUrn("tenantUrn")
            .userUrn("userUrn")
            .username("username")
            .passwordHash("")
            .authorities(new HashSet<>(Arrays.asList("someAuthority", "someOtherAuthority")))
            .build();

        Set<ConstraintViolation<UserDetails>> violations = validator.validate(userDetails);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void thatValidationSucceedsIfPasswordHashIsNull() {

        UserDetails userDetails = UserDetails.builder()
            .tenantUrn("tenantUrn")
            .userUrn("userUrn")
            .username("username")
            .passwordHash(null)
            .authorities(new HashSet<>(Arrays.asList("someAuthority", "someOtherAuthority")))
            .build();

        Set<ConstraintViolation<UserDetails>> violations = validator.validate(userDetails);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void thatValidationSucceedsIfAuthoritiesIsNull() {

        UserDetails userDetails = UserDetails.builder()
            .tenantUrn("tenantUrn")
            .userUrn("userUrn")
            .username("username")
            .passwordHash("passwordHash")
            .authorities(null)
            .build();

        Set<ConstraintViolation<UserDetails>> violations = validator.validate(userDetails);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void thatValidationFailsIfTenantUrnIsNull() {

        UserDetails userDetails = UserDetails.builder()
            .tenantUrn(null)
            .userUrn("userUrn")
            .username("username")
            .passwordHash("passwordHash")
            .authorities(new HashSet<>(Arrays.asList("someAuthority", "someOtherAuthority")))
            .build();

        Set<ConstraintViolation<UserDetails>> violations = validator.validate(userDetails);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());

        ConstraintViolation<UserDetails> violation = violations.iterator()
            .next();

        assertEquals(TENANT_URN,
                     violation.getPropertyPath()
                         .toString());
        assertEquals("{org.hibernate.validator.constraints.NotEmpty.message}", violation.getMessageTemplate());
    }

    @Test
    public void thatValidationFailsIfTenantUrnIsEmpty() {

        UserDetails userDetails = UserDetails.builder()
            .tenantUrn("")
            .userUrn("userUrn")
            .username("username")
            .passwordHash("passwordHash")
            .authorities(new HashSet<>(Arrays.asList("someAuthority", "someOtherAuthority")))
            .build();

        Set<ConstraintViolation<UserDetails>> violations = validator.validate(userDetails);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());

        ConstraintViolation<UserDetails> violation = violations.iterator()
            .next();

        assertEquals(TENANT_URN,
                     violation.getPropertyPath()
                         .toString());
        assertEquals("{org.hibernate.validator.constraints.NotEmpty.message}", violation.getMessageTemplate());
    }

    @Test
    public void thatValidationFailsIfUserUrnIsNull() {

        UserDetails userDetails = UserDetails.builder()
            .tenantUrn("tenantUrn")
            .userUrn(null)
            .username("username")
            .passwordHash("passwordHash")
            .authorities(new HashSet<>(Arrays.asList("someAuthority", "someOtherAuthority")))
            .build();

        Set<ConstraintViolation<UserDetails>> violations = validator.validate(userDetails);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());

        ConstraintViolation<UserDetails> violation = violations.iterator()
            .next();

        assertEquals(USER_URN,
                     violation.getPropertyPath()
                         .toString());
        assertEquals("{org.hibernate.validator.constraints.NotEmpty.message}", violation.getMessageTemplate());
    }

    @Test
    public void thatValidationFailsIfUserUrnIsEmpty() {

        UserDetails userDetails = UserDetails.builder()
            .tenantUrn("tenantUrn")
            .userUrn("")
            .username("username")
            .passwordHash("passwordHash")
            .authorities(new HashSet<>(Arrays.asList("someAuthority", "someOtherAuthority")))
            .build();

        Set<ConstraintViolation<UserDetails>> violations = validator.validate(userDetails);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());

        ConstraintViolation<UserDetails> violation = violations.iterator()
            .next();

        assertEquals(USER_URN,
                     violation.getPropertyPath()
                         .toString());
        assertEquals("{org.hibernate.validator.constraints.NotEmpty.message}", violation.getMessageTemplate());
    }

    @Test
    public void thatValidationFailsIfUserNameIsNull() {

        UserDetails userDetails = UserDetails.builder()
            .tenantUrn("tenantUrn")
            .userUrn("userUrn")
            .username(null)
            .passwordHash("passwordHash")
            .authorities(new HashSet<>(Arrays.asList("someAuthority", "someOtherAuthority")))
            .build();

        Set<ConstraintViolation<UserDetails>> violations = validator.validate(userDetails);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());

        ConstraintViolation<UserDetails> violation = violations.iterator()
            .next();

        assertEquals(USERNAME,
                     violation.getPropertyPath()
                         .toString());
        assertEquals("{org.hibernate.validator.constraints.NotEmpty.message}", violation.getMessageTemplate());
    }

    @Test
    public void thatValidationFailsIfUsernameIsEmpty() {

        UserDetails userDetails = UserDetails.builder()
            .tenantUrn("tenantUrn")
            .userUrn("userUrn")
            .username("")
            .passwordHash("passwordHash")
            .authorities(new HashSet<>(Arrays.asList("someAuthority", "someOtherAuthority")))
            .build();

        Set<ConstraintViolation<UserDetails>> violations = validator.validate(userDetails);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());

        ConstraintViolation<UserDetails> violation = violations.iterator()
            .next();

        assertEquals(USERNAME,
                     violation.getPropertyPath()
                         .toString());
        assertEquals("{org.hibernate.validator.constraints.NotEmpty.message}", violation.getMessageTemplate());
    }

    // endregion

    // region Serialization

    @Test
    public void thatSerializationIgnoresVersion() throws JsonProcessingException {

        UserDetails userDetails = UserDetails.builder()
            .tenantUrn("tenantUrn")
            .userUrn("userUrn")
            .username("username")
            .passwordHash("passwordHash")
            .authorities(new HashSet<>(Arrays.asList("someAuthority", "someOtherAuthority")))
            .build();

        assertNotNull(userDetails.getVersion());

        String jsonString = mapper.writeValueAsString(userDetails);
        JSONObject jsonObject = new JSONObject(jsonString);

        assertFalse(jsonObject.has("version"));
    }

    @Test
    public void thatSerializationFieldsArePresent() throws JsonProcessingException {

        final String expectedTenantUrn = "someTenantUrn";
        final String expectedUserUrn = "someUserUrn";
        final String expectedUsername = "someUsername";
        final String expectedPasswordHash = "somePasswordHash";
        final Collection<String> expectedAuthorities = Arrays.asList("someAuthority", "someOtherAuthority");

        UserDetails userDetails = UserDetails.builder()
            .tenantUrn(expectedTenantUrn)
            .userUrn(expectedUserUrn)
            .username(expectedUsername)
            .passwordHash(expectedPasswordHash)
            .authorities(expectedAuthorities)
            .build();

        String jsonString = mapper.writeValueAsString(userDetails);
        JSONObject jsonObject = new JSONObject(jsonString);

        assertTrue(jsonObject.has(TENANT_URN));
        assertTrue(jsonObject.has(USER_URN));
        assertTrue(jsonObject.has(USERNAME));
        assertTrue(jsonObject.has(PASSWORD_HASH));
        assertTrue(jsonObject.has(AUTHORITIES));
    }

    @Test
    public void thatSerializationValuesAreSet() throws JsonProcessingException {

        final String expectedTenantUrn = "someTenantUrn";
        final String expectedUserUrn = "someUserUrn";
        final String expectedUsername = "someUsername";
        final String expectedPasswordHash = "somePasswordHash";
        final Collection<String> expectedAuthorities = Arrays.asList("someAuthority", "someOtherAuthority");

        UserDetails userDetails = UserDetails.builder()
            .tenantUrn(expectedTenantUrn)
            .userUrn(expectedUserUrn)
            .username(expectedUsername)
            .passwordHash(expectedPasswordHash)
            .authorities(expectedAuthorities)
            .build();

        String jsonString = mapper.writeValueAsString(userDetails);
        JSONObject jsonObject = new JSONObject(jsonString);

        assertEquals(expectedTenantUrn, jsonObject.get(TENANT_URN));
        assertEquals(expectedUserUrn, jsonObject.get(USER_URN));
        assertEquals(expectedUsername, jsonObject.get(USERNAME));
        assertEquals(expectedPasswordHash, jsonObject.get(PASSWORD_HASH));

        assertTrue(jsonObject.get(AUTHORITIES) instanceof JSONArray);

        JSONArray authorities = (JSONArray) jsonObject.get(AUTHORITIES);
        assertEquals(expectedAuthorities.size(), authorities.length());

        ArrayList<String> actualAuthorities = new ArrayList<>();
        for (int i = 0; i < authorities.length(); i++) {
            actualAuthorities.add(authorities.getString(i));
        }

        assertTrue(actualAuthorities.containsAll(expectedAuthorities));
    }

    @Test
    public void thatSerializationSucceedsPasswordHashIsAbsentIfEmpty() throws JsonProcessingException {

        final String expectedTenantUrn = "someTenantUrn";
        final String expectedUserUrn = "someUserUrn";
        final String expectedUsername = "someUsername";
        final String expectedPasswordHash = "";
        final Collection<String> expectedAuthorities = Arrays.asList("someAuthority", "someOtherAuthority");

        UserDetails userDetails = UserDetails.builder()
            .tenantUrn(expectedTenantUrn)
            .userUrn(expectedUserUrn)
            .username(expectedUsername)
            .passwordHash(expectedPasswordHash)
            .authorities(expectedAuthorities)
            .build();

        String jsonString = mapper.writeValueAsString(userDetails);
        JSONObject jsonObject = new JSONObject(jsonString);

        assertFalse(jsonObject.has(PASSWORD_HASH));
    }

    @Test
    public void thatSerializationSucceedsPasswordHashIsAbsentIfNull() throws JsonProcessingException {

        final String expectedTenantUrn = "someTenantUrn";
        final String expectedUserUrn = "someUserUrn";
        final String expectedUsername = "someUsername";
        final String expectedPasswordHash = null;
        final Collection<String> expectedAuthorities = Arrays.asList("someAuthority", "someOtherAuthority");

        UserDetails userDetails = UserDetails.builder()
            .tenantUrn(expectedTenantUrn)
            .userUrn(expectedUserUrn)
            .username(expectedUsername)
            .passwordHash(expectedPasswordHash)
            .authorities(expectedAuthorities)
            .build();

        String jsonString = mapper.writeValueAsString(userDetails);
        JSONObject jsonObject = new JSONObject(jsonString);

        assertFalse(jsonObject.has(PASSWORD_HASH));
    }

    // endregion
}
