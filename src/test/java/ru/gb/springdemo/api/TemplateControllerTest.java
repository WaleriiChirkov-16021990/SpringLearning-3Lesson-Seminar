package ru.gb.springdemo.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import ru.gb.springdemo.model.Reader;
import ru.gb.springdemo.service.BookService;
import ru.gb.springdemo.service.IssuerService;
import ru.gb.springdemo.service.ReaderService;

import java.util.ArrayList;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {TemplateController.class})
@ExtendWith(SpringExtension.class)
class TemplateControllerTest {
    @MockBean
    private BookService bookService;

    @MockBean
    private IssuerService issuerService;

    @MockBean
    private ReaderService readerService;

    @Autowired
    private TemplateController templateController;

    /**
     * Method under test: {@link TemplateController#getBooks(Model)}
     */
    @Test
    void testGetBooks() throws Exception {
        // Arrange
        when(bookService.getBookList()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/ui/books");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(templateController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("books"))
                .andExpect(MockMvcResultMatchers.view().name("booksTemplate"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("booksTemplate"));
    }

    /**
     * Method under test: {@link TemplateController#getIssue(Model)}
     */
    @Test
    void testGetIssue() throws Exception {
        // Arrange
        when(issuerService.getIssueList()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/ui/issues");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(templateController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("issues"))
                .andExpect(MockMvcResultMatchers.view().name("issuesTemplate"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("issuesTemplate"));
    }

    /**
     * Method under test: {@link TemplateController#getIssueByReaderId(long, Model)}
     */
    @Test
    void testGetIssueByReaderId() throws Exception {
        // Arrange
        when(readerService.getReaderById(anyLong())).thenReturn(new Reader("Name"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/ui/readers/{id}", 1L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(templateController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("reader"))
                .andExpect(MockMvcResultMatchers.view().name("issueByReaderId"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("issueByReaderId"));
    }

    /**
     * Method under test: {@link TemplateController#getIssueByReaderId(long, Model)}
     */
    @Test
    void testGetIssueByReaderId2() throws Exception {
        // Arrange
        when(readerService.getReaders()).thenReturn(new ArrayList<>());
        when(readerService.getReaderById(anyLong())).thenReturn(new Reader("Name"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/ui/readers/{id}", "", "Uri Variables");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(templateController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("readers"))
                .andExpect(MockMvcResultMatchers.view().name("readerTemplate"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("readerTemplate"));
    }

    /**
     * Method under test: {@link TemplateController#getReader(Model)}
     */
    @Test
    void testGetReader() throws Exception {
        // Arrange
        when(readerService.getReaders()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/ui/readers");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(templateController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("readers"))
                .andExpect(MockMvcResultMatchers.view().name("readerTemplate"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("readerTemplate"));
    }
}
