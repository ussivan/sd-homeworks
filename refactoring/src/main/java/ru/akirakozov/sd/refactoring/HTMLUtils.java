package ru.akirakozov.sd.refactoring;


import java.sql.*;
import java.util.List;

public class HTMLUtils {

    public static String productToHTML(Product p) {
        if (p == null) {
            return "";
        }
        return p.getName() + "\t" + p.getPrice() + "</br>";
    }

    public static String productsToHTML(List<Product> products) {
        StringBuilder ans = new StringBuilder();
        for (Product p : products) {
            ans.append(productToHTML(p));
        }
        return ans.toString();
    }

    public static String valueToHTML(Integer val) {
        if (val == null) {
            return "";
        }
        return val.toString();
    }

    public static String wrapHTML(String html) {
        return "<html><body>\n" + html + "\n</body></html>";
    }

    public static String query(String responseBody, String content) throws SQLException {
        StringBuilder sb = new StringBuilder();
        if (!responseBody.isEmpty()) {
            sb.append(responseBody);
        }
        sb.append(content);
        return wrapHTML(sb.toString());
    }
}

