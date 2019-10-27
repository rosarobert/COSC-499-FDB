import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LocalDatabaseConnection {
    public static void main(String[] args) {
        //Add hostname and portnumber to url
        String url = "jdbc:sqlserver://MYSURFACE; databaseName=FDB;integratedSecurity=true";
        try {
            Connection connection = DriverManager.getConnection(url);
        } catch (SQLException  e) {
            e.printStackTrace();
        }
    }
}
