package ru.gb.springdemo.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.gb.springdemo.repository.RoleRepository;
import ru.gb.springdemo.service.PersonService;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class RoleControllerTest {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PersonService personService;






    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void findByName() {
    }

    @Test
    void deleteRole() {
    }

    @Test
    void saveRole() {
    }

    @Test
    void updateRole() {
    }
}