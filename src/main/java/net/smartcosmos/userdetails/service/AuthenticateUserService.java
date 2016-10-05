package net.smartcosmos.userdetails.service;

import org.springframework.http.ResponseEntity;

import net.smartcosmos.userdetails.domain.rest.AuthenticateRequest;

/**
 * Interface for User Authentication Service implementations.
 */
public interface AuthenticateUserService {

    /**
     * Gets the User Details for a user authentication request.
     *
     * @param request the user authentication request
     * @return the response entity
     */
    ResponseEntity<?> authenticateUser(AuthenticateRequest request);
}
