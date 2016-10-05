package net.smartcosmos.userdetails.autoconfigure;

import javax.validation.Validator;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import net.smartcosmos.test.config.UserDetailsAutoConfigurationTestConfiguration;
import net.smartcosmos.userdetails.service.AuthenticateUserService;
import net.smartcosmos.userdetails.service.AuthenticateUserServiceDefault;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { UserDetailsAutoConfigurationTestConfiguration.class })
public class UserDetailsAutoConfigurationTest {

    @Autowired
    AuthenticateUserService authenticateUserService;

    @Autowired
    Validator validator;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void thatApplicationContextLoads() {}

    @Test
    public void thatAuthenticateUserServiceExists() {

        assertNotNull(authenticateUserService);
    }

    @Test
    public void thatAuthenticateUserServiceIsDefault() {

        assertTrue(authenticateUserService instanceof AuthenticateUserServiceDefault);
    }

    @Test
    public void thatValidatorExists() {

        assertNotNull(validator);
    }

    @Test
    public void thatValidatorIsLocalValidatorFactoryBean() {

        assertTrue(validator instanceof LocalValidatorFactoryBean);
    }

    @Test
    public void thatPasswordEncoderExists() {

        assertNotNull(passwordEncoder);
    }

    @Test
    public void thatPasswordEncoderIsBCrypt() {

        assertTrue(passwordEncoder instanceof BCryptPasswordEncoder);
    }
}
