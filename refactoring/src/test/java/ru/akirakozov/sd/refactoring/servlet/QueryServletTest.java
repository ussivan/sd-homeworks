package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.akirakozov.sd.refactoring.TestingUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class QueryServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private QueryServlet servlet;

    @BeforeEach
    void init() throws SQLException {
        MockitoAnnotations.openMocks(this);
        servlet = new QueryServlet();
        TestingUtils.init();
        TestingUtils.addSampleData();
    }

    @AfterEach
    void clear() throws SQLException {
        TestingUtils.clearProducts();
    }

    @Test
    void testMinPrice() throws IOException {
        StringWriter respWriter = new StringWriter();
        when(request.getParameter("command")).thenReturn("min");
        when(response.getWriter()).thenReturn(new PrintWriter(respWriter));

        servlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        assertEquals(removeNewLines(respWriter.toString()),
                "<html><body>" +
                        "<h1>Product with min price: </h1>" +
                        "min\t10</br>" +
                        "</body></html>");
    }

    @Test
    void testMaxPrice() throws IOException {
        StringWriter respWriter = new StringWriter();
        when(request.getParameter("command")).thenReturn("max");
        when(response.getWriter()).thenReturn(new PrintWriter(respWriter));

        servlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        assertEquals(removeNewLines(respWriter.toString()),
                "<html><body>" +
                        "<h1>Product with max price: </h1>" +
                        "max\t50</br>" +
                        "</body></html>");
    }

    @Test
    void testCountProducts() throws IOException {
        StringWriter respWriter = new StringWriter();
        when(request.getParameter("command")).thenReturn("count");
        when(response.getWriter()).thenReturn(new PrintWriter(respWriter));

        servlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        assertEquals(removeNewLines(respWriter.toString()),
                "<html><body>" +
                        "Number of products: 3" +
                        "</body></html>");
    }

    @Test
    void testSumProducts() throws IOException {
        StringWriter respWriter = new StringWriter();
        when(request.getParameter("command")).thenReturn("sum");
        when(response.getWriter()).thenReturn(new PrintWriter(respWriter));

        servlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        assertEquals(removeNewLines(respWriter.toString()),
                "<html><body>" +
                        "Summary price: 85" +
                        "</body></html>");
    }

    @Test
    void testIncorrectCommand() throws IOException {
        StringWriter respWriter = new StringWriter();
        when(request.getParameter("command")).thenReturn("padj");
        when(response.getWriter()).thenReturn(new PrintWriter(respWriter));

        servlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        assertEquals(removeNewLines(respWriter.toString()),"Unknown command: padj");
    }

    private String removeNewLines(String s) {
        return s.replaceAll("[\\r\\n]", "");
    }
}