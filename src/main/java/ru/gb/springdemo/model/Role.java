package ru.gb.springdemo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Roles")
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "role")
    private List<Person> persons;
}
