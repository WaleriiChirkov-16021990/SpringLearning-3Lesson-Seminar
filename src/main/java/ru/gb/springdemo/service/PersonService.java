package ru.gb.springdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.springdemo.model.Person;
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

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional
    public Person savePerson(Person person) throws BadRequestException {
        try {
            personRepository.save(person);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage(), e);
        }

        return person;
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
        return personRepository.findByNameIgnoreCase(name);
    }
    public List<Person> findByRole(Role role) {
        return personRepository.findByRole(role).orElseThrow(NotFoundException::new);
    }

}
