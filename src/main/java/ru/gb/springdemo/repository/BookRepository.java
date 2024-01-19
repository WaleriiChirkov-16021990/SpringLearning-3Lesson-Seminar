package ru.gb.springdemo.repository;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Repository;
import ru.gb.springdemo.model.Book;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Getter
@Repository
public class BookRepository {

    private final List<Book> books;

    public BookRepository() {
        this.books = new ArrayList<>();
    }

    @PostConstruct
    public void generateData() {
        books.addAll(List.of(
                new Book("война и мир"),
                new Book("метрвые души"),
                new Book("чистый код")
        ));
    }

    public Book getBookById(long id) {
        return books.stream().filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElse(null);
    }

    public Book getStartWithName(String name) {
        return books.stream()
                .filter(it -> it.getName()
                        .startsWith(name))
                .findFirst()
                .orElse(null);
    }

    public void saveBook(Book book) {
        books.add(book);
    }

    public Book deleteBookById(long id) {
        Book removedBook = null;
        for (Iterator<Book> iterator = books.iterator(); iterator.hasNext(); ) {
            Book book = iterator.next();
            if (book.getId() == id) {
                removedBook = book;
                iterator.remove();
                break;
            }
        }
        return removedBook;
    }

    public List<Book> getBookList() {
        return books;
    }
}
