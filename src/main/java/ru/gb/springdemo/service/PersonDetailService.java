package ru.gb.springdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;
import ru.gb.springdemo.config.SecurityConfig;
import ru.gb.springdemo.model.Person;
import ru.gb.springdemo.security.UserDetail;
import ru.gb.springdemo.repository.PersonRepository;

import static ru.gb.springdemo.config.SecurityConfig.getPasswordEncoder;

@Service
public class PersonDetailService implements UserDetailsService {
    private final PersonRepository repository;
//    private final InMemoryUserDetailsManager userDetailsManager;

    @Autowired
    public PersonDetailService(PersonRepository repository) {
        this.repository = repository;
//        this.userDetailsManager = userDetailsManager;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Person user = userDetailsService().userExists(username) ? (Person) userDetailsService().loadUserByUsername(username) : repository.findByNameIgnoreCase(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Person user = repository.findByNameIgnoreCase(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
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
