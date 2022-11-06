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

class GetProductsServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private GetProductsServlet servlet;

    @BeforeEach
    void init() throws SQLException {
        MockitoAnnotations.openMocks(this);
        servlet = new GetProductsServlet();
        TestingUtils.init();
    }

    @AfterEach
    void clear() throws SQLException {
        TestingUtils.clearProducts();
    }

    @Test
    void testEmptyResponse() throws IOException {
        StringWriter respWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(respWriter));

        servlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        assertEquals(respWriter.toString().replaceAll("\\s+", ""), "<html><body>" + "</body></html>");
    }

    @Test
    void testAddReturnSimpleData() throws IOException, SQLException {
        TestingUtils.addSampleData();
        StringWriter respWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(respWriter));

        servlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        assertEquals(respWriter.toString().replaceAll("\\s+", ""), "<html><body>" +
                        "min10</br>" +
                        "mid25</br>" +
                        "max50</br>" +
                        "</body></html>");
    }
}