package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Utils.query("SELECT * FROM PRODUCT", response, "", Utils.QueryType.GET_ITEMS);

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}