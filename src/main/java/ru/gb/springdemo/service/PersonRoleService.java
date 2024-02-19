package ru.gb.springdemo.service;

import lombok.AllArgsConstructor;
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
import java.util.concurrent.atomic.AtomicReference;

@Service
@Transactional(readOnly = true,
        propagation = Propagation.REQUIRES_NEW,
        rollbackFor = BadRequestException.class)
@AllArgsConstructor
public class PersonRoleService {
    private final PersonsRolesRepository personsRolesRepository;
//    private final PersonService personService;
//    private final RoleService roleService;

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
        AtomicReference<List<PersonsRoles>> list = new AtomicReference<>(personsRolesRepository.findAll());
//        list.forEach((element) -> {
//            element.setPersonId(personService.findOne(element.getPersonId().getId()));
//            element.setRoleId(roleService.findById(element.getRoleId().getUuid()));
//        });
        return list.get();
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
