package net.smartcosmos.test.config;

import javax.validation.Validator;

import org.mockito.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;

import net.smartcosmos.userdetails.service.AuthenticateUserService;
import net.smartcosmos.userdetails.service.UserDetailsService;

@SpringBootApplication
@ComponentScan(basePackages = "net.smartcosmos.userdetails")
public class UserDetailsAutoConfigurationNonDefaultTestConfiguration {

    @Bean
    public UserDetailsService userDetailsService() {

        return Mockito.mock(UserDetailsService.class);
    }

    @Bean
    public AuthenticateUserService authenticateUserService() {

        return Mockito.mock(AuthenticateUserService.class);
    }

    @Bean
    public Validator validator() {

        return Mockito.mock(Validator.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return Mockito.mock(PasswordEncoder.class);
    }

}
