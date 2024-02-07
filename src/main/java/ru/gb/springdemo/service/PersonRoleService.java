package ru.gb.springdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.springdemo.model.Person;
import ru.gb.springdemo.model.PersonsRoles;
import ru.gb.springdemo.model.Role;
import ru.gb.springdemo.repository.PersonsRolesRepository;
import ru.gb.springdemo.util.BadRequestException;
import ru.gb.springdemo.util.NotFoundException;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true,
        propagation = Propagation.REQUIRES_NEW,
        rollbackFor = BadRequestException.class)
@RequiredArgsConstructor

public class PersonRoleService {
    @Autowired
    private final PersonsRolesRepository personsRolesRepository;

    @Transactional
    public void savePersonRole(PersonsRoles personsRoles) throws BadRequestException {
        try {
            personsRolesRepository.save(personsRoles);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage(), e);
        }
    }

    @Transactional
    public void deletePersonRole(Long uuid) throws BadRequestException {
        try {
            personsRolesRepository.deleteById(uuid);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage(), e);
        }
    }

    @Transactional
    public void updatePersonRole(Long id, PersonsRoles personsRoles) throws BadRequestException {
        try {
            personsRoles.setId(id);
            this.personsRolesRepository.save(personsRoles);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage(), e);
        }
    }

    public List<PersonsRoles> findAll() {
        return personsRolesRepository.findAll().stream().toList();
    }

    public PersonsRoles findById(Long id) {
        return personsRolesRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public List<PersonsRoles> findByPersonId(Person personId) throws NotFoundException {
        return personsRolesRepository.findAllByPersonId(personId).orElseThrow(NotFoundException::new);
    }

    public List<PersonsRoles> findByRoleId(Role roleId) throws NotFoundException {
        return personsRolesRepository.findAllByRoleId(roleId).orElseThrow(NotFoundException::new);
    }
}
