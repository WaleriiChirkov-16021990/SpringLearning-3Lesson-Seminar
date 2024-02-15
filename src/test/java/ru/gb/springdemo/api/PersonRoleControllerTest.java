package ru.gb.springdemo.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.stubbing.OngoingStubbing;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.gb.springdemo.Application;
import ru.gb.springdemo.config.SecurityConfig;
import ru.gb.springdemo.model.Person;
import ru.gb.springdemo.model.PersonsRoles;
import ru.gb.springdemo.model.PersonsRolesDto;
import ru.gb.springdemo.model.Role;
import ru.gb.springdemo.repository.PersonsRolesRepository;
import ru.gb.springdemo.service.PersonRoleService;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

//@NotExtensible
/*
(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {
                Application.class,
                SecurityConfig.class,
                ModelMapper.class,
                PersonRoleControllerTest.class,
                PersonsRolesRepository.class
        })
 */
@ActiveProfiles("test")
@AutoConfigureWebTestClient
//@WebMvcTest(Application.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonRoleControllerTest {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private WebTestClient webTestClientTest;

    @Autowired
    private PersonsRolesRepository personsRolesRepository;

    private List<PersonsRoles> personsRoles;
    private List<PersonsRoles> personsRoles1;


    @BeforeEach
    public void preparedData() {

        this.personsRoles = Arrays.asList(
                new PersonsRoles(1L, new Person(UUID.randomUUID(), "John", "password"), new Role(UUID.randomUUID(), "Admin")),
                new PersonsRoles(2L, new Person(UUID.randomUUID(), "Jane", "password"), new Role(UUID.randomUUID(), "User"))
        );
        this.personsRoles1 = personsRolesRepository.saveAll(personsRoles);

    }

    @Test
    public void testFindAll() {

        List<PersonsRoles> responseBody = webTestClientTest.get().uri("/api/people_roles")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<PersonsRolesDto>>() {
                })
                .returnResult().getResponseBody().stream().map((element) -> mapper.map(element, PersonsRoles.class))
                .toList();

        assertEquals(this.personsRoles1.size(), responseBody.size());


        for (PersonsRoles p : responseBody) {
            assertTrue(this.personsRoles1.contains(p));

//            this.personsRoles.stream().filter(e -> e.getId().equals(p.getId())).forEach(e -> assertEquals(e, mapper.map(p, PersonsRoles.class)));
            boolean anyMatch = this.personsRoles1.stream().filter(e -> e.getId().equals(p.getId())).anyMatch(e -> {
                return (e.getPersonId().getId().equals(p.getPersonId().getId())
                        &&
                        e.getPersonId().getName().equals(p.getPersonId().getName())
                        &&
                        e.getPersonId().getPassword().equals(p.getPersonId().getPassword())
                        &&
                        e.getRoleId().getUuid().equals(p.getRoleId().getUuid())
                        &&
                        e.getRoleId().getName().equals(p.getRoleId().getName())
                );
            });

            Assertions.assertTrue(anyMatch);

        }
    }
//
//
//                        .andDo(print())
////                .andDo(() -> System.out.println("Response: " + testRestTemplate.getRestTemplate().getUriTemplateHandler().expand("/api/people_roles")))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].personId.id").exists())
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].personId.name").value("John"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].personId.password").value("password"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].roleId.uuid").exists())
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].roleId.name").value("Admin"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1].personId.id").exists())
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1].personId.name").value("Jane"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1].personId.password").value("password"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1].roleId.uuid").exists())
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1].roleId.name").value("User"));

    // Verify that the service method was called
//        verify(personRoleService, times(1)).findAll();
//    }

//    @Test
//    void findAll() throws Exception {
//        UUID roleUuid = UUID.randomUUID();
//        Role role = new Role();
//        role.setUuid(roleUuid);
//        role.setName("ADMIN");
//
//        UUID role2Uuid = UUID.randomUUID();
//        Role role2 = new Role();
//        role.setUuid(roleUuid);
//        role.setName("USER");
//
//        Person person = new Person();
//        UUID personUuid = UUID.randomUUID();
//        person.setId(personUuid);
//        person.setName("John");
//        person.setPassword("pass");
//
//        Person person2 = new Person();
//        UUID person2Uuid = UUID.randomUUID();
//        person.setId(personUuid);
//        person.setName("James");
//        person.setPassword("pass");
//
//        role.setPersons(List.of(person));
//        person.setRole(List.of(role));
//
//        role2.setPersons(List.of(person2));
//        person2.setRole(Collections.singletonList(role2));
//
//        Long personRoleUUID = 1L;
//
//        List<PersonsRoles> personRoleList = Arrays.asList(
//                new PersonsRoles(personRoleUUID, person, role),
//                new PersonsRoles(++personRoleUUID, person2, role2)
//        );
//
//        when(personRoleService.findAll()).thenReturn(personRoleList);
//
//        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/api/people_roles"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].personId").value(person))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].roleId.name").value("ADMIN"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1].personId.name").value("James"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1].roleId.name").value("USER"));
//
////        Mockito.verify(personRoleService, times(1)).findAll();
//    }

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