package ru.gb.springdemo.util;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import ru.gb.springdemo.model.Book;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.model.Person;
import ru.gb.springdemo.model.Reader;
import ru.gb.springdemo.service.PersonService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserValidatorTest {

    @Mock
    private PersonService personService;


    @Test
    @Order(2)
    void supports() {
        Class<?> clazz = Person.class;
        assertTrue(clazz.isAssignableFrom(Person.class));
        assertFalse(clazz.isAssignableFrom(Book.class));
        assertFalse(clazz.isAssignableFrom(Issue.class));
        assertFalse(clazz.isAssignableFrom(Reader.class));
    }

    @Test
    @Order(3)
    void validate() {
        // Given test when validating successfully
        UserValidator userValidator = new UserValidator(personService);
        Person person = new Person();
        person.setName("John");
        when(personService.findPersonByName(person.getName())).thenReturn(Optional.empty());
        Errors errors = new BeanPropertyBindingResult(person, "person");
        // When
        userValidator.validate(person, errors);
        // Then
        verify(personService, times(1)).findPersonByName(person.getName());
        // Assert that no errors have been recorded
        assertFalse(errors.hasErrors());
    }

    @Test
    @Order(4)
    void validateError() {
        //  When validation fails
        UserValidator userValidator2 = new UserValidator(personService);
        Person person2 = new Person();
        person2.setName("John");
        when(personService.findPersonByName(person2.getName())).thenReturn(Optional.of(person2));
        Errors errors2 = new BeanPropertyBindingResult(person2, "person");
        // When
        userValidator2.validate(person2, errors2);
        // Then
        verify(personService, times(1)).findPersonByName(person2.getName());
        // Assert that an error has been recorded
        assertTrue(errors2.hasErrors());
        // Assert that the "name" field has a validation error
        assert errors2.hasFieldErrors("name");
    }
}