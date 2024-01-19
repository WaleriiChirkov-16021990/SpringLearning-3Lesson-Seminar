package ru.gb.springdemo.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.model.Reader;
import ru.gb.springdemo.service.ReaderService;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/readers")
public class ReaderController {

  @Autowired
  private ReaderService service;

//  @PutMapping
//  public void returnBook(long issueId) {
//    // найти в репозитории выдачу и проставить ей returned_at
//  }

  @PostMapping
  public ResponseEntity<Reader> issueBook(@RequestBody Reader request) {
    final Reader reader;
    try {
      reader = service.save(request);
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(reader);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Reader> getBook(@PathVariable("id")  long id) {
    final Reader reader;
    try {
      reader = service.getReaderById(id);
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
    return new ResponseEntity<>(reader,HttpStatus.OK);
  }


  @GetMapping("/{id}/issue")
  public ResponseEntity<List<Issue>> getIssue(@PathVariable("id") long id) {
    final Reader reader;
    try {
      reader = service.getReaderById(id);
    } catch (Exception e) {
      return new  ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(reader.getIssueList(),HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Reader> deleteReaderById(@PathVariable long id) {
    return new ResponseEntity<>(service.deleteReader(id), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<Reader>> getReaderList(){
    return new ResponseEntity<>(service.getReaders(), HttpStatus.OK);
  }

}
