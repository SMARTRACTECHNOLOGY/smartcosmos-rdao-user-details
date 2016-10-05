package net.smartcosmos.userdetails.resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.internal.matchers.GreaterThan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import net.smartcosmos.test.config.UserDetailsResourceTestConfiguration;
import net.smartcosmos.userdetails.domain.UserDetails;
import net.smartcosmos.userdetails.domain.rest.AuthenticateDetails;
import net.smartcosmos.userdetails.domain.rest.AuthenticateRequest;
import net.smartcosmos.userdetails.service.AuthenticateUserService;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static net.smartcosmos.domain.rest.ErrorResponse.CODE_ERROR;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { UserDetailsResourceTestConfiguration.class })
@WebAppConfiguration
@ActiveProfiles("test")
public class AuthenticateResourceTest {

    @Autowired
    AuthenticateUserService authenticateUserService;

    // region setup

    @Autowired
    protected WebApplicationContext webApplicationContext;
    protected MockMvc mockMvc;
    protected HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays
            .stream(converters)
            .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
            .findAny()
            .get();
    }

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        this.mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .apply(springSecurity())
            .build();
    }

    protected String json(Object o) throws IOException {

        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON,
                                                       mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

    // endregion

    @Test
    public void thatApplicationContextLoads() {}

    @Test
    public void thatAuthenticateSucceeds() throws Exception {

        final String tenantUrn = "urn:tenant:uuid:DAF0D088-75A5-4C65-B331-24F26A30A331";
        final String userUrn = "urn:user:uuid:6E3718FA-3DDD-4079-89C4-D401FAC78CA1";
        final String username = "jules";
        final String password = "hotpassword";
        final String grantType = "credentials";
        final String requestScope = "read";

        final UserDetails expectedUserDetails = UserDetails.builder()
            .tenantUrn(tenantUrn)
            .userUrn(userUrn)
            .username(username)
            .passwordHash("passwordHash")
            .authorities(new HashSet<>(Arrays.asList("someAuthority", "someOtherAuthority")))
            .build();

        AuthenticateDetails details = AuthenticateDetails.builder()
            .grantType(grantType)
            .scope(requestScope)
            .username(username)
            .build();

        AuthenticateRequest request = AuthenticateRequest.builder()
            .authorities(new ArrayList<>())
            .authenticated(false)
            .principal(username)
            .credentials(password)
            .name(username)
            .details(details)
            .build();

        final ResponseEntity expectedResponse = ResponseEntity.ok(expectedUserDetails);
        when(authenticateUserService.authenticateUser(eq(request))).thenReturn(expectedResponse);

        MvcResult mvcResult = this.mockMvc.perform(
            post("/authenticate")
                .content(this.json(request))
                .contentType(APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.tenantUrn", is(tenantUrn)))
            .andExpect(jsonPath("$.userUrn", is(userUrn)))
            .andExpect(jsonPath("$.username", is(username)))
            .andExpect(jsonPath("$.passwordHash", not(isEmptyOrNullString())))
            .andExpect(jsonPath("$.authorities", hasSize(new GreaterThan<>(0))))
            .andReturn();
    }

    @Test
    public void thatMissingUsernameReturnsBadRequest() throws Exception {

        final String username = null;
        final String password = "hotpassword";
        final String grantType = "credentials";
        final String requestScope = "read";

        AuthenticateDetails details = AuthenticateDetails.builder()
            .grantType(grantType)
            .scope(requestScope)
            .username(username)
            .build();

        AuthenticateRequest request = AuthenticateRequest.builder()
            .authorities(new ArrayList<>())
            .authenticated(false)
            .principal(username)
            .credentials(password)
            .name(username)
            .details(details)
            .build();

        MvcResult mvcResult = this.mockMvc.perform(
            post("/authenticate")
                .content(this.json(request))
                .contentType(APPLICATION_JSON_UTF8))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.code", is(CODE_ERROR)))
            .andExpect(jsonPath("$.message").isString())
            .andReturn();
    }

    @Test
    public void thatEmptyUsernameReturnsBadRequest() throws Exception {

        final String username = "";
        final String password = "hotpassword";
        final String grantType = "credentials";
        final String requestScope = "read";

        AuthenticateDetails details = AuthenticateDetails.builder()
            .grantType(grantType)
            .scope(requestScope)
            .username(username)
            .build();

        AuthenticateRequest request = AuthenticateRequest.builder()
            .authorities(new ArrayList<>())
            .authenticated(false)
            .principal(username)
            .credentials(password)
            .name(username)
            .details(details)
            .build();

        MvcResult mvcResult = this.mockMvc.perform(
            post("/authenticate")
                .content(this.json(request))
                .contentType(APPLICATION_JSON_UTF8))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.code", is(CODE_ERROR)))
            .andExpect(jsonPath("$.message").isString())
            .andReturn();
    }

    @Test
    public void thatMissingPasswordReturnsBadRequest() throws Exception {

        final String username = "jules";
        final String password = null;
        final String grantType = "credentials";
        final String requestScope = "read";

        AuthenticateDetails details = AuthenticateDetails.builder()
            .grantType(grantType)
            .scope(requestScope)
            .username(username)
            .build();

        AuthenticateRequest request = AuthenticateRequest.builder()
            .authorities(new ArrayList<>())
            .authenticated(false)
            .principal(username)
            .credentials(password)
            .name(username)
            .details(details)
            .build();

        MvcResult mvcResult = this.mockMvc.perform(
            post("/authenticate")
                .content(this.json(request))
                .contentType(APPLICATION_JSON_UTF8))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.code", is(CODE_ERROR)))
            .andExpect(jsonPath("$.message").isString())
            .andReturn();
    }

    @Test
    public void thatEmptyPasswordReturnsBadRequest() throws Exception {

        final String username = "jules";
        final String password = "";
        final String grantType = "credentials";
        final String requestScope = "read";

        AuthenticateDetails details = AuthenticateDetails.builder()
            .grantType(grantType)
            .scope(requestScope)
            .username(username)
            .build();

        AuthenticateRequest request = AuthenticateRequest.builder()
            .authorities(new ArrayList<>())
            .authenticated(false)
            .principal(username)
            .credentials(password)
            .name(username)
            .details(details)
            .build();

        MvcResult mvcResult = this.mockMvc.perform(
            post("/authenticate")
                .content(this.json(request))
                .contentType(APPLICATION_JSON_UTF8))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.code", is(CODE_ERROR)))
            .andExpect(jsonPath("$.message").isString())
            .andReturn();
    }
}
