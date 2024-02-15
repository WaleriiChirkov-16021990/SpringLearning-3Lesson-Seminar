package ru.gb.springdemo.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;
import java.util.UUID;
import static org.hibernate.annotations.CascadeType.ALL;
@Entity(name = "Person")
@Getter
@Setter
@Table(name = "person")
@NamedQueries({
        @NamedQuery(name = "Person.findDistinctById", query = "select distinct p from Person p where p.id = :id", lockMode = LockModeType.READ),
        @NamedQuery(name = "Person.findByNameIgnoreCaseOrderByNameAsc", query = "select p from Person p where upper(p.name) = upper(:name) order by p.name", lockMode = LockModeType.READ),
        @NamedQuery(name = "Person.existsByNameIgnoreCase", query = "select (count(p) > 0) from Person p where upper(p.name) = upper(:name)"),
        @NamedQuery(name = "Person.deleteById", query = "delete from Person p where p.id = :id"),
        @NamedQuery(name = "Person.updateNameById", query = "update Person p set p.name = :name where p.id = :id")
})
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @Column(nullable = false, name = "name", unique = true)
    private String name;

    @Column(nullable = false, name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(ALL)
    @JoinTable(name = "persons_roles",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> role;

    public Person(UUID uuid, String john, String password) {
        this.id = uuid;
        this.name = john;
        this.password = password;
    }
}
