package ru.gb.springdemo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    private UUID uuid;
//
//    @Column(name = "name", length = 255, nullable = false)
//    private String name;
//
//    @JsonBackReference
//    @ManyToMany(mappedBy = "role")
//    private List<Person> persons;


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @JsonBackReference
    @ManyToMany(mappedBy = "role")
    private List<Person> persons;

    public Role(UUID uuid, String guest) {
    }
}

