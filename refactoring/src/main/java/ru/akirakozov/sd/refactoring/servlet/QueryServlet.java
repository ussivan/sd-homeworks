package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            maxCommand(response);
        } else if ("min".equals(command)) {
            minCommand(response);
        } else if ("sum".equals(command)) {
            sumCommand(response);
        } else if ("count".equals(command)) {
            countCommand(response);
        } else {
            unknownCommand(response, command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }


    private void maxCommand(HttpServletResponse response) {
        Utils.query("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1", response,
                "<h1>Product with max price: </h1>", Utils.QueryType.GET_ITEMS);
    }

    private void minCommand(HttpServletResponse response) {
        Utils.query("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1", response,
                "<h1>Product with min price: </h1>", Utils.QueryType.GET_ITEMS);
    }

    private void countCommand(HttpServletResponse response) {
        Utils.query("SELECT COUNT(*) FROM PRODUCT", response,
                "Number of products: ", Utils.QueryType.COUNT_FUNCTION);
    }

    private void sumCommand(HttpServletResponse response) {
        Utils.query("SELECT SUM(price) FROM PRODUCT", response,
                "Summary price: ", Utils.QueryType.COUNT_FUNCTION);
    }

    private void unknownCommand(HttpServletResponse response, String command) throws IOException {
        response.getWriter().println("Unknown command: " + command);
    }


}