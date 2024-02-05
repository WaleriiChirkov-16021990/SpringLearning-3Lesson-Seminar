package ru.gb.springdemo.model;

import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link Person}
 */
@Value
public class PersonDto implements Serializable {
    String name;
//    RoleDto role;
}