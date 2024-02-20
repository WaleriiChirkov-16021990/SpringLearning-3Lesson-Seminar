package ru.gb.springdemo.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.gb.springdemo.model.Book;
import ru.gb.springdemo.service.BookService;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {BookController.class})
@ExtendWith(SpringExtension.class)
class BookControllerTest {
    @Autowired
    private BookController bookController;

    @MockBean
    private BookService bookService;

    /**
     * Method under test: {@link BookController#deleteReaderById(long)}
     */
    @Test
    @Disabled
    void testDeleteReaderById() throws Exception {
        // Arrange
        Book book = new Book("Name");
        when(bookService.deleteBookById(anyLong())).thenReturn(book);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/books/{id}", 6L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(bookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"id\":6,\"name\":\"Name\"}"));
    }

    /**
     * Method under test: {@link BookController#getBook(long)}
     */
    @Test
    void testGetBook() throws Exception {
        // Arrange
        when(bookService.getBookById(anyLong())).thenThrow(new NoSuchElementException("foo"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books/{id}", 1L);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(bookController).build().perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link BookController#getBook(long)}
     */
    @Test
    void testGetBook2() throws Exception {
        // Arrange
        when(bookService.getBookList()).thenReturn(new ArrayList<>());
        when(bookService.getBookById(anyLong())).thenReturn(new Book("Name"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books/{id}", "", "Uri Variables");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(bookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link BookController#getReaderList()}
     */
    @Test
    void testGetReaderList() throws Exception {
        // Arrange
        when(bookService.getBookList()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(bookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link BookController#issueBook(Book)}
     */
    @Test
    void testIssueBook() throws Exception {
        // Arrange
        when(bookService.getBookList()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.get("/books")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new Book("Name")));

        // Act and Assert
        MockMvcBuilders.standaloneSetup(bookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}
