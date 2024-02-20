package ru.gb.springdemo.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.gb.springdemo.model.Person;
import ru.gb.springdemo.model.PersonDto;
import ru.gb.springdemo.model.Role;
import ru.gb.springdemo.repository.PersonRepository;
import ru.gb.springdemo.service.PersonService;
import ru.gb.springdemo.service.RoleService;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class PeopleControllerTest {

    @Autowired
    private PersonService personService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private List<Person> people;


    @BeforeEach
    public void setUp() {
        Role role = new Role(null, "ADMIN", Collections.singletonList(null));
        Person person = new Person(null, "Alex", "Ivanov", Collections.singletonList(role));
        Person person2 = new Person(null, "Lex", "Vanov", Collections.singletonList(role));
        Person person3 = new Person(null, "Ex", "Anov", Collections.singletonList(role));
        role.setPersons(List.of(person, person2, person3));
        this.people = personRepository.saveAll(
                List.of(
                        person, person2, person3
                ));
    }

    @AfterEach
    public void tearDown() {
        personRepository.deleteAll();
        people = null;
    }

    @Test
    void findAll() {
        List<PersonDto> responseBody = webTestClient.get().uri("/api/people")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(PersonDto.class)
                .hasSize(3)
                .returnResult()
                .getResponseBody();

        assert responseBody != null;
        int step = 0;
        for (PersonDto personDto : responseBody) {
            assertNotNull(personDto);
            assertEquals(this.people.get(step).getId(), personDto.getId());
            assertEquals(this.people.get(step).getName(), personDto.getName());
            assertEquals(this.people.get(step).getRole().get(0).getUuid(), personDto.getRole().get(0).getUuid());
            assertEquals(this.people.get(step).getRole().get(0).getName(), personDto.getRole().get(0).getName());
            step++;
        }
    }

    @Test
    void findById() {
        UUID uuid = this.people.get(0).getId();
//        SqlRowSet uuid = jdbcTemplate.queryForRowSet("SELECT max(id) FROM PERSON");
        PersonDto responseBody =
                webTestClient
                        .get()
                        .uri("/api/people/{id}", uuid)
                        .exchange()
                        .expectStatus().isOk()
                        .expectBody(PersonDto.class)
                        .returnResult()
                        .getResponseBody();

        assertNotNull(responseBody);
        assertEquals(uuid, responseBody.getId());
        assertEquals(people.get(0).getName(), responseBody.getName());
        assertEquals(people.get(0).getRole().get(0).getUuid(), responseBody.getRole().get(0).getUuid());
        assertEquals(people.get(0).getRole().get(0).getName(), responseBody.getRole().get(0).getName());
    }

    @Test
    @Disabled
    void savePerson() {
        Person newPerson = new Person(null, "Velex", "Osipov", Collections.singletonList(
                roleService.findById(this.people.get(0).getRole().get(0).getUuid())));

        PersonDto responseBody = webTestClient
                .post()
                .uri("/api/people")
                .bodyValue(newPerson)
                .exchange()
                .expectStatus().isOk()
                .expectBody(PersonDto.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(responseBody);
        assertEquals(newPerson.getName(), responseBody.getName());
        assertEquals(newPerson.getRole().get(0).getUuid(), responseBody.getRole().get(0).getUuid());
        assertEquals(newPerson.getRole().get(0).getName(), responseBody.getRole().get(0).getName());
    }

    @Test
    @Disabled
    void deletePerson() {

        UUID uuid = this.people.get(0).getId();
        webTestClient
                .delete()
                .uri("/api/people/{id}", uuid)
                .exchange()
                .expectStatus().isOk();

        List<Person> responseBody = webTestClient
                .get()
                .uri("/api/people")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Person.class)
                .returnResult()
                .getResponseBody();

        assert responseBody != null;
        assertEquals(responseBody.size(), 2);
        assertEquals(responseBody.get(0).getId(), this.people.get(1).getId());
        assertEquals(responseBody.get(1).getId(), this.people.get(2).getId());

    }

    @Test
    @Disabled
    void updatePerson() {

        Person newPerson = new Person(null, "Velex", "Osipov", Collections.singletonList(
                roleService.findById(this.people.get(0).getRole().get(0).getUuid())));

        PersonDto responseBody = webTestClient
                .put()
                .uri("/api/people")
                .bodyValue(newPerson)
                .exchange()
                .expectStatus().isOk()
                .expectBody(PersonDto.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(responseBody);
        assertEquals(newPerson.getName(), responseBody.getName());
        assertEquals(newPerson.getRole().get(0).getUuid(), responseBody.getRole().get(0).getUuid());
        assertEquals(newPerson.getRole().get(0).getName(), responseBody.getRole().get(0).getName());
    }

    @Test
    void findByName() {

        List<PersonDto> responseBody = webTestClient
                .get()
                .uri("/api/people/by_name/{name}", this.people.get(0).getName())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(PersonDto.class)
                .returnResult()
                .getResponseBody();

        assert responseBody != null;
        assertEquals(responseBody.size(), 1);


        this.people.stream()
                .filter(p -> p.getId().equals(responseBody.get(0).getId()))
                .findAny()
                .ifPresent(p -> assertEquals(p.getName(), responseBody.get(0).getName()));

        assertEquals(responseBody.get(0).getName(), this.people.get(0).getName());
        assertEquals(responseBody.get(0).getRole().get(0).getUuid(), this.people.get(0).getRole().get(0).getUuid());
        assertEquals(responseBody.get(0).getRole().get(0).getName(), this.people.get(0).getRole().get(0).getName());
    }

    @Test
    void findByRole() {

        List<PersonDto> responseBody = webTestClient
                .get()
                .uri("/api/people/by_role/{id}", this.people.get(0).getRole().get(0).getUuid())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(PersonDto.class)
                .returnResult()
                .getResponseBody();

        assert responseBody != null;
        assertEquals(responseBody.size(), 3);

        AtomicInteger step = new AtomicInteger();
        for (PersonDto person : responseBody) {

            this.people.stream()
                    .filter(p -> p.getId().equals(person.getId()))
                    .findAny()
                    .ifPresent(p -> step.set(this.people.indexOf(p)));

            assertEquals(person.getName(), this.people.get(step.get()).getName());
            assertEquals(person.getRole().get(0).getUuid(), this.people.get(step.get()).getRole().get(0).getUuid());
            assertEquals(person.getRole().get(0).getName(), this.people.get(step.get()).getRole().get(0).getName());
        }
    }
}