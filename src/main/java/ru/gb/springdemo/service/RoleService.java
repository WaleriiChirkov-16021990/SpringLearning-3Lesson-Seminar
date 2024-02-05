package ru.gb.springdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.springdemo.model.Role;
import ru.gb.springdemo.repository.RoleRepository;
import ru.gb.springdemo.util.BadRequestException;
import ru.gb.springdemo.util.NotFoundException;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true,
        propagation = Propagation.MANDATORY,
        rollbackFor = BadRequestException.class)
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    public void saveRole(Role role) throws BadRequestException {
        try {
            roleRepository.save(role);
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
}
