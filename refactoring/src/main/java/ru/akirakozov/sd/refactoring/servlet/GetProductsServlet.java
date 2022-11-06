package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.DAO;
import ru.akirakozov.sd.refactoring.HTMLUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {
    private final DAO dao = new DAO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            response.getWriter().println(HTMLUtils.query("", HTMLUtils.productsToHTML(dao.getProducts())));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}