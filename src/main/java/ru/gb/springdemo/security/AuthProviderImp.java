package ru.gb.springdemo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.gb.springdemo.model.Person;
import ru.gb.springdemo.model.PersonsRoles;
import ru.gb.springdemo.service.PersonDetailService;
import ru.gb.springdemo.service.PersonRoleService;
import ru.gb.springdemo.service.PersonService;

import java.util.List;

@Component
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

        if (!password.equals(personDetails.getPassword())) {
            throw new BadCredentialsException("incorrect password");
        }
        List<PersonsRoles> roles = personRoleService.findByPersonId(person);
        List<SimpleGrantedAuthority> authorities = roles.stream().map(r -> new SimpleGrantedAuthority(r.getRoleId().getName())).toList();

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
}
