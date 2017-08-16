package org.apache.ignite.examples.datagrid;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class JdbcExampleDelete {

    public static void main(String[] args) throws Exception {
        System.out.println("JDBC example started.");

        // Register JDBC driver
        Class.forName("org.apache.ignite.IgniteJdbcDriver");

        // Open JDBC connection
        Connection conn = DriverManager.getConnection(
                "jdbc:ignite:thin://127.0.0.1/");

        // Delete
        try (Statement stmt = conn.createStatement()) {

            // Delete person
            stmt.executeUpdate("DELETE FROM person WHERE name = 'John Doe'");
        }

        System.out.println("Deleted data.");
    }
}
