package ru.gb.springdemo.api;

import lombok.Data;
import org.aspectj.lang.annotation.After;
import org.jetbrains.annotations.Contract;
import org.junit.jupiter.api.AfterEach;
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
import ru.gb.springdemo.model.Role;
import ru.gb.springdemo.repository.PersonRepository;
import ru.gb.springdemo.repository.PersonsRolesRepository;
import ru.gb.springdemo.repository.RoleRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


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
//    private List<PersonsRoles> personsRoles1;

    @BeforeEach
    public void setUp() {

        personsRolesRepository.deleteAll();
        personService.deleteAll();
        roleService.deleteAll();

        final UUID personId = UUID.randomUUID();
        final UUID personId2 = UUID.randomUUID();
        final UUID roleId = UUID.randomUUID();
        final UUID roleId2 = UUID.randomUUID();


        Role roleAdmin = new Role(roleId, "Admin", List.of(new Person()));
        Role roleUser = new Role(roleId2, "User", List.of(new Person()));


        Person personJohn = new Person(personId, "John", "password", Collections.singletonList(roleAdmin));
        Person personJane = new Person(personId2, "Jane", "password", Collections.singletonList(roleUser));


        roleUser.setPersons(Collections.singletonList(personJane));
        roleAdmin.setPersons(Collections.singletonList(personJohn));

        this.personsRoles = Arrays.asList(
                new PersonsRoles(1L, personService.save(personJohn), roleService.save(roleAdmin)),
                new PersonsRoles(2L, personService.save(personJane), roleService.save(roleUser))
        );
    }


    @Test
    public void testFindAll() {

        WebTestClient.ResponseSpec responseSpec = webTestClientTest
                .get()
                .uri("/api/people_roles")
                .exchange();


        List<PersonsRoles> responseBody = Objects.requireNonNull(responseSpec
                        .expectStatus().isOk()
                        .expectBody(new ParameterizedTypeReference<List<PersonsRolesDtoResponse>>() {
                        })
                        .returnResult()
                        .getResponseBody())
                .stream()
                .map((element) -> mapper.map(element, PersonsRoles.class))
                .toList();
        assertEquals(this.personsRoles.size(), responseBody.size());

        for (PersonsRoles p : responseBody) {
            boolean anyMatch = this.personsRoles.stream().filter(e -> e.getId().equals(p.getId())).anyMatch(e -> {
                return e.getPersonId().getId().equals(p.getPersonId().getId())
                        &&
                        e.getPersonId().getName().equals(p.getPersonId().getName())
                        &&
                        e.getPersonId().getPassword().equals(p.getPersonId().getPassword())
                        &&
                        e.getRoleId().getUuid().equals(p.getRoleId().getUuid())
                        &&
                        e.getRoleId().getName().equals(p.getRoleId().getName());

            });
            Assertions.assertTrue(anyMatch);
        }
    }

    @Test
    void findById() {
        Long idGoodTest = this.personsRoles.get(0).getId();
        PersonsRolesDtoResponse personsRoles1 = webTestClientTest.get().uri("/api/people_roles/{id}", idGoodTest).exchange()
                .expectStatus().isOk()
                .expectBody(PersonsRolesDtoResponse.class)
                .returnResult()
                .getResponseBody();

        assert personsRoles1 != null;
        assertEquals(personsRoles1.getId(), idGoodTest);
        assertEquals(personsRoles1.getRoleId().getName(), this.personsRoles.get(0).getRoleId().getName());
        assertEquals(personsRoles1.getPersonId().getId(), this.personsRoles.get(0).getPersonId().getId());

    }

    @Test
    void findByName() {

        UUID personId = this.personsRoles.get(0).getPersonId().getId();
        List<PersonsRolesDtoResponse> responseBody = webTestClientTest.get().uri("/api/people_roles/by_person/{id}", personId).exchange()
                .expectStatus().isOk()
                .expectBodyList(PersonsRolesDtoResponse.class)
                .returnResult()
                .getResponseBody();

        assert responseBody != null;
        assertEquals(responseBody.size(), 1);
        assertEquals(responseBody.get(0).getPersonId().getId(), this.personsRoles.get(0).getPersonId().getId());
        assertEquals(responseBody.get(0).getRoleId().getName(), this.personsRoles.get(0).getRoleId().getName());

    }

    @Test
    void findByRole() {

        UUID roleId = this.personsRoles.get(0).getRoleId().getUuid();
        List<PersonsRolesDtoResponse> responseBody = webTestClientTest.get().uri("/api/people_roles/by_role/{id}", roleId).exchange()
                .expectStatus().isOk()
                .expectBodyList(PersonsRolesDtoResponse.class)
                .returnResult()
                .getResponseBody();

        assert responseBody != null;
        assertEquals(responseBody.size(), 1);
        assertEquals(responseBody.get(0).getRoleId().getUuid(), this.personsRoles.get(0).getRoleId().getUuid());
        assertEquals(responseBody.get(0).getPersonId().getId(), this.personsRoles.get(0).getPersonId().getId());

    }

    @Test
    void delete() {

        Long id = this.personsRoles.get(0).getId();
        webTestClientTest.delete().uri("/api/people_roles/{id}", id)
                .exchange()
                .expectStatus().isForbidden();

        List<PersonsRoles> responseBody = webTestClientTest.get().uri("/api/people_roles").exchange()
                .expectStatus().isOk()
                .expectBodyList(PersonsRoles.class)
                .returnResult()
                .getResponseBody();

        assert responseBody != null;
        assertEquals(responseBody.size(), 2);
        assertEquals(responseBody.get(0).getId(), this.personsRoles.get(0).getId());
    }

    @Test
    void save() {
        PersonsRoles roles = new PersonsRoles(3L, this.personsRoles.get(0).getPersonId(), this.personsRoles.get(1).getRoleId());
        webTestClientTest.post().uri("/api/people_roles")
                .bodyValue(roles)
                .exchange()
                .expectStatus()
                .isForbidden();

        List<PersonsRoles> responseBody = webTestClientTest.get().uri("/api/people_roles").exchange()
                .expectStatus().isOk()
                .expectBodyList(PersonsRoles.class)
                .returnResult()
                .getResponseBody();

        assert responseBody != null;
        assertEquals(responseBody.size(), 2);

    }

    @Test
    void update() {

        PersonsRoles roles = new PersonsRoles(3L, this.personsRoles.get(0).getPersonId(), this.personsRoles.get(1).getRoleId());

        webTestClientTest.put().uri("/api/people_roles/{id}", 2)
                .bodyValue(roles)
                .exchange()
                .expectStatus().isForbidden();

        List<PersonsRoles> responseBody = webTestClientTest.get().uri("/api/people_roles").exchange()
                .expectStatus().isOk()
                .expectBodyList(PersonsRoles.class)
                .returnResult()
                .getResponseBody();

        assert responseBody != null;
        assertEquals(responseBody.size(), 2);

    }

    @Data
    private static class PersonsRolesDtoResponse {
        private Long id;
        private Person personId;
        private Role roleId;
    }
}