package ru.gb.springdemo.api;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
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
    public ResponseEntity<List<PersonDto>> findAll() {
        return new ResponseEntity<>(personService.findAll().stream().map((element) ->
                mapper.map(element, PersonDto.class)).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PersonDto> findById(UUID id) {
        return new ResponseEntity<>(mapper.map(personService.findOne(id), PersonDto.class), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PersonDto> savePerson(@RequestBody @Valid Person person) {
        return new ResponseEntity<>(mapper.map(personService.savePerson(person), PersonDto.class), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deletePerson(@PathVariable("id") UUID id) {
        personService.deletePerson(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> updatePerson(@PathVariable("id") UUID id, @RequestBody @Valid Person person) {
        personService.updatePerson(id, person);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<PersonDto> findByName(@PathVariable("name") String name) {
        return new ResponseEntity<>(mapper.map(personService.findByName(name), PersonDto.class), HttpStatus.OK);
    }

    @GetMapping("/role/{id}")
    public ResponseEntity<List<PersonDto>> findByRole(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(personService.findByRole(roleService.findById(id)).stream().map((element) ->
                mapper.map(element, PersonDto.class)).collect(Collectors.toList()), HttpStatus.OK);
    }
}
