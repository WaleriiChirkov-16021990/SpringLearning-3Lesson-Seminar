package ru.gb.springdemo.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import ru.gb.springdemo.model.Person;
import ru.gb.springdemo.model.PersonsRoles;
import ru.gb.springdemo.model.Role;
import ru.gb.springdemo.repository.PersonRepository;
import ru.gb.springdemo.repository.PersonsRolesRepository;
import ru.gb.springdemo.repository.RoleRepository;
import ru.gb.springdemo.service.PersonRoleService;
import ru.gb.springdemo.service.PersonService;
import ru.gb.springdemo.service.RoleService;

class UserValidatorDiffblueTest {
    /**
     * Method under test: {@link UserValidator#supports(Class)}
     */
    @Test
    void testSupports() {
        // Arrange
        PersonRepository personRepository = mock(PersonRepository.class);
        PersonRoleService personRoleService = new PersonRoleService(mock(PersonsRolesRepository.class));
        UserValidator userValidator = new UserValidator(
                new PersonService(personRepository, personRoleService, new RoleService(mock(RoleRepository.class))));
        Class<Object> clazz = Object.class;

        // Act and Assert
        assertTrue(userValidator.supports(clazz));
    }

    /**
     * Method under test: {@link UserValidator#validate(Object, Errors)}
     */
    @Test
    void testValidate() {
        // Arrange
        Person person = new Person();
        person.setId(UUID.randomUUID());
        person.setName("Name");
        person.setPassword("iloveyou");
        person.setRole(new ArrayList<>());
        Optional<Person> ofResult = Optional.of(person);
        PersonRepository personRepository = mock(PersonRepository.class);
        when(personRepository.findByNameIgnoreCase(Mockito.<String>any())).thenReturn(ofResult);
        PersonsRolesRepository personsRolesRepository = mock(PersonsRolesRepository.class);
        Optional<List<PersonsRoles>> ofResult2 = Optional.of(new ArrayList<>());
        when(personsRolesRepository.findAllByPersonId(Mockito.<Person>any())).thenReturn(ofResult2);
        PersonRoleService personRoleService = new PersonRoleService(personsRolesRepository);
        UserValidator userValidator = new UserValidator(
                new PersonService(personRepository, personRoleService, new RoleService(mock(RoleRepository.class))));

        Person person2 = new Person();
        person2.setId(UUID.randomUUID());
        person2.setName("Name");
        person2.setPassword("iloveyou");
        person2.setRole(new ArrayList<>());
        BindException errors = new BindException(person2, "ru.gb.springdemo.model.Person");

        // Act
        userValidator.validate(person2, errors);

        // Assert
        verify(personRepository).findByNameIgnoreCase(Mockito.<String>any());
        verify(personsRolesRepository).findAllByPersonId(Mockito.<Person>any());
        FieldError fieldError = errors.getFieldError();
        assertEquals("Name", fieldError.getRejectedValue());
        assertEquals("name", fieldError.getField());
        assertEquals("ru.gb.springdemo.model.Person", fieldError.getObjectName());
        assertFalse(fieldError.isBindingFailure());
        BindingResult bindingResult = errors.getBindingResult();
        assertTrue(bindingResult.hasErrors());
        assertSame(errors.getPropertyEditorRegistry(), ((BeanPropertyBindingResult) bindingResult).getPropertyAccessor());
    }

    /**
     * Method under test: {@link UserValidator#validate(Object, Errors)}
     */
    @Test
    void testValidate2() {
        // Arrange
        Person person = new Person();
        person.setId(UUID.randomUUID());
        person.setName("Name");
        person.setPassword("iloveyou");
        person.setRole(new ArrayList<>());
        Optional<Person> ofResult = Optional.of(person);
        PersonRepository personRepository = mock(PersonRepository.class);
        when(personRepository.findByNameIgnoreCase(Mockito.<String>any())).thenReturn(ofResult);

        Person personId = new Person();
        personId.setId(UUID.randomUUID());
        personId.setName("name");
        personId.setPassword("iloveyou");
        personId.setRole(new ArrayList<>());

        Role roleId = new Role();
        roleId.setName("name");
        roleId.setPersons(new ArrayList<>());
        roleId.setUuid(UUID.randomUUID());

        PersonsRoles personsRoles = new PersonsRoles();
        personsRoles.setId(1L);
        personsRoles.setPersonId(personId);
        personsRoles.setRoleId(roleId);

        ArrayList<PersonsRoles> personsRolesList = new ArrayList<>();
        personsRolesList.add(personsRoles);
        Optional<List<PersonsRoles>> ofResult2 = Optional.of(personsRolesList);
        PersonsRolesRepository personsRolesRepository = mock(PersonsRolesRepository.class);
        when(personsRolesRepository.findAllByPersonId(Mockito.<Person>any())).thenReturn(ofResult2);
        PersonRoleService personRoleService = new PersonRoleService(personsRolesRepository);

        Role role = new Role();
        role.setName("Name");
        role.setPersons(new ArrayList<>());
        role.setUuid(UUID.randomUUID());
        Optional<Role> ofResult3 = Optional.of(role);
        RoleRepository roleRepository = mock(RoleRepository.class);
        when(roleRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult3);
        UserValidator userValidator = new UserValidator(
                new PersonService(personRepository, personRoleService, new RoleService(roleRepository)));

        Person person2 = new Person();
        person2.setId(UUID.randomUUID());
        person2.setName("Name");
        person2.setPassword("iloveyou");
        person2.setRole(new ArrayList<>());
        BindException errors = new BindException(person2, "ru.gb.springdemo.model.Person");

        // Act
        userValidator.validate(person2, errors);

        // Assert
        verify(roleRepository).findById(Mockito.<UUID>any());
        verify(personRepository).findByNameIgnoreCase(Mockito.<String>any());
        verify(personsRolesRepository).findAllByPersonId(Mockito.<Person>any());
        FieldError fieldError = errors.getFieldError();
        assertEquals("Name", fieldError.getRejectedValue());
        assertEquals("name", fieldError.getField());
        assertEquals("ru.gb.springdemo.model.Person", fieldError.getObjectName());
        assertFalse(fieldError.isBindingFailure());
        BindingResult bindingResult = errors.getBindingResult();
        assertTrue(bindingResult.hasErrors());
        assertSame(errors.getPropertyEditorRegistry(), ((BeanPropertyBindingResult) bindingResult).getPropertyAccessor());
    }

    /**
     * Method under test: {@link UserValidator#validate(Object, Errors)}
     */
    @Test
    void testValidate3() {
        // Arrange
        Person person = new Person();
        person.setId(UUID.randomUUID());
        person.setName("Name");
        person.setPassword("iloveyou");
        person.setRole(new ArrayList<>());
        Optional<Person> ofResult = Optional.of(person);
        PersonRepository personRepository = mock(PersonRepository.class);
        when(personRepository.findByNameIgnoreCase(Mockito.<String>any())).thenReturn(ofResult);

        Person personId = new Person();
        personId.setId(UUID.randomUUID());
        personId.setName("name");
        personId.setPassword("iloveyou");
        personId.setRole(new ArrayList<>());

        Role roleId = new Role();
        roleId.setName("name");
        roleId.setPersons(new ArrayList<>());
        roleId.setUuid(UUID.randomUUID());

        PersonsRoles personsRoles = new PersonsRoles();
        personsRoles.setId(1L);
        personsRoles.setPersonId(personId);
        personsRoles.setRoleId(roleId);

        Person personId2 = new Person();
        personId2.setId(UUID.randomUUID());
        personId2.setName("is not a valid name, please try again");
        personId2.setPassword("name");
        personId2.setRole(new ArrayList<>());

        Role roleId2 = new Role();
        roleId2.setName("is not a valid name, please try again");
        roleId2.setPersons(new ArrayList<>());
        roleId2.setUuid(UUID.randomUUID());

        PersonsRoles personsRoles2 = new PersonsRoles();
        personsRoles2.setId(2L);
        personsRoles2.setPersonId(personId2);
        personsRoles2.setRoleId(roleId2);

        ArrayList<PersonsRoles> personsRolesList = new ArrayList<>();
        personsRolesList.add(personsRoles2);
        personsRolesList.add(personsRoles);
        Optional<List<PersonsRoles>> ofResult2 = Optional.of(personsRolesList);
        PersonsRolesRepository personsRolesRepository = mock(PersonsRolesRepository.class);
        when(personsRolesRepository.findAllByPersonId(Mockito.<Person>any())).thenReturn(ofResult2);
        PersonRoleService personRoleService = new PersonRoleService(personsRolesRepository);

        Role role = new Role();
        role.setName("Name");
        role.setPersons(new ArrayList<>());
        role.setUuid(UUID.randomUUID());
        Optional<Role> ofResult3 = Optional.of(role);
        RoleRepository roleRepository = mock(RoleRepository.class);
        when(roleRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult3);
        UserValidator userValidator = new UserValidator(
                new PersonService(personRepository, personRoleService, new RoleService(roleRepository)));

        Person person2 = new Person();
        person2.setId(UUID.randomUUID());
        person2.setName("Name");
        person2.setPassword("iloveyou");
        person2.setRole(new ArrayList<>());
        BindException errors = new BindException(person2, "ru.gb.springdemo.model.Person");

        // Act
        userValidator.validate(person2, errors);

        // Assert
        verify(roleRepository, atLeast(1)).findById(Mockito.<UUID>any());
        verify(personRepository).findByNameIgnoreCase(Mockito.<String>any());
        verify(personsRolesRepository).findAllByPersonId(Mockito.<Person>any());
        FieldError fieldError = errors.getFieldError();
        assertEquals("Name", fieldError.getRejectedValue());
        assertEquals("name", fieldError.getField());
        assertEquals("ru.gb.springdemo.model.Person", fieldError.getObjectName());
        assertFalse(fieldError.isBindingFailure());
        BindingResult bindingResult = errors.getBindingResult();
        assertTrue(bindingResult.hasErrors());
        assertSame(errors.getPropertyEditorRegistry(), ((BeanPropertyBindingResult) bindingResult).getPropertyAccessor());
    }
}
