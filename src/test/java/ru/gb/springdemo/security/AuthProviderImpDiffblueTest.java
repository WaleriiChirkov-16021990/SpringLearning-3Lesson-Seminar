package ru.gb.springdemo.security;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.gb.springdemo.model.Person;
import ru.gb.springdemo.repository.PersonRepository;
import ru.gb.springdemo.repository.PersonsRolesRepository;
import ru.gb.springdemo.repository.RoleRepository;
import ru.gb.springdemo.service.PersonDetailService;
import ru.gb.springdemo.service.PersonRoleService;
import ru.gb.springdemo.service.PersonService;
import ru.gb.springdemo.service.RoleService;
import ru.gb.springdemo.util.NotFoundException;

@ContextConfiguration(classes = {AuthProviderImp.class, AuthenticationManagerBuilder.class})
@ExtendWith(SpringExtension.class)
class AuthProviderImpDiffblueTest {
    @Autowired
    private AuthProviderImp authProviderImp;

    @MockBean
    private ObjectPostProcessor objectPostProcessor;

    @MockBean
    private PersonDetailService personDetailService;

    @MockBean
    private PersonRoleService personRoleService;

    @MockBean
    private PersonService personService;

    /**
     * Method under test: {@link AuthProviderImp#authenticate(Authentication)}
     */
    @Test
    void testAuthenticate() throws AuthenticationException, NotFoundException {
        // Arrange
        when(personDetailService.loadUserByUsername(Mockito.<String>any())).thenReturn(new UserDetail(new Person()));

        Person person = new Person();
        person.setId(UUID.randomUUID());
        person.setName("Name");
        person.setPassword("iloveyou");
        person.setRole(new ArrayList<>());
        when(personService.findByName(Mockito.<String>any())).thenReturn(person);

        // Act and Assert
        assertThrows(BadCredentialsException.class,
                () -> authProviderImp.authenticate(new TestingAuthenticationToken("Principal", "Credentials")));
        verify(personDetailService).loadUserByUsername(Mockito.<String>any());
        verify(personService).findByName(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AuthProviderImp#authenticate(Authentication)}
     */
    @Test
    void testAuthenticate2() throws AuthenticationException, NotFoundException {
        // Arrange
        when(personDetailService.loadUserByUsername(Mockito.<String>any())).thenReturn(new UserDetail(new Person()));
        when(personService.findByName(Mockito.<String>any())).thenThrow(new BadCredentialsException("incorrect password"));

        // Act and Assert
        assertThrows(BadCredentialsException.class,
                () -> authProviderImp.authenticate(new TestingAuthenticationToken("Principal", "Credentials")));
        verify(personDetailService).loadUserByUsername(Mockito.<String>any());
        verify(personService).findByName(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AuthProviderImp#authenticate(Authentication)}
     */
    @Test
    void testAuthenticate3() throws AuthenticationException, NotFoundException {
        // Arrange
        when(personDetailService.loadUserByUsername(Mockito.<String>any())).thenReturn(new UserDetail(new Person()));

        Person person = new Person();
        person.setId(UUID.randomUUID());
        person.setName("Name");
        person.setPassword("iloveyou");
        person.setRole(new ArrayList<>());
        when(personService.findByName(Mockito.<String>any())).thenReturn(person);

        // Act and Assert
        assertThrows(BadCredentialsException.class,
                () -> authProviderImp.authenticate(new UsernamePasswordAuthenticationToken("Principal", "Credentials")));
        verify(personDetailService).loadUserByUsername(Mockito.<String>any());
        verify(personService).findByName(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AuthProviderImp#supports(Class)}
     */
    @Test
    void testSupports() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R026 Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@aca673c testClass = ru.gb.springdemo.security.DiffblueFakeClass256, locations = [], classes = [ru.gb.springdemo.security.AuthProviderImp, org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@4c3a33d7, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@1e4ab019, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@f28a1f5c, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@4245dbc0], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
        //       at java.base/java.util.Optional.map(Optional.java:260)
        //   See https://diff.blue/R026 to resolve this issue.

        // Arrange
        PersonRepository personRepository = mock(PersonRepository.class);
        PersonRoleService personRoleService = new PersonRoleService(mock(PersonsRolesRepository.class));
        PersonDetailService personDetailsService = new PersonDetailService(
                new PersonService(personRepository, personRoleService, new RoleService(mock(RoleRepository.class))));
        PersonRoleService personRoleService2 = new PersonRoleService(mock(PersonsRolesRepository.class));
        PersonRepository personRepository2 = mock(PersonRepository.class);
        PersonRoleService personRoleService3 = new PersonRoleService(mock(PersonsRolesRepository.class));
        AuthProviderImp authProviderImp = new AuthProviderImp(personDetailsService, personRoleService2,
                new PersonService(personRepository2, personRoleService3, new RoleService(mock(RoleRepository.class))));
        Class<Object> authentication = Object.class;

        // Act and Assert
        assertTrue(authProviderImp.supports(authentication));
    }

    /**
     * Method under test:
     * {@link AuthProviderImp#configureAuthManagerBuilder(AuthenticationManagerBuilder, PersonDetailService)}
     */
    @Test
    void testConfigureAuthManagerBuilder() throws Exception {
        // Arrange
        // TODO: Populate arranged inputs
        AuthenticationManagerBuilder builder = null;
        PersonDetailService personDetailService = null;

        // Act
        this.authProviderImp.configureAuthManagerBuilder(builder, personDetailService);

        // Assert
        // TODO: Add assertions on result
    }
}
