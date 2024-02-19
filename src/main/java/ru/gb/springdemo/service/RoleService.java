package ru.gb.springdemo.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.springdemo.model.Person;
import ru.gb.springdemo.model.Role;
import ru.gb.springdemo.repository.RoleRepository;
import ru.gb.springdemo.util.BadRequestException;
import ru.gb.springdemo.util.NotFoundException;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@Transactional(readOnly = true,
//        propagation = Propagation.REQUIRES_NEW,
        rollbackFor = BadRequestException.class)
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    @Transactional
    public Role save(Role role) throws BadRequestException {
        try {
            return roleRepository.save(role);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage(), e);
        }
    }

    @Transactional
    public void deleteRole(UUID uuid) throws BadRequestException {
        try {
            roleRepository.deleteById(uuid);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage(), e);
        }
    }

    @Transactional
    public void updateRole(UUID id, Role role) throws BadRequestException {
        try {
            role.setUuid(id);
            this.roleRepository.save(role);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage(), e);
        }
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role findById(UUID id) {
        return roleRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Role findByName(String name) {
        return roleRepository.findByNameIgnoreCase(name).orElseThrow(NotFoundException::new);
    }

    public List<Role> findByPersonsOrderByPersons_IdAsc(Person person) {
        log.info(person.toString());
        return roleRepository.findByPersonsOrderByPersons_IdAsc(person, null);
    }

    @jakarta.transaction.Transactional
    public void deleteAll() {
        roleRepository.deleteAll();
    }
}
