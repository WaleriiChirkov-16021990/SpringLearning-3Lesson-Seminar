package ru.gb.springdemo.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.gb.springdemo.service.BookService;

import ru.gb.springdemo.model.Book;

import java.util.List;
import java.util.NoSuchElementException;



@Slf4j
@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    @Autowired
    private BookService service;

    @PostMapping
    public ResponseEntity<Book> issueBook(@RequestBody Book request) {
        final Book reader;
        try {
            reader = service.save(request);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(reader);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable("id") long id) {
        final Book book;
        try {
            book = service.getBookById(id);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteReaderById(@PathVariable long id) {
        return new ResponseEntity<>(service.deleteBookById(id), HttpStatus.OK);
    }
//    @PreAuthorize("hasAuthority('BOOK')")
    @GetMapping
    public ResponseEntity<List<Book>> getReaderList() {
        return new ResponseEntity<>(service.getBookList(), HttpStatus.OK);
    }
}
