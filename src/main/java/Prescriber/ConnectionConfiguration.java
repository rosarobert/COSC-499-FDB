package Prescriber;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Class for constructing a JDBC connection from different systems.
 * 
 * This is so the poor soul who has to use linux can run the tests.
 * 
 * The class will create a connection from the configuration in
 * resources/databaseConnection.json if it exists. If it doesn't, creates a the
 * file databaseConnection.json in resources from the default settings below,
 * and uses those default settings to create the connection
 * 
 * Note, no one should change this class. If you want to change your connection
 * settings, change them in resources/databaseConnection.json
 * 
 * Another solution to this problem is everyone have the same sa password for
 * SQL Server, but I did not want to have to bring that up since everyone uses
 * integrated security
 */
final class ConnectionConfiguration {

    private static final Gson JSON_SERIALIZER = new Gson();

    @SerializedName("server")
    private final String SERVER = "localhost";

    @SerializedName("database")
    private final String DATABASE = "FDB";

    @SerializedName("useIntegratedSecurity")
    private final boolean USE_INTEGRATED_SECURITY = true;

    @SerializedName("username")
    private final String USERNAME = "sa";

    @SerializedName("password")
    private final String PASSWORD = "linuxSucks123";

    static final Connection getJdbcConnection() {
        ConnectionConfiguration config = new ConnectionConfiguration();
        try {
            File configFile = new File("resources/databaseConnection.json");
            if (configFile.exists()) {
                String configFileAsString = Files.readString(configFile.toPath());
                config = JSON_SERIALIZER.fromJson(configFileAsString, ConnectionConfiguration.class);
            } else if (configFile.createNewFile()) {
                PrintWriter writer = new PrintWriter(configFile);
                writer.write(JSON_SERIALIZER.toJson(config));
                writer.close();
            } else {
                throw new IOException();
            }
            StringBuilder jdbcStringBuilder = new StringBuilder();
            jdbcStringBuilder.append("jdbc:sqlserver://")
                             .append(config.SERVER)
                             .append(";databaseName=")
                             .append(config.DATABASE);
            if (config.USE_INTEGRATED_SECURITY) {
                jdbcStringBuilder.append(";integratedSecurity=true");
                return DriverManager.getConnection(jdbcStringBuilder.toString());
            } else {
                return DriverManager.getConnection(jdbcStringBuilder.toString(), config.USERNAME, config.PASSWORD);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Could not configure file");
        } catch (SQLException e) {
            throw new IllegalStateException("Could not connect to " + config.DATABASE + " via " + config.SERVER);
        }
    }
}