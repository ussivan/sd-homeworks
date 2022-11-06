package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

public class Utils {

    public enum QueryType {
        GET_ITEMS, COUNT_FUNCTION
    }

    public static void query(String sql, HttpServletResponse response, String responseBody, QueryType action) {
        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                response.getWriter().println("<html><body>");
                if (!responseBody.isEmpty()) {
                    response.getWriter().println(responseBody);
                }

                while (rs.next()) {
                    if (action == QueryType.GET_ITEMS) {
                        displayItemsInfo(rs, response);
                    } else if (action == QueryType.COUNT_FUNCTION) {
                        displayFunctionResult(rs, response);
                    }
                }
                response.getWriter().println("</body></html>");

                rs.close();
                stmt.close();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static void displayFunctionResult(ResultSet rs, HttpServletResponse response) throws IOException, SQLException {
        response.getWriter().println(rs.getInt(1));
    }

    static void displayItemsInfo(ResultSet rs, HttpServletResponse response) throws SQLException, IOException {
        String name = rs.getString("name");
        int price = rs.getInt("price");
        response.getWriter().println(name + "\t" + price + "</br>");
    }
}
