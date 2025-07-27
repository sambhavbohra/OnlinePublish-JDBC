import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/articlepublish";
        String username = "Your_dbUsername";
        String password = "your_dbPassword";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected successfully.");
            Statement stmt = connection.createStatement();
            SchemaCreator.createAllTables(connection);
        } catch (SQLException e) {
            System.err.println("Couldn't connect to the database:");
            e.printStackTrace();
        }
    }
}
