package net.smartcosmos.userdetails.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import net.smartcosmos.domain.rest.ErrorResponse;
import net.smartcosmos.userdetails.domain.UserDetails;

import static net.smartcosmos.domain.rest.ErrorResponse.CODE_ERROR;

/**
 * Utility class for default Response Entity creation.
 */
public class ResponseEntityFactory {

    /**
     * Success response for user details.
     *
     * @param userDetails the user details
     * @return the response entity
     */
    public static ResponseEntity<?> success(UserDetails userDetails) {

        return buildResponse(HttpStatus.OK, userDetails);
    }

    /**
     * Bad Request error response for invalid user credentials.
     * <pre>{ "code": 1, "message": "Invalid username or password" }</pre>
     *
     * @return the response entity
     */
    public static ResponseEntity<?> invalidUsernameOrPassword() {

        return buildResponse(HttpStatus.BAD_REQUEST, new ErrorResponse(CODE_ERROR, "Invalid username or password"));
    }

    /**
     * Internal Server Error response for invalid data retrieved from the user details provider.
     * <pre>{ "code": 1, "message": "Invalid data returned" }</pre>
     *
     * @return the response entity
     */
    public static ResponseEntity<?> invalidDataReturned() {

        return buildResponse(HttpStatus.BAD_REQUEST, new ErrorResponse(CODE_ERROR, "Invalid data returned"));
    }

    /**
     * General error response.
     * <pre>{ "code": code, "message": message }</pre>
     *
     * @param httpStatus the HTTP status code
     * @param code the error code
     * @param message the error message
     * @return the response entity
     */
    public static ResponseEntity<?> errorResponse(HttpStatus httpStatus, Integer code, String message) {

        return buildResponse(httpStatus, new ErrorResponse(code, message));
    }

    /**
     * Internal helper method that builds the actual response entity including {@code Content-Type} header.
     *
     * @param httpStatus the HTTP status code
     * @param responseBody the response body
     * @return the response entity
     */
    protected static ResponseEntity<?> buildResponse(HttpStatus httpStatus, Object responseBody) {

        return ResponseEntity.status(httpStatus)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .body(responseBody);
    }
}
