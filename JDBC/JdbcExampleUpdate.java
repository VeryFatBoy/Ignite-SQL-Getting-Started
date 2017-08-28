import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class JdbcExampleUpdate {

    public static void main(String[] args) throws Exception {
        System.out.println("JDBC example started.");

        // Register JDBC driver
        Class.forName("org.apache.ignite.IgniteJdbcDriver");

        // Open JDBC connection
        Connection conn = DriverManager.getConnection(
                "jdbc:ignite:thin://127.0.0.1/");

        // Update
        try (Statement stmt = conn.createStatement()) {

            // Update city
            stmt.executeUpdate("UPDATE city SET name = 'Foster City' WHERE id = 2");
        }

        System.out.println("Updated data.");
    }
}
