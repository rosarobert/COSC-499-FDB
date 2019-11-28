package Utils;

import java.sql.*;
import java.util.*;

public class JoinTables {
    public static void main(String[] args) {
        String url = "jdbc:sqlserver://localhost; databaseName=FDB;integratedSecurity=true";
        try {
            Connection connection = DriverManager.getConnection(url);
            String query = "SELECT COLUMN_NAME , TABLE_NAME \n" +
                    "FROM information_schema.COLUMNS \n" +
                    "WHERE TABLE_NAME LIKE 'R%' " +
                    "ORDER BY COLUMN_NAME ";

            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            Map<String, Set<String>> tableToAttributes = new HashMap<>();
            while (rs.next()) {
                String attributeName = rs.getString(1);
                String tableName = rs.getString(2);
                System.out.println(attributeName + " : " + tableName);
                if(tableToAttributes.containsKey(tableName))
                    tableToAttributes.get(tableName).add(attributeName);
                else {
                    tableToAttributes.put(tableName, new HashSet<>());
                    tableToAttributes.get(tableName).add(attributeName);
                }
            }
            System.out.println(tableToAttributes.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
