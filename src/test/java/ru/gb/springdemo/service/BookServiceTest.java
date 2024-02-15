package ru.gb.springdemo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gb.springdemo.model.Book;
import ru.gb.springdemo.repository.BookRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

//    @Test
//    void save() {
//        Book book = new Book("testBook");
//        bookRepository.saveBook(book);
//        Mockito.when(bookRepository.getBookById(book.getId())).thenReturn(book);
//        assertEquals(book, bookRepository.getBookById(book.getId()));
//
//    }

}