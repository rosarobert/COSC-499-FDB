import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LocalDatabaseConnection {
    public static void main(String[] args) {
        String url = "jdbc:sqlserver://HOSTNAME[:portNumber]; databaseName=FDB;integratedSecurity=true";
        try {
            Connection connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
