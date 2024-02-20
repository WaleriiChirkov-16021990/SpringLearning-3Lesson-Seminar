package ru.gb.springdemo.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.gb.springdemo.model.Role;
import ru.gb.springdemo.model.RoleDto;
import ru.gb.springdemo.repository.RoleRepository;
import ru.gb.springdemo.service.PersonService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class RoleControllerTest {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PersonService personService;

    @Autowired
    private WebTestClient webTestClient;

    private List<Role> roles;


    @BeforeEach
    public void setUp() {
        Role role = new Role(null, "ADMIN", Collections.singletonList(null));
        Role roleUser = new Role(null, "USER", Collections.singletonList(null));
        this.roles = roleRepository.saveAll(List.of(role, roleUser));

    }

    @AfterEach
    public void tearDown() {
        roleRepository.deleteAll();
        roles = null;
    }


    @Test
    void findAll() {

        List<RoleDto> responseBody = webTestClient.get().uri("/api/roles").exchange()
                .expectStatus().isOk()
                .expectBodyList(RoleDto.class)
                .hasSize(2)
                .returnResult()
                .getResponseBody();

        assert responseBody != null;
        int step = 0;
        assertEquals(responseBody.size(), 2);

        for (RoleDto e : responseBody) {
            assertEquals(e.getUuid(), roles.get(step).getUuid());
            assertEquals(e.getName(), roles.get(step).getName());
            step++;
        }
    }

    @Test
    void findById() {

        RoleDto responseBody = webTestClient.get().uri("/api/roles/{id}", roles.get(0).getUuid())
                .exchange()
                .expectStatus().isOk()
                .expectBody(RoleDto.class)
                .returnResult()
                .getResponseBody();

        assert responseBody != null;
        assertEquals(responseBody.getUuid(), roles.get(0).getUuid());
        assertEquals(responseBody.getName(), roles.get(0).getName());

    }

    @Test
    void findByName() {

        List<RoleDto> responseBody = webTestClient.get().uri("/api/roles/name/{name}", "ADMIN")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(RoleDto.class)
                .hasSize(1)
                .returnResult()
                .getResponseBody();

        assert responseBody != null;
        assertEquals(responseBody.get(0).getUuid(), roles.get(0).getUuid());
        assertEquals(responseBody.get(0).getName(), roles.get(0).getName());

    }

    @Test
    @Disabled
    void deleteRole() {

        webTestClient.delete().uri("/api/roles/{id}", roles.get(0).getUuid())
                .exchange()
                .expectStatus().isOk();

        Role role = roleRepository.findById(roles.get(0).getUuid()).orElse(null);
        assert role == null;

    }

    @Test
    @Disabled
    void saveRole() {

        Role role = new Role(null, "ADMIN", Collections.singletonList(null));
        RoleDto responseBody = webTestClient.post().uri("/api/roles")
                .bodyValue(role)
                .exchange()
                .expectStatus().isOk()
                .expectBody(RoleDto.class)
                .returnResult()
                .getResponseBody();

        assert responseBody != null;
        assertEquals(responseBody.getName(), role.getName());

    }

    @Test
    @Disabled
    void updateRole() {

        Role role = new Role(null, "ADMIN", Collections.singletonList(null));
        RoleDto responseBody = webTestClient.put().uri("/api/roles/{id}", roles.get(0).getUuid())
                .bodyValue(role)
                .exchange()
                .expectStatus().isOk()
                .expectBody(RoleDto.class)
                .returnResult()
                .getResponseBody();

        assert responseBody != null;
        assertEquals(responseBody.getName(), role.getName());

    }
}