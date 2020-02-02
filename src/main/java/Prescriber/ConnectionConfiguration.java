package Prescriber;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class for determining how to construct JDBC connection
 * 
 * This is so the poor soul who has to use linux can run the tests.
 * 
 * Any changes to this class will be ignored by git. This means everyone will be
 * able to customize this class to their own needs. We then can all use this one
 * class to construct the connection even with everyone's settings being
 * different
 * 
 * Another solution to this problem is everyone have the same sa password for
 * SQL Server
 */
final class ConnectionConfiguration {
    private static final String SERVER_NAME = "localhost";
    private static final String DATABASE_NAME = "FDB";
    private static final boolean USE_INTEGRATED_SECURITY = true;
    private static final String USER_NAME = "sa";
    private static final String PASSWORD = "linuxSucks123";

    static final Connection getJdbcConnection() {

        StringBuilder jdbcStringBuilder = new StringBuilder();
        jdbcStringBuilder.append("jdbc:sqlserver://").append(SERVER_NAME).append(";databaseName=")
                .append(DATABASE_NAME);
        try {
            if (USE_INTEGRATED_SECURITY) {
                jdbcStringBuilder.append(";integratedSecurity=true");
                return DriverManager.getConnection(jdbcStringBuilder.toString());
            } else {
                return DriverManager.getConnection(jdbcStringBuilder.toString(), USER_NAME, PASSWORD);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Could not connect to " + DATABASE_NAME + " via " + SERVER_NAME);
        }
    }

}