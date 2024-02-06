package ru.gb.springdemo;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import ru.gb.springdemo.model.Person;
import ru.gb.springdemo.model.Role;
import ru.gb.springdemo.repository.PersonRepository;
import ru.gb.springdemo.repository.RoleRepository;
import ru.gb.springdemo.service.PersonService;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class Application {

    // http://localhost:8080/swagger-ui.html

	/*
	 * План занятия:
     -- 0. Анонс группы в телеграмме
     -- 1. Поговорить про стандартную структуру пакетов и "слои" в spring-web приложениях
     2. Поговорить про swagger и его подключение к приложению
     -- 3. Поговорить про REST-соглашения путей
     -- 4. Чуть подробнее рассмотреть исполнение входящего HTTP-запроса (https://mossgreen.github.io/Servlet-Containers-and-Spring-Framework/)
     -- 5. Чуть подробнее поговорить про жизненный цикл бина (и аннотацию PostConstruct)
     -- 6. Чуть подробнее поговорить про жизненный цикл поднятия контекста (и аннотацию EventListener)
     7. Без объяснений показать шаблон взаимодействия с БД (для использования в ДЗ)
     8. TODO
	 */

	/*
	 * слои spring-приложения
	 *
	 * 1. controllers (api)
	 * 2. сервисный слой (services)
	 * 3. репозитории (repositories, dao (data access objects), ...)
	 * 4. jpa-сущности (entity, model, domain)
	 *
	 *
	 * Сервер, отвечающий за выдачу книг в библиотеке.
	 * Нужно напрограммировать ручку, которая либо выдает книгу читателями, либо отказывает в выдаче.
	 *
	 * /book/** - книга
	 * GET /book/25 - получить книгу с идентификатором 25
	 *
	 * /reader/** - читатель
	 * /issue/** - информация о выдаче
	 * POST /issue {"readerId": 25, "bookId": 57} - выдать читателю с идентификатором 25 книгу с идентификатором 57
	 *
	 *

	/*
			Tomcat - контейнер сервлетов (веб-сервер)

			/student/...
			spring-student.war -> tomcat
			/api/...
			spring-api.war -> tomcat

			spring-web.jar
	 */

    public static void main(String[] args) {
//        SpringApplication.run(Application.class, args);
        ApplicationContext application = SpringApplication.run(Application.class, args);

        RoleRepository role = application.getBean(RoleRepository.class);
        PersonService personService = application.getBean(PersonService.class);

        Role issue = new Role();
        issue.setName("ISSUE");
        issue.setUuid(UUID.randomUUID());
        role.save(issue);

        Role book = new Role();
        book.setUuid(UUID.randomUUID());
        book.setName("BOOK");
        role.save(book);

        Role reade = new Role();
        reade.setUuid(UUID.randomUUID());
        reade.setName("READER");
        role.save(reade);

        Person issuer = new Person();
        issuer.setName("Issuer");
        issuer.setPassword("issuer");
        issuer.setRole(List.of(issue));
        personService.savePerson(issuer);

        Person reader = new Person();
        reader.setName("Reader");
        reader.setPassword("reader");
        reader.setRole(List.of(reade));
        personService.savePerson(reader);

        Person booker = new Person();
        booker.setName("Booker");
        booker.setPassword("booker");
        booker.setRole(List.of(book));
        personService.savePerson(booker);

    }

    @Bean
    public static @NotNull ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
