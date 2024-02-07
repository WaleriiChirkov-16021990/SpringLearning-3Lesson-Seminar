package ru.gb.springdemo.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.service.IssuerService;
import ru.gb.springdemo.util.NotIssueException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/issues")
public class IssuerController {

    @Autowired
    private IssuerService service;

//  @PutMapping
//  public void returnBook(long issueId) {
//    // найти в репозитории выдачу и проставить ей returned_at
//  }

    @GetMapping
//    @PreAuthorize("hasAuthority('ISSUE')")
    public List<Issue> getIssues() {
        return service.getIssueList();
    }

    @PostMapping
    public ResponseEntity<Issue> issueBook(@RequestBody IssueRequest request) {
        log.info("Получен запрос на выдачу: readerId = {}, bookId = {}", request.getReaderId(), request.getBookId());
        final Issue issue;
        try {
            issue = service.issue(request);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(issue);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Issue> issueBook(@PathVariable("id") long id) {
        final Issue issue;
        try {
            issue = service.deleteIssue(id);
            Issue newIssue = service.put_data(issue);
            service.saveIssue(newIssue);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(issue,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Issue> issue(@PathVariable("id") long id) {
        Issue findIssue = service.getIssueById(id);
        if (findIssue != null) {
            return new ResponseEntity<>(findIssue, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler
    private ResponseEntity<String> error(NotIssueException request) {
        String response = request.getMessage();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

}
