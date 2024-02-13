package ru.gb.springdemo.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.HashMap;
import javax.sql.DataSource;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.gb.springdemo.repository.PersonRepository;
import ru.gb.springdemo.repository.PersonsRolesRepository;
import ru.gb.springdemo.repository.RoleRepository;
import ru.gb.springdemo.service.PersonDetailService;
import ru.gb.springdemo.service.PersonRoleService;
import ru.gb.springdemo.service.PersonService;
import ru.gb.springdemo.service.RoleService;

@ContextConfiguration(classes = {SecurityConfig.class})
@ExtendWith(SpringExtension.class)
class SecurityConfigDiffblueTest {
    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private PersonDetailService personDetailService;

    @Autowired
    private SecurityConfig securityConfig;

    /**
     * Method under test: {@link SecurityConfig#jdbcUserDetailsManager(DataSource)}
     */
    @Test
    void testJdbcUserDetailsManager() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R026 Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@abd8f83 testClass = ru.gb.springdemo.config.DiffblueFakeClass286, locations = [], classes = [ru.gb.springdemo.config.SecurityConfig, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@54c804ed, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@e65cdd5, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@563357af, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@458dd85b], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
        //       at java.base/java.util.Optional.map(Optional.java:260)
        //   See https://diff.blue/R026 to resolve this issue.

        // Arrange
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        PersonRepository personRepository = mock(PersonRepository.class);
        PersonRoleService personRoleService = new PersonRoleService(mock(PersonsRolesRepository.class));
        DataSource dataSource = mock(DataSource.class);

        // Act
        JdbcUserDetailsManager actualJdbcUserDetailsManagerResult = (new SecurityConfig(passwordEncoder,
                new PersonDetailService(
                        new PersonService(personRepository, personRoleService, new RoleService(mock(RoleRepository.class))))))
                .jdbcUserDetailsManager(dataSource);

        // Assert
        assertEquals("select username,password,enabled from users where username = ?",
                actualJdbcUserDetailsManagerResult.getUsersByUsernameQuery());
        JdbcTemplate jdbcTemplate = actualJdbcUserDetailsManagerResult.getJdbcTemplate();
        assertEquals(-1, jdbcTemplate.getFetchSize());
        assertEquals(-1, jdbcTemplate.getMaxRows());
        assertEquals(-1, jdbcTemplate.getQueryTimeout());
        assertFalse(jdbcTemplate.isResultsMapCaseInsensitive());
        assertFalse(jdbcTemplate.isSkipResultsProcessing());
        assertFalse(jdbcTemplate.isSkipUndeclaredResults());
        assertTrue(jdbcTemplate.isIgnoreWarnings());
        assertTrue(jdbcTemplate.isLazyInit());
        assertSame(dataSource, actualJdbcUserDetailsManagerResult.getDataSource());
        assertSame(dataSource, jdbcTemplate.getDataSource());
    }

    /**
     * Method under test: {@link SecurityConfig#filterChain(HttpSecurity)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testFilterChain() throws Exception {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException: objectPostProcessor cannot be null
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        AuthenticationManagerBuilder authenticationBuilder = new AuthenticationManagerBuilder(null);

        // Act
        securityConfig.filterChain(new HttpSecurity(null, authenticationBuilder, new HashMap<>()));
    }

    /**
     * Method under test: {@link SecurityConfig#getPasswordEncoder()}
     */
    @Test
    void testGetPasswordEncoder() {
        // Arrange, Act and Assert
        assertTrue(SecurityConfig.getPasswordEncoder() instanceof BCryptPasswordEncoder);
    }
}
