package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.DAO;
import ru.akirakozov.sd.refactoring.Product;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Utils {

    public enum QueryType {
        MIN_PROD, MAX_PROD, GET_ITEMS, SUM, COUNT
    }


    public static void query(HttpServletResponse response, String responseBody, QueryType action) {
        DAO dao = new DAO();
        try {
            response.getWriter().println("<html><body>");
            if (!responseBody.isEmpty()) {
                response.getWriter().println(responseBody);
            }

            if (action == QueryType.GET_ITEMS) {
                for (Product p: dao.getProducts()) {
                    response.getWriter().println(p.getName() + "\t" + p.getPrice() + "</br>");
                }
            } else if (action == QueryType.MIN_PROD) {
                Optional<Product> optional = dao.getMinProduct();
                if (optional.isPresent()) {
                    Product p = optional.get();
                    response.getWriter().println(p.getName() + "\t" + p.getPrice() + "</br>");
                }
            } else if (action == QueryType.MAX_PROD) {
                Optional<Product> optional = dao.getMaxProduct();
                if (optional.isPresent()) {
                    Product p = optional.get();
                    response.getWriter().println(p.getName() + "\t" + p.getPrice() + "</br>");
                }
            } else if (action == QueryType.COUNT) {
                Optional<Integer> result = dao.getProductCount();
                if (result.isPresent()) {
                    response.getWriter().println(result.get());
                }
            } else {
                Optional<Integer> result = dao.getProductPriceSum();
                if (result.isPresent()) {
                    response.getWriter().println(result.get());
                }
            }
            response.getWriter().println("</body></html>");
        } catch (SQLException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }


    static int displayFunctionResult(ResultSet rs, HttpServletResponse response) throws IOException, SQLException {
        return rs.getInt(1);
    }

    static Product displayItemsInfo(ResultSet rs, HttpServletResponse response) throws SQLException, IOException {
        String name = rs.getString("name");
        int price = rs.getInt("price");
        return new Product(name, price);
    }
}
