package ru.gb.springdemo.model;

import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link Role}
 */
@Value
public class RoleDto implements Serializable {
    UUID uuid;
    String name;
}