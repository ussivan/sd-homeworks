package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.DAO;
import ru.akirakozov.sd.refactoring.Product;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author akirakozov
 */
public class AddProductServlet extends HttpServlet {

    private final DAO dao = new DAO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));

        try {
            dao.addProduct(new Product(name, price));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("OK");
    }
}