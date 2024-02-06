package ru.gb.springdemo.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.gb.springdemo.config.SecurityConfig;
import ru.gb.springdemo.model.Person;
import ru.gb.springdemo.model.PersonsRoles;
import ru.gb.springdemo.service.PersonDetailService;
import ru.gb.springdemo.service.PersonRoleService;
import ru.gb.springdemo.service.PersonService;

import java.util.List;

@Component
@Slf4j
public class AuthProviderImp implements AuthenticationProvider {

    private final PersonDetailService personDetailsService;
    private final PersonRoleService personRoleService;
    private final PersonService personService;

    @Autowired
    public AuthProviderImp(PersonDetailService personDetailsService, PersonRoleService personRoleService, PersonService personService) {
        this.personDetailsService = personDetailsService;
        this.personRoleService = personRoleService;
        this.personService = personService;
    }

    /**
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        UserDetails personDetails = personDetailsService.loadUserByUsername(username);
        Person person = personService.findByName(username);
        String password = authentication.getCredentials().toString();
        log.info(personDetails.getPassword());
        if (!password.equals(personDetails.getPassword())) {
            throw new BadCredentialsException("incorrect password");
        }
        List<PersonsRoles> roles = personRoleService.findByPersonId(person);
        List<SimpleGrantedAuthority> authorities = roles.stream().map(r -> new SimpleGrantedAuthority(r.getRoleId().getName())).toList();
        log.info(authorities.toString());

        return new UsernamePasswordAuthenticationToken(personDetails, password, authorities);
    }

    /**
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

    @Autowired
    public void configureAuthManagerBuilder(AuthenticationManagerBuilder builder, PersonDetailService personDetailService) throws Exception {
        builder.userDetailsService(personDetailService)
                .passwordEncoder(SecurityConfig.getPasswordEncoder());
    }
//
//    @Bean
//    public PasswordEncoder getPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

}
