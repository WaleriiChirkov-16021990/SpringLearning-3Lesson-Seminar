package ru.gb.springdemo.repository;

//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.springdemo.model.Person;
import ru.gb.springdemo.model.Role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.Future;
@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {
//public interface PersonRepository extends JpaRepository<Person, UUID>, JpaSpecificationExecutor<Person> {

    Optional<List<Person>> findByRole(Role role);

//    @Async
//    Future<List<Person>> findByRole(List<Role> role);

    Optional<Person> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

    Optional<Person> findByName(String name);

    @Transactional
    @Modifying
    @Query("delete from Person p where p.id = ?1")
    void deleteById(UUID id);

    @Transactional
    @Modifying
    @Query("update Person p set p.name = ?2 where p.id = ?1")
    void updateNameById(UUID id, String name);

}