package ru.gb.springdemo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/*
@NamedQueries({
        @NamedQuery(name = "Person.findDistinctById", query = "select distinct p from Person p where p.id = :id", lockMode = LockModeType.READ),
        @NamedQuery(name = "Person.findByNameIgnoreCaseOrderByNameAsc", query = "select p from Person p where upper(p.name) = upper(:name) order by p.name", lockMode = LockModeType.READ),
        @NamedQuery(name = "Person.existsByNameIgnoreCase", query = "select (count(p) > 0) from Person p where upper(p.name) = upper(:name)"),
        @NamedQuery(name = "Person.deleteById", query = "delete from Person p where p.id = :id"),
        @NamedQuery(name = "Person.updateNameById", query = "update Person p set p.name = :name where p.id = :id")
})
 */
@Entity(name = "Person")
@Data
@Table(name = "person")
@NamedQueries({
        @NamedQuery(name = "Person.findDistinctById", query = "select distinct p from Person p where p.id = :id", lockMode = LockModeType.READ),
        @NamedQuery(name = "Person.findByNameIgnoreCaseOrderByNameAsc", query = "select p from Person p where upper(p.name) = upper(:name) order by p.name", lockMode = LockModeType.READ),
        @NamedQuery(name = "Person.existsByNameIgnoreCase", query = "select (count(p) > 0) from Person p where upper(p.name) = upper(:name)"),
        @NamedQuery(name = "Person.deleteById", query = "delete from Person p where p.id = :id"),
        @NamedQuery(name = "Person.updateNameById", query = "update Person p set p.name = :name where p.id = :id")
})
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "persons_roles",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> role;

}
