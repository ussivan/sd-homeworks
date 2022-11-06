package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;
import ru.akirakozov.sd.refactoring.TestingUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;


class AddProductServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private AddProductServlet servlet;

    @BeforeEach
    void init() throws SQLException {
        MockitoAnnotations.openMocks(this);
        servlet = new AddProductServlet();
        TestingUtils.init();
        initMock();
    }

    void initMock() {
        when(request.getParameter("name")).thenReturn("test");
        when(request.getParameter("price")).thenReturn("20");
    }

    @AfterEach
    void clear() throws SQLException {
        TestingUtils.clearProducts();
    }

    @Test
    void testSimpleOkResponse() throws IOException {
        StringWriter out = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(out));

        servlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        assertEquals(out.toString().trim(), "OK");
    }

    @Test
    void testEntityIsAdded() throws IOException, SQLException {
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        servlet.doGet(request, response);

        try (Connection c = TestingUtils.connect()) {
            try (Statement s = c.createStatement()) {
                ResultSet rs = s.executeQuery("SELECT * FROM PRODUCT");
                boolean rsRes = rs.next();
                assertTrue(rsRes);
                String name = rs.getString("name");
                int price = rs.getInt("price");
                assertEquals(name, "test");
                assertEquals(price, 20);
            }
        }
    }
}