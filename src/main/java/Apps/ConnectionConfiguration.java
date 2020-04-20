package Apps;

import org.apache.commons.lang3.Validate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Class for constructing a JDBC connection from different systems.
 * <p>
 * The class will create a connection from the configuration in src/main/resources if it exists. If it doesnt, it
 * creates the file from user input via stdin
 */
public final class ConnectionConfiguration {
    private static final File CONFIG_FILE = new File("src/main/resources/databaseConnectionConfig.json");

    /**
     * For testing if the connection works
     */
    public static void main(String[] args) {
        Connection connection = getJdbcConnection();
    }

    /**
     * Creates a JDBC connection from the file in src/main/resources. If this files does not exist, we create it via
     * stdin
     *
     * @return a JDBC connection to a database
     */
    public static final Connection getJdbcConnection() {
        Validate.isTrue(connectionConfigFileHasBeenCreated(), "Could not create connection config file");
        while (true) {
            try {
                String configFileAsString = readConfigFile();
                System.out.printf("Current SQL String: %s\n\n", configFileAsString);
                System.out.println("Creating connection from file...");
                return createJdbcConnection(configFileAsString);
            } catch (SQLException e) {
                try (PrintWriter writer = new PrintWriter(CONFIG_FILE)) {
                    System.out.println("Failed connection :(");
                    System.out.println("Now trying to create the connection manually");
                    String connectionString = createConnectionStringViaStdin();
                    writer.println(connectionString);
                } catch (FileNotFoundException fileNotFoundException) {
                    throw new IllegalStateException("Could not write to config file");
                }
            }
        }
    }

    /**
     * Reads the config file (src/main/resources/databaseConnectionConfig.json) for creating a JDBC connection
     *
     * @return the file contents as a string
     */
    private static final String readConfigFile() {
        try {
            StringBuilder fileStringBuilder = new StringBuilder();
            Scanner reader = new Scanner(CONFIG_FILE);
            while (reader.hasNextLine()) {
                fileStringBuilder.append(reader.nextLine());
            }
            reader.close();
            return fileStringBuilder.toString();

        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Could not find config file when reading it");
        }
    }

    /**
     * Creates the file containing the connection configuration settings if it does not exist
     *
     * @return true if the file already exists or the creation of the file was successful
     */
    private static final boolean connectionConfigFileHasBeenCreated() {
        try {
            File parentDirectory = CONFIG_FILE.getParentFile();
            boolean parentDirectoryHasBeenCreated = parentDirectory.exists() || parentDirectory.mkdirs();
            boolean configFileHasBeenCreated = CONFIG_FILE.exists() || (parentDirectoryHasBeenCreated && CONFIG_FILE.createNewFile());
            return configFileHasBeenCreated;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Creates a JDBC connection  from a string
     *
     * @param connectionString JDBC connection url
     * @return the JDBC connection
     * @throws SQLException if the connection url is invalid
     */
    private static final Connection createJdbcConnection(String connectionString) throws SQLException {
        return DriverManager.getConnection(connectionString);
    }

    /**
     * Creates  a JDBC connection string via stdin
     *
     * @return the JDBC connection url
     */
    private static final String createConnectionStringViaStdin() {
        try (Scanner input = new Scanner(System.in)) {
            String server = readProperty("server", input);
            String database = readProperty("database", input);
            boolean useIntegratedSecuty = askIntegratedSecurity(input);
            if (useIntegratedSecuty) {
                return "jdbc:sqlserver://" + server + ";database=" + database + ";integratedSecurity=true;";
            } else {
                String username = readProperty("username", input);
                String pass = readProperty("password", input);
                return "jdbc:sqlserver://" + server + ";database=" + database + ";user=" + username + ";password=" + pass + ";";
            }
        }
    }

    /**
     * Prompts the user via stding to enter a property
     *
     * @param propertyName property name to prompt
     * @param input        stream to stdin for the user to input in
     * @return the user input
     */
    private static final String readProperty(String propertyName, Scanner input) {
        while (true) {
            System.out.printf("Enter %s: ", propertyName);
            String propertyValue = input.nextLine();

            //If property value is not empty
            if (!propertyName.equals("") || !propertyValue.contains(" ")) {
                return propertyValue;
            }
            System.out.println("Property value cannot be empty or contain spaces");
        }
    }

    /**
     * Prompts the user to check whether he/she wants to use integrated windows security
     *
     * @param input stream to stdin for the user to input into
     * @return true if the user wants to use integrated security
     */
    private static final boolean askIntegratedSecurity(Scanner input) {
        System.out.print("Use integrated security (true/false)?: ");
        while (true) {
            try {
                return Boolean.parseBoolean(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Illegal input. Try again");
            }
        }
    }
}