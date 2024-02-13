package ru.gb.springdemo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.gb.springdemo.model.Person;
import ru.gb.springdemo.model.PersonsRoles;
import ru.gb.springdemo.model.Role;
import ru.gb.springdemo.repository.PersonRepository;
import ru.gb.springdemo.util.BadRequestException;
import ru.gb.springdemo.util.NotFoundException;

@ContextConfiguration(classes = {PersonService.class})
@ExtendWith(SpringExtension.class)
class PersonServiceDiffblueTest {
    @MockBean
    private PersonRepository personRepository;

    @MockBean
    private PersonRoleService personRoleService;

    @Autowired
    private PersonService personService;

    @MockBean
    private RoleService roleService;

    /**
     * Method under test: {@link PersonService#savePerson(Person)}
     */
    @Test
    void testSavePerson() throws BadRequestException {
        // Arrange
        Person person = new Person();
        person.setId(UUID.randomUUID());
        person.setName("Name");
        person.setPassword("iloveyou");
        person.setRole(new ArrayList<>());
        when(personRepository.save(Mockito.<Person>any())).thenReturn(person);

        Person person2 = new Person();
        person2.setId(UUID.randomUUID());
        person2.setName("Name");
        person2.setPassword("iloveyou");
        person2.setRole(new ArrayList<>());

        // Act
        Person actualSavePersonResult = personService.savePerson(person2);

        // Assert
        verify(personRepository).save(Mockito.<Person>any());
        assertSame(person2, actualSavePersonResult);
    }

    /**
     * Method under test: {@link PersonService#savePerson(Person)}
     */
    @Test
    void testSavePerson2() throws BadRequestException {
        // Arrange
        Person person = new Person();
        person.setId(UUID.randomUUID());
        person.setName("Name");
        person.setPassword("iloveyou");
        person.setRole(new ArrayList<>());
        when(personRepository.save(Mockito.<Person>any())).thenReturn(person);
        Person person2 = mock(Person.class);
        when(person2.getPassword()).thenReturn("iloveyou");
        when(person2.getId()).thenReturn(null);
        doNothing().when(person2).setId(Mockito.<UUID>any());
        doNothing().when(person2).setName(Mockito.<String>any());
        doNothing().when(person2).setPassword(Mockito.<String>any());
        doNothing().when(person2).setRole(Mockito.<List<Role>>any());
        person2.setId(UUID.randomUUID());
        person2.setName("Name");
        person2.setPassword("iloveyou");
        person2.setRole(new ArrayList<>());

        // Act
        Person actualSavePersonResult = personService.savePerson(person2);

        // Assert
        verify(personRepository).save(Mockito.<Person>any());
        verify(person2).getId();
        verify(person2, atLeast(1)).getPassword();
        verify(person2, atLeast(1)).setId(Mockito.<UUID>any());
        verify(person2).setName(Mockito.<String>any());
        verify(person2, atLeast(1)).setPassword(Mockito.<String>any());
        verify(person2).setRole(Mockito.<List<Role>>any());
        assertSame(person2, actualSavePersonResult);
    }

    /**
     * Method under test: {@link PersonService#savePerson(Person)}
     */
    @Test
    void testSavePerson3() throws BadRequestException {
        // Arrange
        Person person = mock(Person.class);
        when(person.getPassword()).thenReturn(null);
        when(person.getId()).thenReturn(null);
        doNothing().when(person).setId(Mockito.<UUID>any());
        doNothing().when(person).setName(Mockito.<String>any());
        doNothing().when(person).setPassword(Mockito.<String>any());
        doNothing().when(person).setRole(Mockito.<List<Role>>any());
        person.setId(UUID.randomUUID());
        person.setName("Name");
        person.setPassword("iloveyou");
        person.setRole(new ArrayList<>());

        // Act and Assert
        assertThrows(BadRequestException.class, () -> personService.savePerson(person));
        verify(person).getId();
        verify(person).getPassword();
        verify(person, atLeast(1)).setId(Mockito.<UUID>any());
        verify(person).setName(Mockito.<String>any());
        verify(person).setPassword(Mockito.<String>any());
        verify(person).setRole(Mockito.<List<Role>>any());
    }

    /**
     * Method under test: {@link PersonService#deletePerson(UUID)}
     */
    @Test
    void testDeletePerson() throws BadRequestException {
        // Arrange
        doNothing().when(personRepository).deleteById(Mockito.<UUID>any());

        // Act
        personService.deletePerson(UUID.randomUUID());

        // Assert that nothing has changed
        verify(personRepository).deleteById(Mockito.<UUID>any());
    }

    /**
     * Method under test: {@link PersonService#deletePerson(UUID)}
     */
    @Test
    void testDeletePerson2() throws BadRequestException {
        // Arrange
        doThrow(new BadRequestException("An error occurred")).when(personRepository).deleteById(Mockito.<UUID>any());

        // Act and Assert
        assertThrows(BadRequestException.class, () -> personService.deletePerson(UUID.randomUUID()));
        verify(personRepository).deleteById(Mockito.<UUID>any());
    }

    /**
     * Method under test: {@link PersonService#updatePerson(UUID, Person)}
     */
    @Test
    void testUpdatePerson() throws BadRequestException {
        // Arrange
        Person person = new Person();
        person.setId(UUID.randomUUID());
        person.setName("Name");
        person.setPassword("iloveyou");
        person.setRole(new ArrayList<>());
        when(personRepository.save(Mockito.<Person>any())).thenReturn(person);
        UUID id = UUID.randomUUID();

        Person person2 = new Person();
        person2.setId(UUID.randomUUID());
        person2.setName("Name");
        person2.setPassword("iloveyou");
        person2.setRole(new ArrayList<>());

        // Act
        personService.updatePerson(id, person2);

        // Assert
        verify(personRepository).save(Mockito.<Person>any());
        assertSame(id, person2.getId());
    }

    /**
     * Method under test: {@link PersonService#updatePerson(UUID, Person)}
     */
    @Test
    void testUpdatePerson2() throws BadRequestException {
        // Arrange
        when(personRepository.save(Mockito.<Person>any())).thenThrow(new BadRequestException("An error occurred"));
        UUID id = UUID.randomUUID();

        Person person = new Person();
        person.setId(UUID.randomUUID());
        person.setName("Name");
        person.setPassword("iloveyou");
        person.setRole(new ArrayList<>());

        // Act and Assert
        assertThrows(BadRequestException.class, () -> personService.updatePerson(id, person));
        verify(personRepository).save(Mockito.<Person>any());
    }

    /**
     * Method under test: {@link PersonService#findAll()}
     */
    @Test
    void testFindAll() {
        // Arrange
        ArrayList<Person> personList = new ArrayList<>();
        when(personRepository.findAll()).thenReturn(personList);

        // Act
        List<Person> actualFindAllResult = personService.findAll();

        // Assert
        verify(personRepository).findAll();
        assertTrue(actualFindAllResult.isEmpty());
        assertSame(personList, actualFindAllResult);
    }

    /**
     * Method under test: {@link PersonService#findAll()}
     */
    @Test
    void testFindAll2() {
        // Arrange
        when(personRepository.findAll()).thenThrow(new BadRequestException("An error occurred"));

        // Act and Assert
        assertThrows(BadRequestException.class, () -> personService.findAll());
        verify(personRepository).findAll();
    }

    /**
     * Method under test: {@link PersonService#findOne(UUID)}
     */
    @Test
    void testFindOne() throws NotFoundException {
        // Arrange
        Person person = new Person();
        person.setId(UUID.randomUUID());
        person.setName("Name");
        person.setPassword("iloveyou");
        person.setRole(new ArrayList<>());
        Optional<Person> ofResult = Optional.of(person);
        when(personRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);

        // Act
        Person actualFindOneResult = personService.findOne(UUID.randomUUID());

        // Assert
        verify(personRepository).findById(Mockito.<UUID>any());
        assertSame(person, actualFindOneResult);
    }

    /**
     * Method under test: {@link PersonService#findOne(UUID)}
     */
    @Test
    void testFindOne2() throws NotFoundException {
        // Arrange
        when(personRepository.findById(Mockito.<UUID>any())).thenThrow(new BadRequestException("An error occurred"));

        // Act and Assert
        assertThrows(BadRequestException.class, () -> personService.findOne(UUID.randomUUID()));
        verify(personRepository).findById(Mockito.<UUID>any());
    }

    /**
     * Method under test: {@link PersonService#findByName(String)}
     */
    @Test
    void testFindByName() throws NotFoundException {
        // Arrange
        Person person = new Person();
        person.setId(UUID.randomUUID());
        person.setName("Name");
        person.setPassword("iloveyou");
        person.setRole(new ArrayList<>());
        Optional<Person> ofResult = Optional.of(person);
        when(personRepository.findByNameIgnoreCase(Mockito.<String>any())).thenReturn(ofResult);

        // Act
        Person actualFindByNameResult = personService.findByName("Name");

        // Assert
        verify(personRepository).findByNameIgnoreCase(Mockito.<String>any());
        assertSame(person, actualFindByNameResult);
    }

    /**
     * Method under test: {@link PersonService#findByName(String)}
     */
    @Test
    void testFindByName2() throws NotFoundException {
        // Arrange
        when(personRepository.findByNameIgnoreCase(Mockito.<String>any()))
                .thenThrow(new BadRequestException("An error occurred"));

        // Act and Assert
        assertThrows(BadRequestException.class, () -> personService.findByName("Name"));
        verify(personRepository).findByNameIgnoreCase(Mockito.<String>any());
    }

    /**
     * Method under test: {@link PersonService#findPersonByName(String)}
     */
    @Test
    void testFindPersonByName() throws NotFoundException {
        // Arrange
        Person person = new Person();
        person.setId(UUID.randomUUID());
        person.setName("Name");
        person.setPassword("iloveyou");
        person.setRole(new ArrayList<>());
        Optional<Person> ofResult = Optional.of(person);
        when(personRepository.findByNameIgnoreCase(Mockito.<String>any())).thenReturn(ofResult);
        when(personRoleService.findByPersonId(Mockito.<Person>any())).thenReturn(new ArrayList<>());

        // Act
        Optional<Person> actualFindPersonByNameResult = personService.findPersonByName("Name");

        // Assert
        verify(personRepository).findByNameIgnoreCase(Mockito.<String>any());
        verify(personRoleService).findByPersonId(Mockito.<Person>any());
        assertTrue(actualFindPersonByNameResult.get().getRole().isEmpty());
        assertTrue(actualFindPersonByNameResult.isPresent());
    }

    /**
     * Method under test: {@link PersonService#findPersonByName(String)}
     */
    @Test
    void testFindPersonByName2() throws NotFoundException {
        // Arrange
        Person person = new Person();
        person.setId(UUID.randomUUID());
        person.setName("Name");
        person.setPassword("iloveyou");
        person.setRole(new ArrayList<>());
        Optional<Person> ofResult = Optional.of(person);
        when(personRepository.findByNameIgnoreCase(Mockito.<String>any())).thenReturn(ofResult);
        when(personRoleService.findByPersonId(Mockito.<Person>any()))
                .thenThrow(new BadRequestException("An error occurred"));

        // Act and Assert
        assertThrows(BadRequestException.class, () -> personService.findPersonByName("Name"));
        verify(personRepository).findByNameIgnoreCase(Mockito.<String>any());
        verify(personRoleService).findByPersonId(Mockito.<Person>any());
    }

    /**
     * Method under test: {@link PersonService#findPersonByName(String)}
     */
    @Test
    void testFindPersonByName3() throws NotFoundException {
        // Arrange
        Person person = new Person();
        person.setId(UUID.randomUUID());
        person.setName("Name");
        person.setPassword("iloveyou");
        person.setRole(new ArrayList<>());
        Optional<Person> ofResult = Optional.of(person);
        when(personRepository.findByNameIgnoreCase(Mockito.<String>any())).thenReturn(ofResult);

        Person personId = new Person();
        personId.setId(UUID.randomUUID());
        personId.setName("Name");
        personId.setPassword("iloveyou");
        personId.setRole(new ArrayList<>());

        Role roleId = new Role();
        roleId.setName("Name");
        roleId.setPersons(new ArrayList<>());
        roleId.setUuid(UUID.randomUUID());

        PersonsRoles personsRoles = new PersonsRoles();
        personsRoles.setId(1L);
        personsRoles.setPersonId(personId);
        personsRoles.setRoleId(roleId);

        ArrayList<PersonsRoles> personsRolesList = new ArrayList<>();
        personsRolesList.add(personsRoles);
        when(personRoleService.findByPersonId(Mockito.<Person>any())).thenReturn(personsRolesList);

        Role role = new Role();
        role.setName("Name");
        role.setPersons(new ArrayList<>());
        role.setUuid(UUID.randomUUID());
        when(roleService.findById(Mockito.<UUID>any())).thenReturn(role);

        // Act
        Optional<Person> actualFindPersonByNameResult = personService.findPersonByName("Name");

        // Assert
        verify(personRepository).findByNameIgnoreCase(Mockito.<String>any());
        verify(personRoleService).findByPersonId(Mockito.<Person>any());
        verify(roleService).findById(Mockito.<UUID>any());
        assertEquals(1, actualFindPersonByNameResult.get().getRole().size());
        assertTrue(actualFindPersonByNameResult.isPresent());
    }

    /**
     * Method under test: {@link PersonService#findPersonByName(String)}
     */
    @Test
    void testFindPersonByName4() throws NotFoundException {
        // Arrange
        Person person = new Person();
        person.setId(UUID.randomUUID());
        person.setName("Name");
        person.setPassword("iloveyou");
        person.setRole(new ArrayList<>());
        Optional<Person> ofResult = Optional.of(person);
        when(personRepository.findByNameIgnoreCase(Mockito.<String>any())).thenReturn(ofResult);

        Person personId = new Person();
        personId.setId(UUID.randomUUID());
        personId.setName("Name");
        personId.setPassword("iloveyou");
        personId.setRole(new ArrayList<>());

        Role roleId = new Role();
        roleId.setName("Name");
        roleId.setPersons(new ArrayList<>());
        roleId.setUuid(UUID.randomUUID());

        PersonsRoles personsRoles = new PersonsRoles();
        personsRoles.setId(1L);
        personsRoles.setPersonId(personId);
        personsRoles.setRoleId(roleId);

        Person personId2 = new Person();
        personId2.setId(UUID.randomUUID());
        personId2.setName("42");
        personId2.setPassword("Password");
        personId2.setRole(new ArrayList<>());

        Role roleId2 = new Role();
        roleId2.setName("42");
        roleId2.setPersons(new ArrayList<>());
        roleId2.setUuid(UUID.randomUUID());

        PersonsRoles personsRoles2 = new PersonsRoles();
        personsRoles2.setId(2L);
        personsRoles2.setPersonId(personId2);
        personsRoles2.setRoleId(roleId2);

        ArrayList<PersonsRoles> personsRolesList = new ArrayList<>();
        personsRolesList.add(personsRoles2);
        personsRolesList.add(personsRoles);
        when(personRoleService.findByPersonId(Mockito.<Person>any())).thenReturn(personsRolesList);

        Role role = new Role();
        role.setName("Name");
        role.setPersons(new ArrayList<>());
        role.setUuid(UUID.randomUUID());
        when(roleService.findById(Mockito.<UUID>any())).thenReturn(role);

        // Act
        Optional<Person> actualFindPersonByNameResult = personService.findPersonByName("Name");

        // Assert
        verify(personRepository).findByNameIgnoreCase(Mockito.<String>any());
        verify(personRoleService).findByPersonId(Mockito.<Person>any());
        verify(roleService, atLeast(1)).findById(Mockito.<UUID>any());
        assertEquals(2, actualFindPersonByNameResult.get().getRole().size());
        assertTrue(actualFindPersonByNameResult.isPresent());
    }

    /**
     * Method under test: {@link PersonService#findByRole(Role)}
     */
    @Test
    void testFindByRole() {
        // Arrange
        ArrayList<Person> personList = new ArrayList<>();
        Optional<List<Person>> ofResult = Optional.of(personList);
        when(personRepository.findByRole(Mockito.<Role>any())).thenReturn(ofResult);

        Role role = new Role();
        role.setName("Name");
        role.setPersons(new ArrayList<>());
        role.setUuid(UUID.randomUUID());

        // Act
        List<Person> actualFindByRoleResult = personService.findByRole(role);

        // Assert
        verify(personRepository).findByRole(Mockito.<Role>any());
        assertTrue(actualFindByRoleResult.isEmpty());
        assertSame(personList, actualFindByRoleResult);
    }

    /**
     * Method under test: {@link PersonService#findByRole(Role)}
     */
    @Test
    void testFindByRole2() {
        // Arrange
        when(personRepository.findByRole(Mockito.<Role>any())).thenThrow(new BadRequestException("An error occurred"));

        Role role = new Role();
        role.setName("Name");
        role.setPersons(new ArrayList<>());
        role.setUuid(UUID.randomUUID());

        // Act and Assert
        assertThrows(BadRequestException.class, () -> personService.findByRole(role));
        verify(personRepository).findByRole(Mockito.<Role>any());
    }

    /**
     * Method under test: {@link PersonService#getRoleByPerson(Person)}
     */
    @Test
    void testGetRoleByPerson() throws NotFoundException {
        // Arrange
        when(personRoleService.findByPersonId(Mockito.<Person>any())).thenReturn(new ArrayList<>());

        Person person = new Person();
        person.setId(UUID.randomUUID());
        person.setName("Name");
        person.setPassword("iloveyou");
        person.setRole(new ArrayList<>());

        // Act
        List<Role> actualRoleByPerson = personService.getRoleByPerson(person);

        // Assert
        verify(personRoleService).findByPersonId(Mockito.<Person>any());
        assertTrue(actualRoleByPerson.isEmpty());
    }

    /**
     * Method under test: {@link PersonService#getRoleByPerson(Person)}
     */
    @Test
    void testGetRoleByPerson2() throws NotFoundException {
        // Arrange
        Person personId = new Person();
        personId.setId(UUID.randomUUID());
        personId.setName("Name");
        personId.setPassword("iloveyou");
        personId.setRole(new ArrayList<>());

        Role roleId = new Role();
        roleId.setName("Name");
        roleId.setPersons(new ArrayList<>());
        roleId.setUuid(UUID.randomUUID());

        PersonsRoles personsRoles = new PersonsRoles();
        personsRoles.setId(1L);
        personsRoles.setPersonId(personId);
        personsRoles.setRoleId(roleId);

        ArrayList<PersonsRoles> personsRolesList = new ArrayList<>();
        personsRolesList.add(personsRoles);
        when(personRoleService.findByPersonId(Mockito.<Person>any())).thenReturn(personsRolesList);

        Role role = new Role();
        role.setName("Name");
        role.setPersons(new ArrayList<>());
        role.setUuid(UUID.randomUUID());
        when(roleService.findById(Mockito.<UUID>any())).thenReturn(role);

        Person person = new Person();
        person.setId(UUID.randomUUID());
        person.setName("Name");
        person.setPassword("iloveyou");
        person.setRole(new ArrayList<>());

        // Act
        List<Role> actualRoleByPerson = personService.getRoleByPerson(person);

        // Assert
        verify(personRoleService).findByPersonId(Mockito.<Person>any());
        verify(roleService).findById(Mockito.<UUID>any());
        assertEquals(1, actualRoleByPerson.size());
    }

    /**
     * Method under test: {@link PersonService#getRoleByPerson(Person)}
     */
    @Test
    void testGetRoleByPerson3() throws NotFoundException {
        // Arrange
        Person personId = new Person();
        personId.setId(UUID.randomUUID());
        personId.setName("Name");
        personId.setPassword("iloveyou");
        personId.setRole(new ArrayList<>());

        Role roleId = new Role();
        roleId.setName("Name");
        roleId.setPersons(new ArrayList<>());
        roleId.setUuid(UUID.randomUUID());

        PersonsRoles personsRoles = new PersonsRoles();
        personsRoles.setId(1L);
        personsRoles.setPersonId(personId);
        personsRoles.setRoleId(roleId);

        Person personId2 = new Person();
        personId2.setId(UUID.randomUUID());
        personId2.setName("42");
        personId2.setPassword("Password");
        personId2.setRole(new ArrayList<>());

        Role roleId2 = new Role();
        roleId2.setName("42");
        roleId2.setPersons(new ArrayList<>());
        roleId2.setUuid(UUID.randomUUID());

        PersonsRoles personsRoles2 = new PersonsRoles();
        personsRoles2.setId(2L);
        personsRoles2.setPersonId(personId2);
        personsRoles2.setRoleId(roleId2);

        ArrayList<PersonsRoles> personsRolesList = new ArrayList<>();
        personsRolesList.add(personsRoles2);
        personsRolesList.add(personsRoles);
        when(personRoleService.findByPersonId(Mockito.<Person>any())).thenReturn(personsRolesList);

        Role role = new Role();
        role.setName("Name");
        role.setPersons(new ArrayList<>());
        role.setUuid(UUID.randomUUID());
        when(roleService.findById(Mockito.<UUID>any())).thenReturn(role);

        Person person = new Person();
        person.setId(UUID.randomUUID());
        person.setName("Name");
        person.setPassword("iloveyou");
        person.setRole(new ArrayList<>());

        // Act
        List<Role> actualRoleByPerson = personService.getRoleByPerson(person);

        // Assert
        verify(personRoleService).findByPersonId(Mockito.<Person>any());
        verify(roleService, atLeast(1)).findById(Mockito.<UUID>any());
        assertEquals(2, actualRoleByPerson.size());
    }
}
