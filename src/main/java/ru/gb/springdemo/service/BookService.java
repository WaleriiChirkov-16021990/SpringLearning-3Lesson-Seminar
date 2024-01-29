package ru.gb.springdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.springdemo.api.IssueRequest;
import ru.gb.springdemo.model.Book;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.repository.BookRepository;
import ru.gb.springdemo.repository.IssueRepository;
import ru.gb.springdemo.repository.ReaderRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BookService {

  // спринг это все заинжектит
  private final BookRepository bookRepository;

  public Book save(Book book) {
    bookRepository.saveAndFlush(book);
    return book;
  }

  public Book getBookById(Long id) {
    return bookRepository.findById(id).orElseThrow(RuntimeException::new);
  }

  public Book deleteBookById(Long id) {
    Book book = getBookById(id);
     bookRepository.deleteById(id);
     return book;
  }

  public List<Book> getBookList() {
    try {
      return bookRepository.findAll();
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

}
