package net.smartcosmos.userdetails.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.smartcosmos.annotation.SmartCosmosRdao;
import net.smartcosmos.userdetails.domain.rest.AuthenticateRequest;
import net.smartcosmos.userdetails.service.AuthenticateUserService;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import static net.smartcosmos.userdetails.util.ResponseEntityFactory.invalidUsernameOrPassword;

@SmartCosmosRdao
public class AuthenticateResource {

    private final AuthenticateUserService service;

    @Autowired
    public AuthenticateResource(AuthenticateUserService authenticateUserService) {

        service = authenticateUserService;
    }

    @RequestMapping(value = "authenticate",
                    method = RequestMethod.POST,
                    produces = APPLICATION_JSON_UTF8_VALUE,
                    consumes = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> authenticate(@RequestBody @Valid AuthenticateRequest requestBody) {

        return service.authenticateUser(requestBody);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> invalidRequest() {

        return invalidUsernameOrPassword();
    }
}
