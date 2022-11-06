package ru.akirakozov.sd.refactoring;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DAO {

    public List<Product> getProducts() throws SQLException {
        return executeQuery("SELECT * FROM PRODUCT", this::resultSetToProduct);
    }

    public Optional<Product> getMinProduct() throws SQLException {
        List<Product> products = executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1", this::resultSetToProduct);
        return lastOrEmpty(products);
    }

    public Optional<Product> getMaxProduct() throws SQLException {
        List<Product> products = executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1", this::resultSetToProduct);
        return lastOrEmpty(products);
    }

    public Optional<Integer> getProductPriceSum() throws SQLException {
        List<Integer> sums = executeQuery("SELECT SUM(price) FROM PRODUCT", rs -> rs.getInt(1));
        return lastOrEmpty(sums);
    }

    public Optional<Integer> getProductCount() throws SQLException {
        List<Integer> sums = executeQuery("SELECT COUNT(*) FROM PRODUCT", rs -> rs.getInt(1));
        return lastOrEmpty(sums);
    }

    private <T> List<T> executeQuery(String sql, SQLFunction<ResultSet, T> rsMapper) throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            try (Statement stmt = c.createStatement()) {
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    List<T> res = new ArrayList<>();
                    while (rs.next()) {
                        res.add(rsMapper.apply(rs));
                    }
                    return res;
                }
            }
        }
    }

    private Product resultSetToProduct(ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        int price = rs.getInt("price");
        return new Product(name, price);
    }

    private <T> Optional<T> lastOrEmpty(List<T> products) {
        return products.isEmpty() ? Optional.empty() : Optional.of(products.get(products.size() - 1));
    }

    private interface SQLFunction<T, R> {
        R apply (T arg) throws SQLException;
    }
}
