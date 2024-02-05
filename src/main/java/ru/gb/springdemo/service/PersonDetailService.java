package ru.gb.springdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.gb.springdemo.model.Person;
import ru.gb.springdemo.security.UserDetail;
import ru.gb.springdemo.repository.PersonRepository;

@Service
public class PersonDetailService implements UserDetailsService {
    private final PersonRepository repository;

    @Autowired
    public PersonDetailService(PersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person user = repository.findByNameIgnoreCase(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UserDetail(user);
    }
}
