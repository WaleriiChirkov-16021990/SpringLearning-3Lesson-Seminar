package ru.gb.springdemo.api;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.gb.springdemo.model.Role;
import ru.gb.springdemo.dto.RoleDto;
import ru.gb.springdemo.service.RoleService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/roles")
public class RoleController {
    private final RoleService roleService;
    private final ModelMapper mapper;

    @Autowired
    public RoleController(RoleService roleService, ModelMapper mapper) {
        this.roleService = roleService;
        this.mapper = mapper;
    }

    @GetMapping
    public List<RoleDto> findAll() {
        return roleService.findAll().stream().map((element) ->
                mapper.map(element, RoleDto.class)).collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public RoleDto findById(@PathVariable("id") UUID id) {
        return mapper.map(roleService.findById(id), RoleDto.class);
    }

    @GetMapping("/name/{name}")
    public RoleDto findByName(@PathVariable("name") String name) {
        return mapper.map(roleService.findByName(name), RoleDto.class);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteRole(@PathVariable("id") UUID id) {
        roleService.deleteRole(id);
    }

    @PostMapping
    public RoleDto saveRole(@RequestBody @Valid Role role) {
        roleService.saveRole(role);
        return mapper.map(role, RoleDto.class);
    }

    @PutMapping("/{id}")
    public RoleDto updateRole(@PathVariable("id") UUID id, @RequestBody @Valid Role role) {
        roleService.updateRole(id, role);
        return mapper.map(role, RoleDto.class);
    }
}
