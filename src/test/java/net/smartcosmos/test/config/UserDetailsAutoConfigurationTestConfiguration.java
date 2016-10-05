package net.smartcosmos.test.config;

import org.mockito.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import net.smartcosmos.userdetails.service.UserDetailsService;

@SpringBootApplication
@ComponentScan(basePackages = "net.smartcosmos.userdetails")
public class UserDetailsAutoConfigurationTestConfiguration {

    @Bean
    public UserDetailsService userDetailsService() {

        return Mockito.mock(UserDetailsService.class);
    }
}
