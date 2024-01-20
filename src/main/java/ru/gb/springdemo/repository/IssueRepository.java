package ru.gb.springdemo.repository;

import lombok.Getter;
import org.springframework.stereotype.Repository;
import ru.gb.springdemo.model.Issue;

import java.util.ArrayList;
import java.util.List;

@Getter
@Repository
public class IssueRepository {

  private final List<Issue> issues;

  public IssueRepository() {
    this.issues = new ArrayList<>();
//    issues.add(new Issue(1L,1L));
//    issues.add(new Issue(2L,1L));
//    issues.add(new Issue(3L,2L));
  }

  public void save(Issue issue) {
    // insert into ....
    issues.add(issue);
  }

  public Issue getIssueById(long id) {
    return issues.stream().filter(issue -> issue.getId() == id).findFirst().orElse(null);
  }

}
