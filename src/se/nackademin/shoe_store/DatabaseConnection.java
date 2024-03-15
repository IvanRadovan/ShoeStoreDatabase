package se.nackademin.shoe_store;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DatabaseConnection implements Connectable {

    private final Properties properties;

    private static final String PROPERTIES_PATH = "resources\\database.properties";
    private static final String URL = "url";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    public DatabaseConnection() {
        this.properties = new Properties();
        try {
            properties.load(new FileInputStream(PROPERTIES_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResultSet getTable(String query) {
        ResultSet resultSet;
        try {
            Connection connection = DriverManager.getConnection(
                    properties.getProperty(URL),
                    properties.getProperty(USERNAME),
                    properties.getProperty(PASSWORD));

            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    @Override
    public CallableStatement getStoredProcedure() {
        try {
            Connection connection = DriverManager.getConnection(
                    properties.getProperty(URL),
                    properties.getProperty(USERNAME),
                    properties.getProperty(PASSWORD));
            return connection.prepareCall("CALL AddToCart(?, ?, ?, ?)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
