package net.smartcosmos.userdetails.domain.rest;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * DTO representation of a serialized {@link UsernamePasswordAuthenticationToken}.
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString(exclude = "credentials")  // constants don't work here
public class AuthenticateRequest {

    public static final String DETAILS = "details";
    public static final String AUTHORITIES = "authorities";
    public static final String AUTHENTICATED = "authenticated";
    public static final String PRINCIPAL = "principal";
    public static final String CREDENTIALS = "credentials";
    public static final String NAME = "name";

    private static final int VERSION_1 = 1;
    private final int version = VERSION_1;

    @JsonProperty(DETAILS)
    private AuthenticateDetails details;

    @JsonProperty(AUTHORITIES)
    private List<String> authorities;

    @JsonProperty(AUTHENTICATED)
    private Boolean authenticated;

    @JsonProperty(PRINCIPAL)
    private String principal;

    @NotEmpty
    @JsonProperty(CREDENTIALS)
    private String credentials;

    @NotEmpty
    @JsonProperty(NAME)
    private String name;
}
