package org.apache.ignite.examples.datagrid;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class JdbcExampleCreateTables {

    public static void main(String[] args) throws Exception {
        System.out.println("JDBC example started.");

        // Register JDBC driver
        Class.forName("org.apache.ignite.IgniteJdbcDriver");

        // Open JDBC connection
        Connection conn = DriverManager.getConnection(
                "jdbc:ignite:thin://127.0.0.1/");

        // Create database objects
        try (Statement stmt = conn.createStatement()) {

            // Create reference City table based on REPLICATED template
            stmt.executeUpdate("CREATE TABLE city (" +
                    " id LONG PRIMARY KEY, name VARCHAR) " +
                    " WITH \"template=replicated\"");

            // Create table based on PARTITIONED template with one backup
            stmt.executeUpdate("CREATE TABLE person (" +
                    " id LONG, name VARCHAR, city_id LONG, " +
                    " PRIMARY KEY (id, city_id)) " +
                    " WITH \"backups=1, affinityKey=city_id\"");
        }

        System.out.println("Created database tables.");
    }
}
