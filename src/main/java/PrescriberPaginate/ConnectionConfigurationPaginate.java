package PrescriberPaginate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Class for constructing a JDBC connection from different systems.
 * <p>
 * This is so the poor soul who has to use linux can run the tests.
 * <p>
 * The class will create a connection from the configuration in
 * resources/databaseConnection.json if it exists. If it doesn't, creates a the
 * file databaseConnection.json in resources from the default settings below,
 * and uses those default settings to create the connection
 * <p>
 * Note, no one should change this class. If you want to change your connection
 * settings, change them in resources/databaseConnection.json
 * <p>
 * Another solution to this problem is everyone have the same sa password for
 * SQL Server, but I did not want to have to bring that up since everyone uses
 * integrated security
 */
final class ConnectionConfigurationPaginate {

    private static final Gson JSON_SERIALIZER = new Gson();

    @SerializedName("server")
    private String server;

    @SerializedName("database")
    private String database;

    @SerializedName("useIntegratedSecurity")
    private boolean useIntegratedSecurity;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    static Connection getJdbcConnection() {
        ConnectionConfigurationPaginate config = null;
        try {
            File configFile = new File("src/main/resources/databaseConnection.json");
            if (configFile.exists()) {
                String configFileAsString = readFile(configFile);
                config = JSON_SERIALIZER.fromJson(configFileAsString, ConnectionConfigurationPaginate.class);
                System.out.println(config.username);

            } else if (configFile.createNewFile()) {
                config = createConfig();
                PrintWriter writer = new PrintWriter(configFile);
                writer.write(JSON_SERIALIZER.toJson(config));
                writer.close();
            } else {
                throw new IOException();
            }
            StringBuilder jdbcStringBuilder = new StringBuilder();
            jdbcStringBuilder.append("jdbc:sqlserver://").append(config.server).append(";databaseName=")
                    .append(config.database).append(";");
            if (config.useIntegratedSecurity) {
                jdbcStringBuilder.append("integratedSecurity=true;");
            } else {
                jdbcStringBuilder.append("user=").append(config.username).append(";password=").append(config.password);
            }
            System.out.println(jdbcStringBuilder.toString());
            return DriverManager.getConnection(jdbcStringBuilder.toString());
        } catch (IOException e) {
            throw new IllegalStateException("Could not configure file");
        } catch (SQLException e) {
            throw new IllegalStateException("Could not connect to " + config.database + " via " + config.server);
        }
    }

    private static String readFile(File file) throws FileNotFoundException {

        StringBuilder fileStringBuilder = new StringBuilder();
        Scanner reader = new Scanner(file);
        while (reader.hasNextLine()) {
            fileStringBuilder.append(reader.nextLine());
        }
        reader.close();
        return fileStringBuilder.toString();
    }

    private static ConnectionConfigurationPaginate createConfig() {
        ConnectionConfigurationPaginate config = new ConnectionConfigurationPaginate();
        Scanner input = new Scanner(System.in);
        System.out.println("User integrate security? (true/false)");
        config.useIntegratedSecurity = Boolean.parseBoolean(input.next());
        if (!config.useIntegratedSecurity) {
            System.out.println("Enter username:");
            config.username = input.next();
            System.out.println("Enter password: ");
            config.password = input.next();
        }
        input.close();
        return config;
    }
}