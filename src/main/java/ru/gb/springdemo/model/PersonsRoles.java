package ru.gb.springdemo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "persons_roles")
@Data
public class PersonsRoles {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person personId;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "uuid")
    private Role roleId;
}
