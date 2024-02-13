package ru.gb.springdemo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.gb.springdemo.model.Person;
import ru.gb.springdemo.model.Role;
import ru.gb.springdemo.repository.RoleRepository;
import ru.gb.springdemo.util.BadRequestException;

@ContextConfiguration(classes = {RoleService.class})
@ExtendWith(SpringExtension.class)
class RoleServiceDiffblueTest {
    @MockBean
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    /**
     * Method under test: {@link RoleService#saveRole(Role)}
     */
    @Test
    void testSaveRole() throws BadRequestException {
        // Arrange
        Role role = new Role();
        role.setName("Name");
        ArrayList<Person> persons = new ArrayList<>();
        role.setPersons(persons);
        role.setUuid(UUID.randomUUID());
        when(roleRepository.save(Mockito.<Role>any())).thenReturn(role);

        Role role2 = new Role();
        role2.setName("Name");
        role2.setPersons(new ArrayList<>());
        UUID uuid = UUID.randomUUID();
        role2.setUuid(uuid);

        // Act
        roleService.saveRole(role2);

        // Assert that nothing has changed
        verify(roleRepository).save(Mockito.<Role>any());
        assertEquals("Name", role2.getName());
        assertEquals(persons, role2.getPersons());
        assertSame(uuid, role2.getUuid());
    }

    /**
     * Method under test: {@link RoleService#saveRole(Role)}
     */
    @Test
    void testSaveRole2() throws BadRequestException {
        // Arrange
        when(roleRepository.save(Mockito.<Role>any())).thenThrow(new BadRequestException("An error occurred"));

        Role role = new Role();
        role.setName("Name");
        role.setPersons(new ArrayList<>());
        role.setUuid(UUID.randomUUID());

        // Act and Assert
        assertThrows(BadRequestException.class, () -> roleService.saveRole(role));
        verify(roleRepository).save(Mockito.<Role>any());
    }

    /**
     * Method under test: {@link RoleService#deleteRole(UUID)}
     */
    @Test
    void testDeleteRole() throws BadRequestException {
        // Arrange
        doNothing().when(roleRepository).deleteById(Mockito.<UUID>any());

        // Act
        roleService.deleteRole(UUID.randomUUID());

        // Assert that nothing has changed
        verify(roleRepository).deleteById(Mockito.<UUID>any());
    }

    /**
     * Method under test: {@link RoleService#deleteRole(UUID)}
     */
    @Test
    void testDeleteRole2() throws BadRequestException {
        // Arrange
        doThrow(new BadRequestException("An error occurred")).when(roleRepository).deleteById(Mockito.<UUID>any());

        // Act and Assert
        assertThrows(BadRequestException.class, () -> roleService.deleteRole(UUID.randomUUID()));
        verify(roleRepository).deleteById(Mockito.<UUID>any());
    }

    /**
     * Method under test: {@link RoleService#updateRole(UUID, Role)}
     */
    @Test
    void testUpdateRole() throws BadRequestException {
        // Arrange
        Role role = new Role();
        role.setName("Name");
        role.setPersons(new ArrayList<>());
        role.setUuid(UUID.randomUUID());
        when(roleRepository.save(Mockito.<Role>any())).thenReturn(role);
        UUID id = UUID.randomUUID();

        Role role2 = new Role();
        role2.setName("Name");
        role2.setPersons(new ArrayList<>());
        role2.setUuid(UUID.randomUUID());

        // Act
        roleService.updateRole(id, role2);

        // Assert
        verify(roleRepository).save(Mockito.<Role>any());
        assertSame(id, role2.getUuid());
    }

    /**
     * Method under test: {@link RoleService#updateRole(UUID, Role)}
     */
    @Test
    void testUpdateRole2() throws BadRequestException {
        // Arrange
        when(roleRepository.save(Mockito.<Role>any())).thenThrow(new BadRequestException("An error occurred"));
        UUID id = UUID.randomUUID();

        Role role = new Role();
        role.setName("Name");
        role.setPersons(new ArrayList<>());
        role.setUuid(UUID.randomUUID());

        // Act and Assert
        assertThrows(BadRequestException.class, () -> roleService.updateRole(id, role));
        verify(roleRepository).save(Mockito.<Role>any());
    }

    /**
     * Method under test: {@link RoleService#findAll()}
     */
    @Test
    void testFindAll() {
        // Arrange
        ArrayList<Role> roleList = new ArrayList<>();
        when(roleRepository.findAll()).thenReturn(roleList);

        // Act
        List<Role> actualFindAllResult = roleService.findAll();

        // Assert
        verify(roleRepository).findAll();
        assertTrue(actualFindAllResult.isEmpty());
        assertSame(roleList, actualFindAllResult);
    }

    /**
     * Method under test: {@link RoleService#findAll()}
     */
    @Test
    void testFindAll2() {
        // Arrange
        when(roleRepository.findAll()).thenThrow(new BadRequestException("An error occurred"));

        // Act and Assert
        assertThrows(BadRequestException.class, () -> roleService.findAll());
        verify(roleRepository).findAll();
    }

    /**
     * Method under test: {@link RoleService#findById(UUID)}
     */
    @Test
    void testFindById() {
        // Arrange
        Role role = new Role();
        role.setName("Name");
        role.setPersons(new ArrayList<>());
        role.setUuid(UUID.randomUUID());
        Optional<Role> ofResult = Optional.of(role);
        when(roleRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);

        // Act
        Role actualFindByIdResult = roleService.findById(UUID.randomUUID());

        // Assert
        verify(roleRepository).findById(Mockito.<UUID>any());
        assertSame(role, actualFindByIdResult);
    }

    /**
     * Method under test: {@link RoleService#findById(UUID)}
     */
    @Test
    void testFindById2() {
        // Arrange
        when(roleRepository.findById(Mockito.<UUID>any())).thenThrow(new BadRequestException("An error occurred"));

        // Act and Assert
        assertThrows(BadRequestException.class, () -> roleService.findById(UUID.randomUUID()));
        verify(roleRepository).findById(Mockito.<UUID>any());
    }

    /**
     * Method under test: {@link RoleService#findByName(String)}
     */
    @Test
    void testFindByName() {
        // Arrange
        Role role = new Role();
        role.setName("Name");
        role.setPersons(new ArrayList<>());
        role.setUuid(UUID.randomUUID());
        Optional<Role> ofResult = Optional.of(role);
        when(roleRepository.findByNameIgnoreCase(Mockito.<String>any())).thenReturn(ofResult);

        // Act
        Role actualFindByNameResult = roleService.findByName("Name");

        // Assert
        verify(roleRepository).findByNameIgnoreCase(Mockito.<String>any());
        assertSame(role, actualFindByNameResult);
    }

    /**
     * Method under test: {@link RoleService#findByName(String)}
     */
    @Test
    void testFindByName2() {
        // Arrange
        when(roleRepository.findByNameIgnoreCase(Mockito.<String>any()))
                .thenThrow(new BadRequestException("An error occurred"));

        // Act and Assert
        assertThrows(BadRequestException.class, () -> roleService.findByName("Name"));
        verify(roleRepository).findByNameIgnoreCase(Mockito.<String>any());
    }

    /**
     * Method under test:
     * {@link RoleService#findByPersonsOrderByPersons_IdAsc(Person)}
     */
    @Test
    void testFindByPersonsOrderByPersons_IdAsc() {
        // Arrange
        ArrayList<Role> roleList = new ArrayList<>();
        when(roleRepository.findRoleByPersonsIsNotNull(Mockito.<Person>any(), Mockito.<Pageable>any()))
                .thenReturn(roleList);

        Person person = new Person();
        person.setId(UUID.randomUUID());
        person.setName("Name");
        person.setPassword("iloveyou");
        person.setRole(new ArrayList<>());

        // Act
        List<Role> actualFindByPersonsOrderByPersons_IdAscResult = roleService.findByPersonsOrderByPersons_IdAsc(person);

        // Assert
        verify(roleRepository).findRoleByPersonsIsNotNull(Mockito.<Person>any(), Mockito.<Pageable>any());
        assertTrue(actualFindByPersonsOrderByPersons_IdAscResult.isEmpty());
        assertSame(roleList, actualFindByPersonsOrderByPersons_IdAscResult);
    }

    /**
     * Method under test:
     * {@link RoleService#findByPersonsOrderByPersons_IdAsc(Person)}
     */
    @Test
    void testFindByPersonsOrderByPersons_IdAsc2() {
        // Arrange
        when(roleRepository.findRoleByPersonsIsNotNull(Mockito.<Person>any(), Mockito.<Pageable>any()))
                .thenThrow(new BadRequestException("An error occurred"));

        Person person = new Person();
        person.setId(UUID.randomUUID());
        person.setName("Name");
        person.setPassword("iloveyou");
        person.setRole(new ArrayList<>());

        // Act and Assert
        assertThrows(BadRequestException.class, () -> roleService.findByPersonsOrderByPersons_IdAsc(person));
        verify(roleRepository).findRoleByPersonsIsNotNull(Mockito.<Person>any(), Mockito.<Pageable>any());
    }
}
