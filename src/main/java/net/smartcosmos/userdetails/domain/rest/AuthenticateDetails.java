package net.smartcosmos.userdetails.domain.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticateDetails {

    public static final String GRANT_TYPE = "grant_type";
    public static final String SCOPE = "scope";
    public static final String USERNAME = "username";

    private static final int VERSION = 1;
    private final int version = VERSION;

    @JsonProperty(GRANT_TYPE)
    private String grantType;

    @JsonProperty(SCOPE)
    private String scope;

    @JsonProperty(USERNAME)
    private String username;
}
