package se.nackademin.shoe_store;

import se.nackademin.shoe_store.tables.Table;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface Provider<T extends Table> {
    T fetch(ResultSet resultSet) throws SQLException;
}
