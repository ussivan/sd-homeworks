package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.DAO;
import ru.akirakozov.sd.refactoring.Product;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    private final DAO dao = new DAO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");
        try {
            String res;
            if ("max".equals(command)) {
                res = maxCommand();
            } else if ("min".equals(command)) {
                res = minCommand();
            } else if ("sum".equals(command)) {
                res = sumCommand();
            } else if ("count".equals(command)) {
                res = countCommand();
            } else {
                res = unknownCommand(command);
            }
            response.getWriter().println(res);
        } catch (SQLException e) {
            throw new RuntimeException();
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }


    private String maxCommand() throws SQLException {
        Optional<Product> optional = dao.getMaxProduct();
        return HTMLUtils.query("<h1>Product with max price: </h1>", HTMLUtils.productToHTML(optional.orElse(null)));
    }

    private String minCommand() throws SQLException {
        Optional<Product> optional = dao.getMinProduct();
        return HTMLUtils.query("<h1>Product with min price: </h1>", HTMLUtils.productToHTML(optional.orElse(null)));
    }

    private String countCommand() throws SQLException {
        return HTMLUtils.query("Number of products: ", HTMLUtils.valueToHTML(dao.getProductCount().orElse(null)));
    }

    private String sumCommand() throws SQLException {
        return HTMLUtils.query("Summary price: ", HTMLUtils.valueToHTML(dao.getProductPriceSum().orElse(null)));
    }

    private String unknownCommand(String command) {
        return "Unknown command: " + command;
    }


}