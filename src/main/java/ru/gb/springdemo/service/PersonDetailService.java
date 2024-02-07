package ru.gb.springdemo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.gb.springdemo.model.Person;
import ru.gb.springdemo.security.UserDetail;

@Service
@Slf4j
public class PersonDetailService implements UserDetailsService {
    private final PersonService personService;

    @Autowired
    public PersonDetailService(PersonService personService) {
        this.personService = personService;
    }

//    private final RoleService roleService;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Person user = userDetailsService().userExists(username) ? (Person) userDetailsService().loadUserByUsername(username) : repository.findByNameIgnoreCase(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Person user = personService.findPersonByName(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
//        user.setRole(personService.getRoleByPerson(user));
//        return new UserDetail(user);
        log.info("User details loaded: {}", user);
        log.info("User  roles: {}", user.getRole().get(0).getName());
        return new UserDetail(user);
    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        UserDetails user1 = User.withUsername("John")
//                .password(getPasswordEncoder().encode("password"))
//                .roles("ISSUE")
//                .build();
//
//        UserDetails user2 = User.withUsername("Jane")
//                .password(getPasswordEncoder().encode("password"))
//                .roles("READER")
//                .build();
//
//        UserDetails user3 = User.withUsername("Jack")
//                .password(getPasswordEncoder().encode("password"))
//                .roles("BOOK")
//                .build();
//
//        return new InMemoryUserDetailsManager(user1, user2, user3);
//    }
}
