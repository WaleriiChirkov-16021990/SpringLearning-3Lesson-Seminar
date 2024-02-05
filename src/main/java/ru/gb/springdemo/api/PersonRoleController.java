package ru.gb.springdemo.api;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import ru.gb.springdemo.model.Person;
import ru.gb.springdemo.model.PersonsRoles;
import ru.gb.springdemo.model.PersonsRolesDto;
import ru.gb.springdemo.model.Role;
import ru.gb.springdemo.service.PersonRoleService;
import ru.gb.springdemo.service.PersonService;
import ru.gb.springdemo.service.RoleService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/people_roles")
public class PersonRoleController {
    private final PersonRoleService personRoleService;
    private final PersonService personService;

    private final RoleService roleService;
    private final ModelMapper mapper;

    @Autowired
    public PersonRoleController(PersonRoleService personRoleService, PersonService personService, RoleService roleService, ModelMapper mapper) {
        this.personRoleService = personRoleService;
        this.personService = personService;
        this.roleService = roleService;
        this.mapper = mapper;
    }

    @GetMapping
    public List<PersonsRolesDto> findAll() {
        return personRoleService.findAll().stream().map((element) ->
                mapper.map(element, PersonsRolesDto.class)).collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public PersonsRolesDto findById(@PathVariable("id") UUID id) {
        return mapper.map(personRoleService.findById(id), PersonsRolesDto.class);
    }

    @GetMapping("/by_person/{id}")
    public List<PersonsRolesDto> findByName(@PathVariable("id") UUID personId) {
        Person person = personService.findOne(personId);
        return personRoleService.findByPersonId(person).stream().map((element) ->
                mapper.map(element, PersonsRolesDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/by_role/{id}")
    public List<PersonsRolesDto> findByRole(@PathVariable("id") UUID roleId) {
        Role role = roleService.findById(roleId);
        return personRoleService.findByRoleId(role).stream().map((element) ->
                mapper.map(element, PersonsRolesDto.class)).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") UUID id) {
        personRoleService.deletePersonRole(id);
    }

    @PostMapping
    public PersonsRolesDto save(@RequestBody @Valid PersonsRolesDto personsRolesDto) {
        personRoleService.savePersonRole(mapper.map(personsRolesDto, PersonsRoles.class));
        return personsRolesDto;
    }

    @PutMapping("/edit/{id}")
    public PersonsRolesDto update(@PathVariable("id") UUID id, @RequestBody @Valid PersonsRolesDto personsRolesDto) {
        personRoleService.updatePersonRole(id, mapper.map(personsRolesDto, PersonsRoles.class));
        return personsRolesDto;
    }
}
