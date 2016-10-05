package net.smartcosmos.userdetails.autoconfigure;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import net.smartcosmos.userdetails.service.AuthenticateUserService;
import net.smartcosmos.userdetails.service.AuthenticateUserServiceDefault;
import net.smartcosmos.userdetails.service.UserDetailsService;

/**
 * Auto-configuration that creates default beans if no other versions exist.
 */
@Configuration
@ComponentScan(basePackages = "net.smartcosmos.userdetails")
@Slf4j
public class UserDetailsAutoConfiguration {

    /**
     * If no custom implementation of {@link AuthenticateUserService} is available, use the default.
     *
     * @param userDetailsService the user details service
     * @return the AuthenticateUserService bean
     */
    @Bean
    @ConditionalOnMissingBean
    @Autowired
    public AuthenticateUserService authenticateUserService(UserDetailsService userDetailsService) {

        log.info("Default AuthenticateUserService being started.");
        return new AuthenticateUserServiceDefault(userDetailsService);
    }

    /**
     * If no Validator is available, use the {@link LocalValidatorFactoryBean}.
     *
     * @return the Validator bean
     */
    @Bean
    @ConditionalOnMissingBean
    public javax.validation.Validator localValidatorFactoryBean() {

        log.info("LocalValidatorFactoryBean being started.");
        return new LocalValidatorFactoryBean();
    }

    /**
     * If no Password Encoder is available, use the {@link BCryptPasswordEncoder}.
     *
     * @return the Password Encoder bean
     */
    @Bean
    @ConditionalOnMissingBean
    PasswordEncoder passwordEncoder() {

        log.info("BCryptPasswordEncoder being started.");
        return new BCryptPasswordEncoder();
    }
}
