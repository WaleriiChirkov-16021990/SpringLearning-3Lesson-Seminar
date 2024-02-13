package ru.gb.springdemo.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import ru.gb.springdemo.model.Person;
import ru.gb.springdemo.model.Role;

@ContextConfiguration(classes = {RoleRepository.class})
@EnableAutoConfiguration
@EntityScan(basePackages = {"ru.gb.springdemo.model"})
@DataJpaTest(properties = {"spring.main.allow-bean-definition-overriding=true"})
class RoleRepositoryDiffblueTest {
    @Autowired
    private RoleRepository roleRepository;

    /**
     * Method under test: {@link RoleRepository#findByNameIgnoreCase(String)}
     */
    @Test
    void testFindByNameIgnoreCase() {
        // Arrange
        Role role = new Role();
        role.setName("Name");
        role.setPersons(new ArrayList<>());

        Role role2 = new Role();
        role2.setName("42");
        role2.setPersons(new ArrayList<>());
        roleRepository.save(role);
        roleRepository.save(role2);

        // Act and Assert
        assertTrue(roleRepository.findByNameIgnoreCase("Name").isPresent());
    }

    /**
     * Method under test:
     * {@link RoleRepository#findRoleByPersonsIsNotNull(Person, Pageable)} (Person, Pageable)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testFindByPersonsOrderByPersons_IdAsc() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   org.springframework.dao.InvalidDataAccessApiUsageException: org.hibernate.TransientObjectException: object references an unsaved transient instance - save the transient instance before flushing: ru.gb.springdemo.model.Person
        //       at jdk.proxy4/jdk.proxy4.$Proxy165.findByPersonsOrderByPersons_IdAsc(Unknown Source)
        //   java.lang.IllegalStateException: org.hibernate.TransientObjectException: object references an unsaved transient instance - save the transient instance before flushing: ru.gb.springdemo.model.Person
        //       at org.hibernate.internal.ExceptionConverterImpl.convert(ExceptionConverterImpl.java:157)
        //       at org.hibernate.query.spi.AbstractSelectionQuery.list(AbstractSelectionQuery.java:438)
        //       at org.hibernate.query.Query.getResultList(Query.java:120)
        //       at jdk.proxy4/jdk.proxy4.$Proxy165.findByPersonsOrderByPersons_IdAsc(Unknown Source)
        //   org.hibernate.TransientObjectException: object references an unsaved transient instance - save the transient instance before flushing: ru.gb.springdemo.model.Person
        //       at org.hibernate.metamodel.mapping.EntityIdentifierMapping.getIdentifierIfNotUnsaved(EntityIdentifierMapping.java:106)
        //       at org.hibernate.query.sqm.internal.SqmUtil.createValueBindings(SqmUtil.java:444)
        //       at org.hibernate.query.sqm.internal.SqmUtil.createJdbcParameterBindings(SqmUtil.java:397)
        //       at org.hibernate.query.sqm.internal.ConcreteSqmSelectQueryPlan.buildCacheableSqmInterpretation(ConcreteSqmSelectQueryPlan.java:413)
        //       at org.hibernate.query.sqm.internal.ConcreteSqmSelectQueryPlan.withCacheableSqmInterpretation(ConcreteSqmSelectQueryPlan.java:324)
        //       at org.hibernate.query.sqm.internal.ConcreteSqmSelectQueryPlan.performList(ConcreteSqmSelectQueryPlan.java:300)
        //       at org.hibernate.query.sqm.internal.QuerySqmImpl.doList(QuerySqmImpl.java:509)
        //       at org.hibernate.query.spi.AbstractSelectionQuery.list(AbstractSelectionQuery.java:427)
        //       at org.hibernate.query.Query.getResultList(Query.java:120)
        //       at jdk.proxy4/jdk.proxy4.$Proxy165.findByPersonsOrderByPersons_IdAsc(Unknown Source)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        Role role = new Role();
        role.setName("Name");
        role.setPersons(new ArrayList<>());

        Role role2 = new Role();
        role2.setName("42");
        role2.setPersons(new ArrayList<>());
        roleRepository.save(role);
        roleRepository.save(role2);

        Person person = new Person();
        person.setName("Name");
        person.setPassword("iloveyou");
        person.setRole(new ArrayList<>());

        // Act
        roleRepository.findRoleByPersonsIsNotNull(person, Pageable.unpaged());
    }
}
