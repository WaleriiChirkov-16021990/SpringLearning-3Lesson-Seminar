package ru.gb.springdemo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cascade;

import java.util.List;
import java.util.UUID;
@Accessors(chain = true)
@Entity(name = "Person")
@Getter
@Setter
@Table(name = "Person")
@NamedQueries({
        @NamedQuery(name = "Person.findDistinctById", query = "select distinct p from Person p where p.id = :id", lockMode = LockModeType.READ),
        @NamedQuery(name = "Person.findByNameIgnoreCaseOrderByNameAsc", query = "select p from Person p where upper(p.name) = upper(:name) order by p.name", lockMode = LockModeType.READ),
        @NamedQuery(name = "Person.existsByNameIgnoreCase", query = "select (count(p) > 0) from Person p where upper(p.name) = upper(:name)"),
        @NamedQuery(name = "Person.deleteById", query = "delete from Person p where p.id = :id"),
        @NamedQuery(name = "Person.updateNameById", query = "update Person p set p.name = :name where p.id = :id")
})
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, name = "name", unique = true)
    private String name;

    @Column(nullable = false, name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinTable(name = "persons_roles",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> role;
}
