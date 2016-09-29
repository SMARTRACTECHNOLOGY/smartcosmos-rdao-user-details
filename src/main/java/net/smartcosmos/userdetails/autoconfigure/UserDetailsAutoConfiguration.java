package net.smartcosmos.userdetails.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.smartcosmos.userdetails.service.AuthenticateUserService;
import net.smartcosmos.userdetails.service.AuthenticateUserServiceDefault;
import net.smartcosmos.userdetails.service.UserDetailsService;

/**
 * Auto-configuration that creates default beans if no other versions exist.
 */
@Configuration
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

        return new AuthenticateUserServiceDefault(userDetailsService);
    }
}
