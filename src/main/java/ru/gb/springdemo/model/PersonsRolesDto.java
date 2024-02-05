package ru.gb.springdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link PersonsRoles}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonsRolesDto implements Serializable {
    PersonDto personId;
    @NotNull(message = "Not null role for Person")
    RoleDto roleId;
}