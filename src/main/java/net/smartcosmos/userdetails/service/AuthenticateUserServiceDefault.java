package net.smartcosmos.userdetails.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;

import net.smartcosmos.userdetails.domain.UserDetails;
import net.smartcosmos.userdetails.domain.rest.AuthenticateRequest;

import static net.smartcosmos.userdetails.util.ResponseEntityFactory.invalidDataReturned;
import static net.smartcosmos.userdetails.util.ResponseEntityFactory.invalidUsernameOrPassword;
import static net.smartcosmos.userdetails.util.ResponseEntityFactory.success;

/**
 * Default implementation of the Authenticate User Service.
 */
@Slf4j
public class AuthenticateUserServiceDefault implements AuthenticateUserService {

    private final UserDetailsService userDetailsService;

    @Autowired
    public AuthenticateUserServiceDefault(UserDetailsService userDetailsService) {

        this.userDetailsService = userDetailsService;
    }

    @Override
    public ResponseEntity<?> authenticateUser(AuthenticateRequest request) {

        log.debug("Requested information on username {} with {}", request.getName(), request);

        try {
            UserDetails userDetails = userDetailsService.getUserDetails(request.getName(), request.getCredentials());

            if (userDetailsService.isValid(userDetails)) {
                log.info("Validation of authentication response for user {} : valid", request.getName());
                return success(userDetails);
            }
            log.info("Validation of authentication response for user {} : invalid", request.getName());
            return invalidDataReturned();
        } catch (AuthenticationException e) {
            log.info("Authenticating user {} failed. Request was {}. Exception: {}", request.getName(), request, e.toString());
            return invalidUsernameOrPassword();
        }
    }
}
