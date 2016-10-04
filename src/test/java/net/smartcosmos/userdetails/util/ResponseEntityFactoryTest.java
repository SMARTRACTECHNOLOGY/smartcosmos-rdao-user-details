package net.smartcosmos.userdetails.util;

import org.junit.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import net.smartcosmos.domain.rest.ErrorResponse;
import net.smartcosmos.userdetails.domain.UserDetails;

import static org.junit.Assert.*;

import static net.smartcosmos.domain.rest.ErrorResponse.CODE_ERROR;

public class ResponseEntityFactoryTest {

    // region Success Response

    @Test
    public void thatSuccessResponseIsOk() {

        final UserDetails userDetails = UserDetails.builder()
            .build();

        ResponseEntity responseEntity = ResponseEntityFactory.success(userDetails);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void thatSuccessResponseReturnsContentTypeJson() {

        final UserDetails userDetails = UserDetails.builder()
            .build();

        ResponseEntity responseEntity = ResponseEntityFactory.success(userDetails);

        assertEquals(MediaType.APPLICATION_JSON_UTF8,
                     responseEntity.getHeaders()
                         .getContentType());
    }

    @Test
    public void thatSuccessResponseContainsBody() {

        final UserDetails userDetails = UserDetails.builder()
            .build();

        ResponseEntity responseEntity = ResponseEntityFactory.success(userDetails);

        assertEquals(userDetails, responseEntity.getBody());
    }

    // endregion

    // region Bad Credentials Response

    @Test
    public void thatInvalidUsernameOrPasswordReturnsBadRequest() {

        ResponseEntity responseEntity = ResponseEntityFactory.invalidUsernameOrPassword();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void thatInvalidUsernameOrPasswordReturnsContentTypeJson() {

        ResponseEntity responseEntity = ResponseEntityFactory.invalidUsernameOrPassword();

        assertEquals(MediaType.APPLICATION_JSON_UTF8,
                     responseEntity.getHeaders()
                         .getContentType());
    }

    @Test
    public void thatInvalidUsernameOrPasswordContainsBody() {

        final ErrorResponse excpectedErrorResponse = new ErrorResponse(CODE_ERROR, "Invalid username or password");

        ResponseEntity responseEntity = ResponseEntityFactory.invalidUsernameOrPassword();

        assertEquals(excpectedErrorResponse, responseEntity.getBody());
    }

    // endregion

    // region Invalid Data returned

    @Test
    public void thatInvalidDataReturnsBadRequest() {

        ResponseEntity responseEntity = ResponseEntityFactory.invalidDataReturned();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void thatInvalidDataReturnsContentTypeJson() {

        ResponseEntity responseEntity = ResponseEntityFactory.invalidDataReturned();

        assertEquals(MediaType.APPLICATION_JSON_UTF8,
                     responseEntity.getHeaders()
                         .getContentType());
    }

    @Test
    public void thatInvalidDataContainsBody() {

        final ErrorResponse excpectedErrorResponse = new ErrorResponse(CODE_ERROR, "Invalid data returned");

        ResponseEntity responseEntity = ResponseEntityFactory.invalidDataReturned();

        assertEquals(excpectedErrorResponse, responseEntity.getBody());
    }

    // endregion

    // region Error Response

    @Test
    public void thatErrorResponseReturnsGivenHttpCode() {

        final HttpStatus expectedHttpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        final Integer expectedCode = CODE_ERROR;
        final String expectedMessage = "someMessage";

        ResponseEntity responseEntity = ResponseEntityFactory.errorResponse(expectedHttpStatus, expectedCode, expectedMessage);

        assertEquals(expectedHttpStatus, responseEntity.getStatusCode());
    }

    @Test
    public void thatErrorResponseReturnsContentTypeJson() {

        final HttpStatus expectedHttpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        final Integer expectedCode = CODE_ERROR;
        final String expectedMessage = "someMessage";

        ResponseEntity responseEntity = ResponseEntityFactory.errorResponse(expectedHttpStatus, expectedCode, expectedMessage);

        assertEquals(MediaType.APPLICATION_JSON_UTF8,
                     responseEntity.getHeaders()
                         .getContentType());
    }

    @Test
    public void thatErrorResponseContainsBody() {

        final HttpStatus expectedHttpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        final Integer expectedCode = CODE_ERROR;
        final String expectedMessage = "someMessage";
        final ErrorResponse expectedResponseBody = new ErrorResponse(expectedCode, expectedMessage);

        ResponseEntity responseEntity = ResponseEntityFactory.errorResponse(expectedHttpStatus, expectedCode, expectedMessage);

        assertTrue(responseEntity.getBody() instanceof ErrorResponse);
        assertEquals(expectedResponseBody, responseEntity.getBody());
    }

    // endregion

    // region Build Response

    @Test
    public void thatBuildResponseReturnsGivenHttpCode() {

        final HttpStatus expectedHttpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        final String expectedResponseBody = "someMessage";

        ResponseEntity responseEntity = ResponseEntityFactory.buildResponse(expectedHttpStatus, expectedResponseBody);

        assertEquals(expectedHttpStatus, responseEntity.getStatusCode());
    }

    @Test
    public void thatBuildResponseReturnsContentTypeJson() {

        final HttpStatus expectedHttpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        final String expectedResponseBody = "someMessage";

        ResponseEntity responseEntity = ResponseEntityFactory.buildResponse(expectedHttpStatus, expectedResponseBody);

        assertEquals(MediaType.APPLICATION_JSON_UTF8,
                     responseEntity.getHeaders()
                         .getContentType());
    }

    @Test
    public void thatBuildResponseContainsBody() {

        final HttpStatus expectedHttpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        final String expectedResponseBody = "someMessage";

        ResponseEntity responseEntity = ResponseEntityFactory.buildResponse(expectedHttpStatus, expectedResponseBody);

        assertEquals(expectedResponseBody, responseEntity.getBody());
    }

    // endregion
}
