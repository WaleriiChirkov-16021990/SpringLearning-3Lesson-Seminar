package ru.gb.springdemo.util;

//import jakarta.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.gb.springdemo.model.Person;
import ru.gb.springdemo.service.PersonService;

@Component
public class UserValidator implements Validator {

    private final PersonService personService;

    @Autowired
    public UserValidator(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(Person.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (personService.findPersonByName(person.getName()).isPresent()) {
            errors.rejectValue("name", person.getName(), "is not a valid name, please try again");
        }
    }
}
