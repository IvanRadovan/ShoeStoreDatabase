package se.nackademin.shoe_store;

import java.sql.CallableStatement;
import java.sql.ResultSet;

public interface Connectable {

    ResultSet getTable(String table);

    CallableStatement getStoredProcedure();
}
