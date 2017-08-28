
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class JdbcExampleCreateIndexes {

    public static void main(String[] args) throws Exception {
        System.out.println("JDBC example started.");

        // Register JDBC driver
        Class.forName("org.apache.ignite.IgniteJdbcDriver");

        // Open JDBC connection
        Connection conn = DriverManager.getConnection(
                "jdbc:ignite:thin://127.0.0.1/");

        // Create indexes
        try (Statement stmt = conn.createStatement()) {

            // Create an index on the city table
            stmt.executeUpdate("CREATE INDEX idx_city_name ON city (name)");

            // Create an index on the person table
            stmt.executeUpdate("CREATE INDEX idx_person_name ON person (name)");
        }

        System.out.println("Created database indexes.");
    }
}
