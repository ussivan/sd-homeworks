package ru.akirakozov.sd.refactoring;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

public class TestingUtils {
    private static final String DB_URL = "jdbc:sqlite:test.db";

    private static final String INIT_SQL = "CREATE TABLE IF NOT EXISTS PRODUCT" +
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            " NAME           TEXT    NOT NULL, " +
            " PRICE          INT     NOT NULL)";

    private static final String CLEAR_SQL = "DELETE FROM PRODUCT";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
    public static void init() throws SQLException {
        exec(INIT_SQL);
    }

    public static void clearProducts() throws SQLException {
        exec(CLEAR_SQL);
    }

    public static void addProduct(String name, int value) throws SQLException {
        exec("INSERT INTO PRODUCT " +
                "(NAME, PRICE) VALUES (\"" + name + "\"," + value + ")");
    }

    private static void exec(String sql) throws SQLException {
        try (Connection c = connect()) {
            try (Statement s = c.createStatement()) {
                s.executeUpdate(sql);
            }
        }
    }

    public static final Map<String, Integer> SAMPLE_DATA = new LinkedHashMap<>();

    static {
        SAMPLE_DATA.put("min", 10);
        SAMPLE_DATA.put("mid", 25);
        SAMPLE_DATA.put("max", 50);
    }

    public static void addSampleData() throws SQLException {
        for (Map.Entry<String, Integer> entry : SAMPLE_DATA.entrySet()) {
            addProduct(entry.getKey(), entry.getValue());
        }
    }

}