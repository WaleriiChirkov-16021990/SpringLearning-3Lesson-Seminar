package ru.gb.springdemo.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import ru.gb.springdemo.model.Person;
import ru.gb.springdemo.model.Role;

@ContextConfiguration(classes = {PersonRepository.class})
@EnableAutoConfiguration
@EntityScan(basePackages = {"ru.gb.springdemo.model"})
@DataJpaTest(properties = {"spring.main.allow-bean-definition-overriding=true"})
class PersonRepositoryDiffblueTest {
    @Autowired
    private PersonRepository personRepository;

    /**
     * Method under test: {@link PersonRepository#findByRole(Role)}
     */
    @Test
//    @Disabled("TODO: Complete this test")
    void testFindByRole() {

        // Arrange
        Person person = new Person();
        person.setName("Name");
        person.setPassword("iloveyou");
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("USER"));
        person.setRole(roles);


        Person person2 = new Person();
        person2.setName("42");
        person2.setPassword("Password");
        List<Role> roles2 = new ArrayList<>();
        roles2.add(new Role("ADMIN"));
        person2.setRole(roles2);
        personRepository.save(person);
        personRepository.save(person2);

        Role role = new Role();
        role.setName("USER");
        List<Person> persons = new ArrayList<>();
        persons.add(person);
        role.setPersons(persons);

        // Act
        when(personRepository.findByRole(role)).thenReturn((Optional.of(List.of(person))));


    }

    /**
     * Method under test: {@link PersonRepository#findByNameIgnoreCase(String)}
     */
    @Test
    void testFindByNameIgnoreCase() {
        // Arrange
        Person person = new Person();
        person.setName("Name");
        person.setPassword("iloveyou");
        person.setRole(new ArrayList<>());

        Person person2 = new Person();
        person2.setName("42");
        person2.setPassword("Password");
        person2.setRole(new ArrayList<>());
        personRepository.save(person);
        personRepository.save(person2);

        // Act and Assert
        assertTrue(personRepository.findByNameIgnoreCase("name").isPresent());
    }

    /**
     * Method under test: {@link PersonRepository#existsByNameIgnoreCase(String)}
     */
    @Test
    void testExistsByNameIgnoreCase() {
        // Arrange
        Person person = new Person();
        person.setName("Name");
        person.setPassword("iloveyou");
        person.setRole(new ArrayList<>());

        Person person2 = new Person();
        person2.setName("42");
        person2.setPassword("Password");
        person2.setRole(new ArrayList<>());
        personRepository.save(person);
        personRepository.save(person2);

        // Act and Assert
        assertTrue(personRepository.existsByNameIgnoreCase("Name"));
    }

    /**
     * Method under test: {@link PersonRepository#deleteById(UUID)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testDeleteById() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   org.springframework.dao.DataIntegrityViolationException: could not execute statement [Нарушение уникального индекса или первичного ключа: "PUBLIC.CONSTRAINT_INDEX_8 ON PUBLIC.PERSON(NAME NULLS FIRST) VALUES ( /* 1 */ 'Name' )"
        //   Unique index or primary key violation: "PUBLIC.CONSTRAINT_INDEX_8 ON PUBLIC.PERSON(NAME NULLS FIRST) VALUES ( /* 1 */ 'Name' )"; SQL statement:
        //   insert into person (name,password,id) values (?,?,?) [23505-224]] [insert into person (name,password,id) values (?,?,?)]; SQL [insert into person (name,password,id) values (?,?,?)]; constraint ["PUBLIC.CONSTRAINT_INDEX_8 ON PUBLIC.PERSON(NAME NULLS FIRST) VALUES ( /* 1 */ 'Name' )"; SQL statement:
        //   insert into person (name,password,id) values (?,?,?) [23505-224]]
        //       at jdk.proxy4/jdk.proxy4.$Proxy165.deleteById(Unknown Source)
        //   org.hibernate.exception.ConstraintViolationException: could not execute statement [Нарушение уникального индекса или первичного ключа: "PUBLIC.CONSTRAINT_INDEX_8 ON PUBLIC.PERSON(NAME NULLS FIRST) VALUES ( /* 1 */ 'Name' )"
        //   Unique index or primary key violation: "PUBLIC.CONSTRAINT_INDEX_8 ON PUBLIC.PERSON(NAME NULLS FIRST) VALUES ( /* 1 */ 'Name' )"; SQL statement:
        //   insert into person (name,password,id) values (?,?,?) [23505-224]] [insert into person (name,password,id) values (?,?,?)]
        //       at org.hibernate.exception.internal.SQLExceptionTypeDelegate.convert(SQLExceptionTypeDelegate.java:62)
        //       at org.hibernate.exception.internal.StandardSQLExceptionConverter.convert(StandardSQLExceptionConverter.java:58)
        //       at org.hibernate.engine.jdbc.spi.SqlExceptionHelper.convert(SqlExceptionHelper.java:108)
        //       at org.hibernate.engine.jdbc.internal.ResultSetReturnImpl.executeUpdate(ResultSetReturnImpl.java:197)
        //       at org.hibernate.engine.jdbc.mutation.internal.AbstractMutationExecutor.performNonBatchedMutation(AbstractMutationExecutor.java:107)
        //       at org.hibernate.engine.jdbc.mutation.internal.MutationExecutorSingleNonBatched.performNonBatchedOperations(MutationExecutorSingleNonBatched.java:40)
        //       at org.hibernate.engine.jdbc.mutation.internal.AbstractMutationExecutor.execute(AbstractMutationExecutor.java:52)
        //       at org.hibernate.persister.entity.mutation.InsertCoordinator.doStaticInserts(InsertCoordinator.java:175)
        //       at org.hibernate.persister.entity.mutation.InsertCoordinator.coordinateInsert(InsertCoordinator.java:113)
        //       at org.hibernate.persister.entity.AbstractEntityPersister.insert(AbstractEntityPersister.java:2873)
        //       at org.hibernate.action.internal.EntityInsertAction.execute(EntityInsertAction.java:104)
        //       at org.hibernate.engine.spi.ActionQueue.executeActions(ActionQueue.java:632)
        //       at org.hibernate.engine.spi.ActionQueue.executeActions(ActionQueue.java:499)
        //       at org.hibernate.event.internal.AbstractFlushingEventListener.performExecutions(AbstractFlushingEventListener.java:363)
        //       at org.hibernate.event.internal.DefaultAutoFlushEventListener.onAutoFlush(DefaultAutoFlushEventListener.java:61)
        //       at org.hibernate.event.service.internal.EventListenerGroupImpl.fireEventOnEachListener(EventListenerGroupImpl.java:127)
        //       at org.hibernate.internal.SessionImpl.autoFlushIfRequired(SessionImpl.java:1386)
        //       at org.hibernate.sql.exec.internal.StandardJdbcMutationExecutor.execute(StandardJdbcMutationExecutor.java:43)
        //       at org.hibernate.query.sqm.mutation.internal.SqmMutationStrategyHelper.cleanUpCollectionTable(SqmMutationStrategyHelper.java:174)
        //       at org.hibernate.query.sqm.mutation.internal.SqmMutationStrategyHelper.lambda$cleanUpCollectionTables$2(SqmMutationStrategyHelper.java:93)
        //       at org.hibernate.metamodel.mapping.internal.ImmutableAttributeMappingList.forEach(ImmutableAttributeMappingList.java:44)
        //       at org.hibernate.persister.entity.AbstractEntityPersister.forEachAttributeMapping(AbstractEntityPersister.java:4904)
        //       at org.hibernate.persister.entity.AbstractEntityPersister.visitSubTypeAttributeMappings(AbstractEntityPersister.java:6165)
        //       at org.hibernate.query.sqm.mutation.internal.SqmMutationStrategyHelper.cleanUpCollectionTables(SqmMutationStrategyHelper.java:90)
        //       at org.hibernate.query.sqm.internal.SimpleDeleteQueryPlan.executeUpdate(SimpleDeleteQueryPlan.java:125)
        //       at org.hibernate.query.sqm.internal.QuerySqmImpl.doExecuteUpdate(QuerySqmImpl.java:705)
        //       at org.hibernate.query.sqm.internal.QuerySqmImpl.executeUpdate(QuerySqmImpl.java:675)
        //       at jdk.proxy4/jdk.proxy4.$Proxy165.deleteById(Unknown Source)
        //   org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException: Нарушение уникального индекса или первичного ключа: "PUBLIC.CONSTRAINT_INDEX_8 ON PUBLIC.PERSON(NAME NULLS FIRST) VALUES ( /* 1 */ 'Name' )"
        //   Unique index or primary key violation: "PUBLIC.CONSTRAINT_INDEX_8 ON PUBLIC.PERSON(NAME NULLS FIRST) VALUES ( /* 1 */ 'Name' )"; SQL statement:
        //   insert into person (name,password,id) values (?,?,?) [23505-224]
        //       at org.h2.message.DbException.getJdbcSQLException(DbException.java:520)
        //       at org.h2.message.DbException.getJdbcSQLException(DbException.java:489)
        //       at org.h2.message.DbException.get(DbException.java:223)
        //       at org.h2.message.DbException.get(DbException.java:199)
        //       at org.h2.index.Index.getDuplicateKeyException(Index.java:527)
        //       at org.h2.mvstore.db.MVSecondaryIndex.checkUnique(MVSecondaryIndex.java:223)
        //       at org.h2.mvstore.db.MVSecondaryIndex.add(MVSecondaryIndex.java:184)
        //       at org.h2.mvstore.db.MVTable.addRow(MVTable.java:519)
        //       at org.h2.command.dml.Insert.insertRows(Insert.java:174)
        //       at org.h2.command.dml.Insert.update(Insert.java:135)
        //       at org.h2.command.dml.DataChangeStatement.update(DataChangeStatement.java:74)
        //       at org.h2.command.CommandContainer.update(CommandContainer.java:169)
        //       at org.h2.command.Command.executeUpdate(Command.java:256)
        //       at org.h2.jdbc.JdbcPreparedStatement.executeUpdateInternal(JdbcPreparedStatement.java:216)
        //       at org.h2.jdbc.JdbcPreparedStatement.executeUpdate(JdbcPreparedStatement.java:174)
        //       at org.hibernate.engine.jdbc.internal.ResultSetReturnImpl.executeUpdate(ResultSetReturnImpl.java:194)
        //       at org.hibernate.engine.jdbc.mutation.internal.AbstractMutationExecutor.performNonBatchedMutation(AbstractMutationExecutor.java:107)
        //       at org.hibernate.engine.jdbc.mutation.internal.MutationExecutorSingleNonBatched.performNonBatchedOperations(MutationExecutorSingleNonBatched.java:40)
        //       at org.hibernate.engine.jdbc.mutation.internal.AbstractMutationExecutor.execute(AbstractMutationExecutor.java:52)
        //       at org.hibernate.persister.entity.mutation.InsertCoordinator.doStaticInserts(InsertCoordinator.java:175)
        //       at org.hibernate.persister.entity.mutation.InsertCoordinator.coordinateInsert(InsertCoordinator.java:113)
        //       at org.hibernate.persister.entity.AbstractEntityPersister.insert(AbstractEntityPersister.java:2873)
        //       at org.hibernate.action.internal.EntityInsertAction.execute(EntityInsertAction.java:104)
        //       at org.hibernate.engine.spi.ActionQueue.executeActions(ActionQueue.java:632)
        //       at org.hibernate.engine.spi.ActionQueue.executeActions(ActionQueue.java:499)
        //       at org.hibernate.event.internal.AbstractFlushingEventListener.performExecutions(AbstractFlushingEventListener.java:363)
        //       at org.hibernate.event.internal.DefaultAutoFlushEventListener.onAutoFlush(DefaultAutoFlushEventListener.java:61)
        //       at org.hibernate.event.service.internal.EventListenerGroupImpl.fireEventOnEachListener(EventListenerGroupImpl.java:127)
        //       at org.hibernate.internal.SessionImpl.autoFlushIfRequired(SessionImpl.java:1386)
        //       at org.hibernate.sql.exec.internal.StandardJdbcMutationExecutor.execute(StandardJdbcMutationExecutor.java:43)
        //       at org.hibernate.query.sqm.mutation.internal.SqmMutationStrategyHelper.cleanUpCollectionTable(SqmMutationStrategyHelper.java:174)
        //       at org.hibernate.query.sqm.mutation.internal.SqmMutationStrategyHelper.lambda$cleanUpCollectionTables$2(SqmMutationStrategyHelper.java:93)
        //       at org.hibernate.metamodel.mapping.internal.ImmutableAttributeMappingList.forEach(ImmutableAttributeMappingList.java:44)
        //       at org.hibernate.persister.entity.AbstractEntityPersister.forEachAttributeMapping(AbstractEntityPersister.java:4904)
        //       at org.hibernate.persister.entity.AbstractEntityPersister.visitSubTypeAttributeMappings(AbstractEntityPersister.java:6165)
        //       at org.hibernate.query.sqm.mutation.internal.SqmMutationStrategyHelper.cleanUpCollectionTables(SqmMutationStrategyHelper.java:90)
        //       at org.hibernate.query.sqm.internal.SimpleDeleteQueryPlan.executeUpdate(SimpleDeleteQueryPlan.java:125)
        //       at org.hibernate.query.sqm.internal.QuerySqmImpl.doExecuteUpdate(QuerySqmImpl.java:705)
        //       at org.hibernate.query.sqm.internal.QuerySqmImpl.executeUpdate(QuerySqmImpl.java:675)
        //       at jdk.proxy4/jdk.proxy4.$Proxy165.deleteById(Unknown Source)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        Person person = new Person();
        person.setName("Name");
        person.setPassword("iloveyou");
        person.setRole(new ArrayList<>());

        Person person2 = new Person();
        person2.setName("42");
        person2.setPassword("Password");
        person2.setRole(new ArrayList<>());
        personRepository.save(person);
        personRepository.save(person2);

        Person person3 = new Person();
        person3.setName("Name");
        person3.setPassword("iloveyou");
        person3.setRole(new ArrayList<>());

        // Act
        personRepository.deleteById(personRepository.save(person3).getId());
    }

    /**
     * Method under test: {@link PersonRepository#updateNameById(UUID, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateNameById() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   org.springframework.dao.DataIntegrityViolationException: could not execute statement [Нарушение уникального индекса или первичного ключа: "PUBLIC.CONSTRAINT_INDEX_8 ON PUBLIC.PERSON(NAME NULLS FIRST) VALUES ( /* 30 */ 'Name' )"
        //   Unique index or primary key violation: "PUBLIC.CONSTRAINT_INDEX_8 ON PUBLIC.PERSON(NAME NULLS FIRST) VALUES ( /* 30 */ 'Name' )"; SQL statement:
        //   insert into person (name,password,id) values (?,?,?) [23505-224]] [insert into person (name,password,id) values (?,?,?)]; SQL [insert into person (name,password,id) values (?,?,?)]; constraint ["PUBLIC.CONSTRAINT_INDEX_8 ON PUBLIC.PERSON(NAME NULLS FIRST) VALUES ( /* 30 */ 'Name' )"; SQL statement:
        //   insert into person (name,password,id) values (?,?,?) [23505-224]]
        //       at jdk.proxy4/jdk.proxy4.$Proxy165.updateNameById(Unknown Source)
        //   org.hibernate.exception.ConstraintViolationException: could not execute statement [Нарушение уникального индекса или первичного ключа: "PUBLIC.CONSTRAINT_INDEX_8 ON PUBLIC.PERSON(NAME NULLS FIRST) VALUES ( /* 30 */ 'Name' )"
        //   Unique index or primary key violation: "PUBLIC.CONSTRAINT_INDEX_8 ON PUBLIC.PERSON(NAME NULLS FIRST) VALUES ( /* 30 */ 'Name' )"; SQL statement:
        //   insert into person (name,password,id) values (?,?,?) [23505-224]] [insert into person (name,password,id) values (?,?,?)]
        //       at org.hibernate.exception.internal.SQLExceptionTypeDelegate.convert(SQLExceptionTypeDelegate.java:62)
        //       at org.hibernate.exception.internal.StandardSQLExceptionConverter.convert(StandardSQLExceptionConverter.java:58)
        //       at org.hibernate.engine.jdbc.spi.SqlExceptionHelper.convert(SqlExceptionHelper.java:108)
        //       at org.hibernate.engine.jdbc.internal.ResultSetReturnImpl.executeUpdate(ResultSetReturnImpl.java:197)
        //       at org.hibernate.engine.jdbc.mutation.internal.AbstractMutationExecutor.performNonBatchedMutation(AbstractMutationExecutor.java:107)
        //       at org.hibernate.engine.jdbc.mutation.internal.MutationExecutorSingleNonBatched.performNonBatchedOperations(MutationExecutorSingleNonBatched.java:40)
        //       at org.hibernate.engine.jdbc.mutation.internal.AbstractMutationExecutor.execute(AbstractMutationExecutor.java:52)
        //       at org.hibernate.persister.entity.mutation.InsertCoordinator.doStaticInserts(InsertCoordinator.java:175)
        //       at org.hibernate.persister.entity.mutation.InsertCoordinator.coordinateInsert(InsertCoordinator.java:113)
        //       at org.hibernate.persister.entity.AbstractEntityPersister.insert(AbstractEntityPersister.java:2873)
        //       at org.hibernate.action.internal.EntityInsertAction.execute(EntityInsertAction.java:104)
        //       at org.hibernate.engine.spi.ActionQueue.executeActions(ActionQueue.java:632)
        //       at org.hibernate.engine.spi.ActionQueue.executeActions(ActionQueue.java:499)
        //       at org.hibernate.event.internal.AbstractFlushingEventListener.performExecutions(AbstractFlushingEventListener.java:363)
        //       at org.hibernate.event.internal.DefaultAutoFlushEventListener.onAutoFlush(DefaultAutoFlushEventListener.java:61)
        //       at org.hibernate.event.service.internal.EventListenerGroupImpl.fireEventOnEachListener(EventListenerGroupImpl.java:127)
        //       at org.hibernate.internal.SessionImpl.autoFlushIfRequired(SessionImpl.java:1386)
        //       at org.hibernate.sql.exec.internal.StandardJdbcMutationExecutor.execute(StandardJdbcMutationExecutor.java:43)
        //       at org.hibernate.query.sqm.internal.SimpleUpdateQueryPlan.executeUpdate(SimpleUpdateQueryPlan.java:89)
        //       at org.hibernate.query.sqm.internal.QuerySqmImpl.doExecuteUpdate(QuerySqmImpl.java:705)
        //       at org.hibernate.query.sqm.internal.QuerySqmImpl.executeUpdate(QuerySqmImpl.java:675)
        //       at jdk.proxy4/jdk.proxy4.$Proxy165.updateNameById(Unknown Source)
        //   org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException: Нарушение уникального индекса или первичного ключа: "PUBLIC.CONSTRAINT_INDEX_8 ON PUBLIC.PERSON(NAME NULLS FIRST) VALUES ( /* 30 */ 'Name' )"
        //   Unique index or primary key violation: "PUBLIC.CONSTRAINT_INDEX_8 ON PUBLIC.PERSON(NAME NULLS FIRST) VALUES ( /* 30 */ 'Name' )"; SQL statement:
        //   insert into person (name,password,id) values (?,?,?) [23505-224]
        //       at org.h2.message.DbException.getJdbcSQLException(DbException.java:520)
        //       at org.h2.message.DbException.getJdbcSQLException(DbException.java:489)
        //       at org.h2.message.DbException.get(DbException.java:223)
        //       at org.h2.message.DbException.get(DbException.java:199)
        //       at org.h2.index.Index.getDuplicateKeyException(Index.java:527)
        //       at org.h2.mvstore.db.MVSecondaryIndex.checkUnique(MVSecondaryIndex.java:223)
        //       at org.h2.mvstore.db.MVSecondaryIndex.add(MVSecondaryIndex.java:184)
        //       at org.h2.mvstore.db.MVTable.addRow(MVTable.java:519)
        //       at org.h2.command.dml.Insert.insertRows(Insert.java:174)
        //       at org.h2.command.dml.Insert.update(Insert.java:135)
        //       at org.h2.command.dml.DataChangeStatement.update(DataChangeStatement.java:74)
        //       at org.h2.command.CommandContainer.update(CommandContainer.java:169)
        //       at org.h2.command.Command.executeUpdate(Command.java:256)
        //       at org.h2.jdbc.JdbcPreparedStatement.executeUpdateInternal(JdbcPreparedStatement.java:216)
        //       at org.h2.jdbc.JdbcPreparedStatement.executeUpdate(JdbcPreparedStatement.java:174)
        //       at org.hibernate.engine.jdbc.internal.ResultSetReturnImpl.executeUpdate(ResultSetReturnImpl.java:194)
        //       at org.hibernate.engine.jdbc.mutation.internal.AbstractMutationExecutor.performNonBatchedMutation(AbstractMutationExecutor.java:107)
        //       at org.hibernate.engine.jdbc.mutation.internal.MutationExecutorSingleNonBatched.performNonBatchedOperations(MutationExecutorSingleNonBatched.java:40)
        //       at org.hibernate.engine.jdbc.mutation.internal.AbstractMutationExecutor.execute(AbstractMutationExecutor.java:52)
        //       at org.hibernate.persister.entity.mutation.InsertCoordinator.doStaticInserts(InsertCoordinator.java:175)
        //       at org.hibernate.persister.entity.mutation.InsertCoordinator.coordinateInsert(InsertCoordinator.java:113)
        //       at org.hibernate.persister.entity.AbstractEntityPersister.insert(AbstractEntityPersister.java:2873)
        //       at org.hibernate.action.internal.EntityInsertAction.execute(EntityInsertAction.java:104)
        //       at org.hibernate.engine.spi.ActionQueue.executeActions(ActionQueue.java:632)
        //       at org.hibernate.engine.spi.ActionQueue.executeActions(ActionQueue.java:499)
        //       at org.hibernate.event.internal.AbstractFlushingEventListener.performExecutions(AbstractFlushingEventListener.java:363)
        //       at org.hibernate.event.internal.DefaultAutoFlushEventListener.onAutoFlush(DefaultAutoFlushEventListener.java:61)
        //       at org.hibernate.event.service.internal.EventListenerGroupImpl.fireEventOnEachListener(EventListenerGroupImpl.java:127)
        //       at org.hibernate.internal.SessionImpl.autoFlushIfRequired(SessionImpl.java:1386)
        //       at org.hibernate.sql.exec.internal.StandardJdbcMutationExecutor.execute(StandardJdbcMutationExecutor.java:43)
        //       at org.hibernate.query.sqm.internal.SimpleUpdateQueryPlan.executeUpdate(SimpleUpdateQueryPlan.java:89)
        //       at org.hibernate.query.sqm.internal.QuerySqmImpl.doExecuteUpdate(QuerySqmImpl.java:705)
        //       at org.hibernate.query.sqm.internal.QuerySqmImpl.executeUpdate(QuerySqmImpl.java:675)
        //       at jdk.proxy4/jdk.proxy4.$Proxy165.updateNameById(Unknown Source)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        Person person = new Person();
        person.setName("Name");
        person.setPassword("iloveyou");
        person.setRole(new ArrayList<>());

        Person person2 = new Person();
        person2.setName("42");
        person2.setPassword("Password");
        person2.setRole(new ArrayList<>());
        personRepository.save(person);
        personRepository.save(person2);

        Person person3 = new Person();
        person3.setName("Name");
        person3.setPassword("iloveyou");
        person3.setRole(new ArrayList<>());

        // Act
        personRepository.updateNameById(personRepository.save(person3).getId(), "Name");
    }
}
