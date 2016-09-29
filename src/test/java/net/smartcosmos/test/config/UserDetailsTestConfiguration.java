package net.smartcosmos.test.config;

import org.mockito.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import net.smartcosmos.userdetails.service.AuthenticateUserService;

@SpringBootApplication
@ComponentScan(basePackages = "net.smartcosmos")
public class UserDetailsTestConfiguration {

    @Bean
    public AuthenticateUserService authenticateUserService() {

        return Mockito.mock(AuthenticateUserService.class);
    }
}
