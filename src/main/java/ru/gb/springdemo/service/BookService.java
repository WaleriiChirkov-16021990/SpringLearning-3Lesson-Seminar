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
//  private final ReaderRepository readerRepository;
//  private final IssueRepository issueRepository;

  public Book save(Book book) {
    bookRepository.saveBook(book);
    return book;

  }

  public Book getBookById(long id) {
    return bookRepository.getBookById(id);
  }

  public Book deleteBookById(long id) {
    return bookRepository.deleteBookById(id);
  }

  public List<Book> getBookList() {
    return bookRepository.getBookList();
  }

}
