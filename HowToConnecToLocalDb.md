## Connecting to Local FDB Database Instance

Note that these instructions are specific to Windows, but should not be far off from Mac/Docker

1) Download the SQL Server Driver from [here](https://docs.microsoft.com/en-us/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server?view=sql-server-ver15)
2) Determine whether you have a 32-bit version of Java or a 64-bit version of java
   - On windows, if you have a Java folder under C:\\Program Files(x86), then it is a 32-bit version. If its in C:\\Program Files, it is probably 64-bit, but its the opposite of the other Program Files folder. If its only C:\\Program Files, its 32-bit
3) If you installiation is a 64-bit version, go to your downloaded SQL Server driver and then go enu > auth > x86 and copy that file. If its a 32-bit version, do the same thing except copy the file in the x64 folder.
4) Find your Java folder again, and look for the \jdk, and copy this file into the \bin and \lib folders. If you have multiple jdks, make sure you check which version your IDE is using.
5) Use the following code by removing the optional `[:portNumber]`. If you are connecting to this using Docker, you might need to include the port number in the url. There is more infomation [here](https://docs.microsoft.com/en-us/sql/connect/jdbc/building-the-connection-url?view=sql-server-ver15)
```
public class ConnectTest {
    public static void main(String[] args) {
        String url = "jdbc:sqlserver://localhost[:portNumber]; databaseName=FDB;integratedSecurity=true";
        try {
            Connection connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

```
