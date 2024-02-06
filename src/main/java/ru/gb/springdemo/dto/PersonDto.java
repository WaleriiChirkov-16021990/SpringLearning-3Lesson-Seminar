package ru.gb.springdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import ru.gb.springdemo.model.Person;
import ru.gb.springdemo.model.Role;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * DTO for {@link Person}
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersonDto implements Serializable {
    String name;
    List<Role> role;

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(List<Role> role) {
        this.role = role;
    }
}