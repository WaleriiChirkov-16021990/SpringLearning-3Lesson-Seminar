package ru.gb.springdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.springdemo.config.SecurityConfig;
import ru.gb.springdemo.model.Person;
import ru.gb.springdemo.model.PersonsRoles;
import ru.gb.springdemo.model.Role;
import ru.gb.springdemo.repository.PersonRepository;
import ru.gb.springdemo.util.BadRequestException;
import ru.gb.springdemo.util.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true,
        propagation = Propagation.REQUIRED,
        rollbackFor = BadRequestException.class)
public class PersonService {
    private final PersonRepository personRepository;
    private final PersonRoleService personRoleService;
    private final RoleService roleService;

    @Autowired
    public PersonService(PersonRepository personRepository, PersonRoleService personRoleService, RoleService roleService) {
        this.personRepository = personRepository;
        this.personRoleService = personRoleService;
        this.roleService = roleService;
    }

    @Transactional
    public Person save(Person person) throws BadRequestException {
        try {
            enrichPerson(person);
            return personRepository.save(person);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage(), e);
        }


    }

    private void enrichPerson(Person person) {
        if (person.getId() == null) {
            person.setId(UUID.randomUUID());
        }
        person.setPassword(SecurityConfig.getPasswordEncoder().encode(person.getPassword()));
        System.out.println(person.getPassword());
    }

    @Transactional
    public void deletePerson(UUID uuid) throws BadRequestException {
        try {
            personRepository.deleteById(uuid);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage(), e);
        }

    }

    @Transactional
    public void updatePerson(UUID id, Person person) throws BadRequestException {
        try {
            person.setId(id);
            this.personRepository.save(person);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage(), e);
        }
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person findOne(UUID id) throws NotFoundException {
        return personRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Person findByName(String name) throws NotFoundException {
        return personRepository.findByNameIgnoreCase(name).orElseThrow(NotFoundException::new);
    }

    public Optional<Person> findPersonByName(String name) throws NotFoundException {
        Person person = personRepository.findByNameIgnoreCase(name).orElseThrow(NotFoundException::new);
        person.setRole(this.getRoleByPerson(person));
        return Optional.of(person);
    }

    public List<Person> findByRole(UUID role) {
        Role byId = roleService.findById(role);
        return personRepository.findByRole(byId).orElseThrow(NotFoundException::new);
    }

    public List<Role> getRoleByPerson(Person person) throws NotFoundException {
        List<PersonsRoles> byPersonId = personRoleService.findByPersonId(person);
        return byPersonId.stream().map(role -> roleService.findById(role.getRoleId().getUuid())).toList();
    }

    @jakarta.transaction.Transactional
    public void deleteAll() {
        personRepository.deleteAll();
    }
}
