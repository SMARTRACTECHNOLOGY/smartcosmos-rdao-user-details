package net.smartcosmos.userdetails.domain;

import java.beans.ConstructorProperties;
import java.util.Collection;
import java.util.HashSet;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import org.hibernate.validator.constraints.NotEmpty;

import static net.smartcosmos.userdetails.domain.UserDetails.PASSWORD_HASH;

/**
 * This is the response from the User Details Service that will contain the necessary
 * information for caching purposes. While not required, if the password hash is filled
 * this will speed up authentication considerably, since it can be queried against the
 * native Spring Security Cache.
 */
@Data
@ToString(exclude = PASSWORD_HASH)
@JsonIgnoreProperties({ "version" })
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserDetails {

    public static final String TENANT_URN = "tenantUrn";
    public static final String USER_URN = "userUrn";
    public static final String USERNAME = "username";
    public static final String PASSWORD_HASH = "passwordHash";
    public static final String AUTHORITIES = "authorities";

    private static final int VERSION = 1;
    private final int version = VERSION;

    @NotEmpty
    @JsonProperty(TENANT_URN)
    private final String tenantUrn;

    @NotEmpty
    @JsonProperty(USER_URN)
    private final String userUrn;

    @NotEmpty
    @JsonProperty(USERNAME)
    private final String username;

    @JsonProperty(PASSWORD_HASH)
    private String passwordHash;

    @NotNull
    @JsonProperty(AUTHORITIES)
    private final Collection<String> authorities;

    @Builder
    @ConstructorProperties({ TENANT_URN, USER_URN, USERNAME, PASSWORD_HASH, AUTHORITIES })
    public UserDetails(String tenantUrn, String userUrn, String username, String passwordHash, Collection<String> authorities) {

        this.tenantUrn = tenantUrn;
        this.userUrn = userUrn;
        this.username = username;
        this.passwordHash = passwordHash;

        this.authorities = new HashSet<>();
        if (authorities != null && !authorities.isEmpty()) {
            this.authorities.addAll(authorities);
        }
    }
}
