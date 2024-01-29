package ru.gb.springdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.springdemo.model.Issue;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
}
