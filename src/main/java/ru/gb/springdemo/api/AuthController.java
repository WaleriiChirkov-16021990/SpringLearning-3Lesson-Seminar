package ru.gb.springdemo.api;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.springdemo.model.Person;
import ru.gb.springdemo.model.PersonDto;
import ru.gb.springdemo.service.PersonService;
import ru.gb.springdemo.util.UserValidator;

@Controller
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final PersonService personService;
    private final UserValidator userValidator;
    private final ModelMapper modelMapper;

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "auth/logout";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") PersonDto person) {
        return "auth/registry";
    }

    @PostMapping("/registration")
    public String register(@ModelAttribute("person") @Valid PersonDto personDto, BindingResult bindingResult) {
        userValidator.validate(personDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "auth/registry";
        }
        personService.savePerson(modelMapper.map(personDto, Person.class));
        return "auth/login";
    }
}
