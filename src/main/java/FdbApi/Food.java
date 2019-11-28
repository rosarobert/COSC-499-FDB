package FdbApi;

import java.sql.*;

public class Food {

    private Connection con;

    /**
     * GCN_SEQNO to Food Allergy
     * Obtaining GCN, FDCDE, DNAME, RESULT
     **/
    public String GCNtoFDCDE(int GCN) throws SQLException{

        PreparedStatement pstmt = con.prepareStatement(
                "SELECT GC.GCN_SEQNO, DF.FDCDE, DNAME, RESULT "+
                        "FROM RDFIMGC0 AS DF " +
                        "JOIN RGCNSEQ4 AS GC ON (DF.GCN_SEQNO = GC.GCN_SEQNO)" +
                        "JOIN RDFIMMA0 AS DFI ON (DF.FDCDE = DFI.FDCDE) "+
                        "WHERE GC.GCN_SEQNO = ?");
        pstmt.setInt(1,GCN);

        StringBuilder output = new StringBuilder();
        output.append("GCN_SEQNO, FDCDE, DNAME, RESULT");

        ResultSet rst = pstmt.executeQuery();

        while (rst.next()) {
            output.append("\n");
            output.append(rst.getString(1).trim());
            output.append(", ");
            output.append(rst.getString(2).trim());
            output.append(", ");
            output.append(rst.getString(3).trim());
            output.append(", ");
            output.append(rst.getString(4).trim());
        }
        return output.toString();
    }

    /**
     * Routed_MED_ID to Food Allergy
     * Obtaining ROUTED_MED_ID, FDCDE, DNAME, RESULT
     **/
    public String RoutedToFDCDE(int RMI) throws SQLException{

        PreparedStatement pstmt = con.prepareStatement(
                "SELECT RMI.ROUTED_MED_ID, DF.FDCDE, DNAME, RESULT "+
                        "FROM RDFIMRM0 AS DF " +
                        "JOIN RMIRMID1 AS RMI ON (DF.ROUTED_MED_ID = RMI.ROUTED_MED_ID) " +
                        "JOIN RDFIMMA0 AS DFI ON  (DF.FDCDE = DFI.FDCDE)"+
                        "WHERE RMI.ROUTED_MED_ID = ?");
        pstmt.setInt(1,RMI);

        StringBuilder output = new StringBuilder();
        output.append("ROUTED_MED_ID, FDCDE, DNAME, RESULT");

        ResultSet rst = pstmt.executeQuery();

        while (rst.next()) {
            output.append("\n");
            output.append(rst.getString(1).trim());
            output.append(", ");
            output.append(rst.getString(2).trim());
            output.append(", ");
            output.append(rst.getString(3).trim());
            output.append(", ");
            output.append(rst.getString(4).trim());
        }
        return output.toString();
    }

    /** Makes a connection to the database and returns connection to caller.
     * @return
     * 		connection
     * @throws SQLException
     * 		if an error occurs
     */
    public Connection connect() {
        String url = "jdbc:sqlserver://localhost:1433; databaseName=FDB;integratedSecurity=true";
        System.out.println("Connecting to database.");
        try{
            con = DriverManager.getConnection(url);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return con;
    }
    /**Closes connection to database.*/
    public void close() {
        System.out.println("Closing database connection.");
        try {
            if (con != null)
                con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
