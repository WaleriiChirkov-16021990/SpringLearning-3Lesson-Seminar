package ru.gb.springdemo.api;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
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
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.service.IssuerService;
import ru.gb.springdemo.util.NotIssueException;

@ContextConfiguration(classes = {IssuerController.class})
@ExtendWith(SpringExtension.class)
class IssuerControllerDiffblueTest {
    @Autowired
    private IssuerController issuerController;

    @MockBean
    private IssuerService issuerService;

    /**
     * Method under test: {@link IssuerController#getIssues()}
     */
    @Test
    void testGetIssues() throws Exception {
        // Arrange
        when(issuerService.getIssueList()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/issues");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(issuerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link IssuerController#getIssues()}
     */
    @Test
    void testGetIssues2() throws Exception {
        // Arrange
        when(issuerService.getIssueList()).thenThrow(new NotIssueException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/issues");

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(issuerController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(409))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("An error occurred"));
    }

    /**
     * Method under test: {@link IssuerController#issue(long)}
     */
    @Test
    void testIssue() throws Exception {
        // Arrange
        when(issuerService.getIssueById(anyLong())).thenReturn(null);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/issues/{id}", 1L);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(issuerController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link IssuerController#issue(long)}
     */
    @Test
    void testIssue2() throws Exception {
        // Arrange
        when(issuerService.getIssueById(anyLong())).thenThrow(new NotIssueException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/issues/{id}", 1L);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(issuerController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(409))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("An error occurred"));
    }

    /**
     * Method under test: {@link IssuerController#issueBook(long)}
     */
    @Test
    void testIssueBook() throws Exception {
        // Arrange
        when(issuerService.deleteIssue(anyLong())).thenThrow(new NotIssueException("An error occurred"));
        when(issuerService.put_data(Mockito.<Issue>any())).thenThrow(new NotIssueException("An error occurred"));
        when(issuerService.saveIssue(Mockito.<Issue>any())).thenThrow(new NotIssueException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/issues/{id}", 1L);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(issuerController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link IssuerController#issueBook(IssueRequest)}
     */
    @Test
    void testIssueBook2() throws Exception {
        // Arrange
        when(issuerService.getIssueList()).thenReturn(new ArrayList<>());

        IssueRequest issueRequest = new IssueRequest();
        issueRequest.setBookId(1L);
        issueRequest.setReaderId(1L);
        String content = (new ObjectMapper()).writeValueAsString(issueRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/issues")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(issuerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link IssuerController#issueBook(IssueRequest)}
     */
    @Test
    void testIssueBook3() throws Exception {
        // Arrange
        when(issuerService.getIssueList()).thenThrow(new NotIssueException("An error occurred"));

        IssueRequest issueRequest = new IssueRequest();
        issueRequest.setBookId(1L);
        issueRequest.setReaderId(1L);
        String content = (new ObjectMapper()).writeValueAsString(issueRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/issues")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(issuerController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(409))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("An error occurred"));
    }
}
