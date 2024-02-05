package ru.gb.springdemo.api;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.gb.springdemo.model.Person;
import ru.gb.springdemo.model.PersonDto;
import ru.gb.springdemo.service.PersonService;
import ru.gb.springdemo.service.RoleService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/people")
public class PeopleController {
    private final PersonService personService;
    private final ModelMapper mapper;
    private final RoleService roleService;

    @Autowired
    public PeopleController(PersonService personService, ModelMapper mapper, RoleService roleService) {
        this.personService = personService;
        this.mapper = mapper;
        this.roleService = roleService;
    }

    @GetMapping
    public List<PersonDto> findAll() {
        return personService.findAll().stream().map((element) ->
                mapper.map(element, PersonDto.class)).collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public PersonDto findById(UUID id) {
        return mapper.map(personService.findOne(id), PersonDto.class);
    }

    @PostMapping
    public PersonDto savePerson(Person person) {
        return mapper.map(personService.savePerson(person), PersonDto.class);
    }

    @DeleteMapping
    public void deletePerson(UUID id) {
        personService.deletePerson(id);
    }

    @PutMapping(value = "/{id}")
    public void updatePerson(@PathVariable("id") UUID id, @RequestBody @Valid Person person) {
        personService.updatePerson(id, person);
    }

    @GetMapping("/name/{name}")
    public PersonDto findByName(@PathVariable("name") String name) {
        return mapper.map(personService.findByName(name), PersonDto.class);
    }

    @GetMapping("/role/{id}")
    public List<PersonDto> findByRole(@PathVariable("id") UUID id) {
        return personService.findByRole(roleService.findById(id)).stream().map((element) ->
                mapper.map(element, PersonDto.class)).collect(Collectors.toList());
    }
}
