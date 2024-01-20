package ru.gb.springdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.gb.springdemo.api.IssueRequest;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.model.Reader;
import ru.gb.springdemo.repository.BookRepository;
import ru.gb.springdemo.repository.IssueRepository;
import ru.gb.springdemo.repository.ReaderRepository;
import ru.gb.springdemo.util.NotIssueException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class IssuerService {

  // спринг это все заинжектит
  private final BookRepository bookRepository;
  private final ReaderRepository readerRepository;
  private final IssueRepository issueRepository;
  @Value("${application.max-allowed-books}")
  private int countBook;

  public Issue issue(IssueRequest request) {
    if (bookRepository.getBookById(request.getBookId()) == null) {
      throw new NoSuchElementException("Не найдена книга с идентификатором \"" + request.getBookId() + "\"");
    }
    if (readerRepository.getReaderById(request.getReaderId()) == null) {
      throw new NoSuchElementException("Не найден читатель с идентификатором \"" + request.getReaderId() + "\"");
    }
    // можно проверить, что у читателя нет книг на руках (или его лимит не превышает в Х книг)
    Reader readerRequest = readerRepository.getReaderById(request.getReaderId());
    if (countBook > 0) {
        if (readerRequest.getBookList().size() > countBook) {
          throw new NotIssueException("Limit of book list is reached for reader: " + request.getReaderId());
        }
    } else {
      if (!readerRequest.getBookList().isEmpty()) {
        throw new NotIssueException("Limit of book list is reached for reader: " + request.getReaderId());
      }
    }
    Issue issue = new Issue(request.getBookId(), request.getReaderId());
    readerRequest.getBookList().add(bookRepository.getBookById(request.getBookId()));
    readerRequest.getIssueList().add(issue);
    issueRepository.save(issue);
    return issue;
  }

  public Issue saveIssue(Issue issue) {
    this.issueRepository.save(issue);
    return issue;
  }

  public Issue getIssueById(long id) {
    return issueRepository.getIssueById(id);
  }

  public List<Issue> getIssueList() {
    return issueRepository.getIssues();
  }

  public Issue deleteIssue(long id) {
    return issueRepository.getIssueById(id);
  }

  public Issue put_data(Issue request) {
    request.setReturned_at(LocalDateTime.now());
    return request;
  }
}
