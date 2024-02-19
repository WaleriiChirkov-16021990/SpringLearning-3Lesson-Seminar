package ru.gb.springdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link PersonsRoles}
 */
//@Value
@Data
@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor(force = true)
//@Setter
public class PersonsRolesDto implements Serializable {

    @NotNull(message = "Not null id for PersonRole")
    Long id;
    @NotNull(message = "Not null person for Role")
    Person personId;
    @NotNull(message = "Not null role for Person")
    Role roleId;

//    public void setPersonId(PersonDto map) {
//        this.personId = map;
//    }
}