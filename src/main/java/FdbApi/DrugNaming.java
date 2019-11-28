package FdbApi;

import java.sql.*;

public class DrugNaming {

    private Connection con;

    /**
     * Obtain all manufactured drugs from a prefix
     * Obtaining LN, HICL_SEQNO, GCN_SEQNO, DIN, IADDTE, IOBSDTE, MFG
     **/
    public String drugNamingManufactured(String namePrefix) throws SQLException{

        namePrefix += "%";
        System.out.println(namePrefix);

        PreparedStatement pstmt = con.prepareStatement(
                "SELECT t1.LN, t3.HICL_SEQNO, t1.GCN_SEQNO, t1.DIN, t1.IADDDTE, t1.IOBSDTE, t2.MFG "+
                        "FROM RICAIDC1 AS t1 " +
                        "JOIN RLBLRCA1 AS t2 ON (t1.ILBLRID = t2.ILBLRID) " +
                        "JOIN RGCNSEQ4 AS t3 ON (t1.GCN_SEQNO = t3.GCN_SEQNO) "+
                        "WHERE t1.LN LIKE ? " +
                        "ORDER BY t1.LN");
        pstmt.setString(1,namePrefix);

        StringBuilder output = new StringBuilder();
        output.append("LN, HICL_SEQNO, GCN_SEQNO, DIN, IADDTE, IOBSDTE, MFG");

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
            output.append(", ");
            output.append(rst.getString(5).trim());
            output.append(", ");
            output.append(rst.getString(6).trim());
            output.append(", ");
            output.append(rst.getString(7).trim());
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
