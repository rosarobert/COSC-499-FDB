package FdbApi;

import Info.ManufacturedDrug;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DrugNaming {

    private Connection con;

    public static void main(String[] args) throws SQLException {
        DrugNaming x = new DrugNaming();
        x.connect();
        List<ManufacturedDrug> drugs = x.drugNamingManufactured("");
        printDrugs(drugs);
        x.close();
    }

    public static void printDrugs(List<ManufacturedDrug> drugs) {
        System.out.println("_______________________________________________________");
        System.out.printf("| %-30s | %-20s |\n", "LABEL NAME", "MANUFACTURER NAME");
        System.out.println("________________________________________________________");
        for (ManufacturedDrug drug : drugs)
            System.out.printf("| %-30s | %-20s |\n", drug.getLN(), drug.getMFG());
    }

    /**
     * Obtain all manufactured drugs from a prefix Obtaining LN, HICL_SEQNO, GCN_SEQNO, DIN, IADDTE, IOBSDTE, MFG
     **/
    public List<ManufacturedDrug> drugNamingManufactured(String namePrefix) throws SQLException {

        namePrefix += "%";
        System.out.println(namePrefix);

        PreparedStatement pstmt = con.prepareStatement(
                "SELECT TOP(100) t1.LN, t3.HICL_SEQNO, t1.GCN_SEQNO, t1.DIN, t1.IADDDTE, t1.IOBSDTE, t2.MFG " +
                        "FROM RICAIDC1 AS t1 " +
                        "JOIN RLBLRCA1 AS t2 ON (t1.ILBLRID = t2.ILBLRID) " +
                        "JOIN RGCNSEQ4 AS t3 ON (t1.GCN_SEQNO = t3.GCN_SEQNO) " +
                        "WHERE t1.LN LIKE ? " +
                        "ORDER BY t1.LN");
        pstmt.setString(1, namePrefix);
        ResultSet rst = pstmt.executeQuery();

        List<ManufacturedDrug> drugsReturned = new ArrayList<>();

        while (rst.next()) {
            ManufacturedDrug.ManufactoredDrugBuilder mDrugBuilder = new ManufacturedDrug.ManufactoredDrugBuilder();
            mDrugBuilder
                    .setLabelName(rst.getString(1))
                    .setIngredientListIdentifier(rst.getInt(2))
                    .setClinicalFormulationId(rst.getInt(3))
                    .setCanadianDrugIdentificationNumber(rst.getString(4))
                    .setIddfAddDate(rst.getString(5))
                    .setIddsObsoleteDate(rst.getString(6))
                    .setManufacturerName(rst.getString(7));
            drugsReturned.add(mDrugBuilder.buildDrug());
        }
        return drugsReturned;
    }

    /**
     * Makes a connection to the database and returns connection to caller.
     *
     * @return connection
     * @throws SQLException if an error occurs
     */
    public Connection connect() {
        String url = "jdbc:sqlserver://localhost:1433; databaseName=FDB;integratedSecurity=true";
        System.out.println("Connecting to database.");
        try {
            con = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    /**
     * Closes connection to database.
     */
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
