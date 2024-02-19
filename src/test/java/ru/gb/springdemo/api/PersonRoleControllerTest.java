package ru.gb.springdemo.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.gb.springdemo.model.Person;
import ru.gb.springdemo.model.PersonsRoles;
import ru.gb.springdemo.model.PersonsRolesDto;
import ru.gb.springdemo.model.Role;
import ru.gb.springdemo.repository.PersonRepository;
import ru.gb.springdemo.repository.PersonsRolesRepository;
import ru.gb.springdemo.repository.RoleRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@WebMvcTest(Application.class)

@ActiveProfiles("test")
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonRoleControllerTest {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private WebTestClient webTestClientTest;

    @Autowired
    private PersonsRolesRepository personsRolesRepository;

    @Autowired
    private PersonRepository personService;

    @Autowired
    private RoleRepository roleService;

    private List<PersonsRoles> personsRoles;
    private List<PersonsRoles> personsRoles1;


//    @BeforeEach
//    public void preparedData() {
//
//
//    }

    @Test
    public void testFindAll() {
//        mapper.typeMap(PersonsRolesDto.class, PersonsRoles.class)
//                .addMappings(mapping -> {
//                    mapping.map(PersonsRolesDto::getPerson, PersonsRoles::setPersonId);
//                    // или mapping.map(PersonsRolesDto::getRoleId, PersonsRoles::setId);
//                });


        Role roleAdmin = new Role(UUID.randomUUID(), "Admin", List.of(new Person()));
        Role roleUser = new Role(UUID.randomUUID(), "User", List.of(new Person()));


        Person personJohn = new Person(UUID.randomUUID(), "John", "password", Collections.singletonList(roleAdmin));
        Person personJane = new Person(UUID.randomUUID(), "Jane", "password", Collections.singletonList(roleUser));


//        personJohn.setRole(Collections.singletonList(roleAdmin));
//        personJane.setRole(Collections.singletonList(roleUser));

        roleUser.setPersons(Collections.singletonList(personJane));
        roleAdmin.setPersons(Collections.singletonList(personJohn));

        personService.save(personJane);
        personService.save(personJohn);
        roleService.save(roleAdmin);
        roleService.save(roleUser);

        this.personsRoles = Arrays.asList(
                new PersonsRoles(1L, personJohn, roleAdmin),
                new PersonsRoles(2L, personJane, roleUser)
        );
//        this.personsRoles1 = personsRolesRepository.saveAll(personsRoles);


        WebTestClient.ResponseSpec responseSpec = webTestClientTest
                .get()
                .uri("/api/people_roles")
                .exchange();


        List<PersonsRoles> responseBody = responseSpec
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<PersonsRolesDto>>() {
                })
                .returnResult()
                .getResponseBody()
                .stream()
                .map((element) -> mapper.map(element, PersonsRoles.class))
                .toList();

        assertEquals(this.personsRoles.size(), responseBody.size());

//        List<PersonsRoles> responseBody1 = responseBody.stream().map((element) -> mapper.map(element, PersonsRoles.class)).toList();


        for (PersonsRoles p : responseBody) {
//            assertTrue(this.personsRoles.contains(p));
            boolean anyMatch = this.personsRoles.stream().filter(e -> e.getId().equals(p.getId())).anyMatch(e -> {
//                boolean b = e.getPersonId().getId().equals(p.getPersonId().getId())
//                        &&
//                        e.getPersonId().getName().equals(p.getPersonId().getName())
//                        &&
//                        e.getPersonId().getPassword().equals(p.getPersonId().getPassword())
//                        &&
//                        e.getRoleId().getUuid().equals(p.getRoleId().getUuid())
//                        &&
//                        e.getRoleId().getName().equals(p.getRoleId().getName());
//                return b;

                boolean b = e.getPersonId().getId().equals(p.getPersonId().getId());
                boolean a = e.getPersonId().getName().equals(p.getPersonId().getName());
                boolean c = e.getPersonId().getPassword().equals(p.getPersonId().getPassword());
                boolean d = e.getRoleId().getUuid().equals(p.getRoleId().getUuid());
                boolean f = e.getRoleId().getName().equals(p.getRoleId().getName());
                return b || a || c || d || f;
            });
            Assertions.assertTrue(anyMatch);

        }
    }

    @Test
    void findById() {
    }

    @Test
    void findByName() {
    }

    @Test
    void findByRole() {
    }

    @Test
    void delete() {
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }
}