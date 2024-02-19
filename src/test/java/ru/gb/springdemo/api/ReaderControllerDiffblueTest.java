package ru.gb.springdemo.api;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import ru.gb.springdemo.model.Reader;
import ru.gb.springdemo.service.ReaderService;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {ReaderController.class})
@ExtendWith(SpringExtension.class)
class ReaderControllerDiffblueTest {
    @Autowired
    private ReaderController readerController;

    @MockBean
    private ReaderService readerService;

    /**
     * Method under test: {@link ReaderController#deleteReaderById(long)}
     */
    @Test
    void testDeleteReaderById() throws Exception {
        Reader reader = new Reader("Name");
        when(readerService.deleteReader(anyLong())).thenReturn(reader);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/readers/{id}", 1L);
        MockMvcBuilders.standaloneSetup(readerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"id\":1,\"name\":\"Name\",\"bookList\":[],\"issueList\":[]}"));
    }

    /**
     * Method under test: {@link ReaderController#getBook(long)}
     */
    @Test
    void testGetBook() throws Exception {
        when(readerService.getReaderById(anyLong())).thenThrow(new NoSuchElementException("foo"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/readers/{id}", 1L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(readerController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link ReaderController#getBook(long)}
     */
    @Test
    void testGetBook2() throws Exception {
        when(readerService.getReaders()).thenReturn(new ArrayList<>());
        when(readerService.getReaderById(anyLong())).thenReturn(new Reader("Name"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/readers/{id}", "", "Uri Variables");
        MockMvcBuilders.standaloneSetup(readerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link ReaderController#getIssue(long)}
     */
    @Test
    void testGetIssue() throws Exception {
        when(readerService.getReaderById(anyLong())).thenReturn(new Reader("Name"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/readers/{id}/issue", 1L);
        MockMvcBuilders.standaloneSetup(readerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link ReaderController#getIssue(long)}
     */
    @Test
    void testGetIssue2() throws Exception {
        when(readerService.getReaderById(anyLong())).thenThrow(new NoSuchElementException("foo"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/readers/{id}/issue", 1L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(readerController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link ReaderController#getReaderList()}
     */
    @Test
    void testGetReaderList() throws Exception {
        when(readerService.getReaders()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/readers");
        MockMvcBuilders.standaloneSetup(readerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link ReaderController#issueBook(Reader)}
     */
    @Test
    void testIssueBook() throws Exception {
        when(readerService.getReaders()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.get("/readers")
                .contentType(MediaType.APPLICATION_JSON);
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content((new ObjectMapper()).writeValueAsString(null));
        MockMvcBuilders.standaloneSetup(readerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}
