package net.smartcosmos.userdetails.domain.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static net.smartcosmos.userdetails.domain.rest.AuthenticateDetails.PASSWORD;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString(exclude = PASSWORD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticateDetails {

    public static final String GRANT_TYPE = "grant_type";
    public static final String PASSWORD = "password";
    public static final String SCOPE = "scope";
    public static final String USERNAME = "username";

    private static final int VERSION = 1;
    private final int version = VERSION;

    @JsonProperty(GRANT_TYPE)
    private String grantType;

    @JsonProperty(PASSWORD)
    private String password;

    @JsonProperty(SCOPE)
    private String scope;

    @JsonProperty(USERNAME)
    private String username;
}
