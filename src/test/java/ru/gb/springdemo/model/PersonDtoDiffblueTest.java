package ru.gb.springdemo.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class PersonDtoDiffblueTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link PersonDto#setName(String)}
     *   <li>{@link PersonDto#setRole(List)}
     * </ul>
     */
    @Test
    void testGettersAndSetters() {
        // TODO: Complete this test.
        //   Reason: R002 Missing observers.
        //   Diffblue Cover was unable to create an assertion.
        //   Add getters for the following fields or make them package-private:
        //     PersonDto.name
        //     PersonDto.role

        // Arrange
        PersonDto personDto = new PersonDto();

        // Act
        personDto.setName("Name");
        personDto.setRole(new ArrayList<>());
    }
}
