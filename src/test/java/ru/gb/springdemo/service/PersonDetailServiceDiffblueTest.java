package ru.gb.springdemo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.gb.springdemo.model.Person;
import ru.gb.springdemo.model.Role;
import ru.gb.springdemo.util.NotFoundException;

@ContextConfiguration(classes = {PersonDetailService.class})
@ExtendWith(SpringExtension.class)
class PersonDetailServiceDiffblueTest {
    @Autowired
    private PersonDetailService personDetailService;

    @MockBean
    private PersonService personService;

    /**
     * Method under test: {@link PersonDetailService#loadUserByUsername(String)}
     */
    @Test
    void testLoadUserByUsername() throws UsernameNotFoundException, NotFoundException {
        // Arrange
        Role role = new Role();
        role.setName("User details loaded: {}");
        role.setPersons(new ArrayList<>());
        role.setUuid(UUID.randomUUID());

        ArrayList<Role> role2 = new ArrayList<>();
        role2.add(role);

        Person person = new Person();
        person.setId(UUID.randomUUID());
        person.setName("Name");
        person.setPassword("iloveyou");
        person.setRole(role2);
        Optional<Person> ofResult = Optional.of(person);
        when(personService.findPersonByName(Mockito.<String>any())).thenReturn(ofResult);

        // Act
        UserDetails actualLoadUserByUsernameResult = personDetailService.loadUserByUsername("janedoe");

        // Assert
        verify(personService).findPersonByName(Mockito.<String>any());
        assertEquals("iloveyou", actualLoadUserByUsernameResult.getPassword());
    }

    /**
     * Method under test: {@link PersonDetailService#loadUserByUsername(String)}
     */
    @Test
    void testLoadUserByUsername2() throws UsernameNotFoundException, NotFoundException {
        // Arrange
        Optional<Person> emptyResult = Optional.empty();
        when(personService.findPersonByName(Mockito.<String>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(UsernameNotFoundException.class, () -> personDetailService.loadUserByUsername("janedoe"));
        verify(personService).findPersonByName(Mockito.<String>any());
    }

    /**
     * Method under test: {@link PersonDetailService#loadUserByUsername(String)}
     */
    @Test
    void testLoadUserByUsername3() throws UsernameNotFoundException, NotFoundException {
        // Arrange
        when(personService.findPersonByName(Mockito.<String>any()))
                .thenThrow(new UsernameNotFoundException("User not found"));

        // Act and Assert
        assertThrows(UsernameNotFoundException.class, () -> personDetailService.loadUserByUsername("janedoe"));
        verify(personService).findPersonByName(Mockito.<String>any());
    }
}
