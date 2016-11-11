package net.smartcosmos.userdetails.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import net.smartcosmos.annotation.SmartCosmosRdao;
import net.smartcosmos.userdetails.service.AuthenticateUserService;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import static net.smartcosmos.userdetails.util.ResponseEntityFactory.invalidUsernameOrPassword;

@SmartCosmosRdao
public class ActiveResource {

    private final AuthenticateUserService service;

    @Autowired
    public ActiveResource(AuthenticateUserService authenticateUserService) {

        service = authenticateUserService;
    }

    @RequestMapping(value = "active/{username}",
                    produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> authenticate(@PathVariable("username") String username) {

        return service.isUserActive(username);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> invalidRequest() {

        return invalidUsernameOrPassword();
    }
}
