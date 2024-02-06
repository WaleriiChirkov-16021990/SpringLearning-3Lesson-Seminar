package ru.gb.springdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.springdemo.model.Person;
import ru.gb.springdemo.model.PersonsRoles;
import ru.gb.springdemo.model.Role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonsRolesRepository extends JpaRepository<PersonsRoles, Long> {

    Optional<List<PersonsRoles>> findAllByPersonId(Person personId);
    Optional<List<PersonsRoles>> findAllByRoleId(Role roleId);
}