package Prescriber;


import Info.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A implementation of {@link Prescriber} using the FDB database
 */
class FdbPrescriber implements Prescriber {

    private Connection fdbConnection;

    @Override
    public List<DrugInteraction> queryDrugInteractionsWithOtherDrugs(Drug drug) {
        return null;
    }

    @Override
    public void initializePrescriber(String url) {
        try {
            fdbConnection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new IllegalStateException("Error when creating connection to fdb" + e.getMessage());
        }
    }

    @Override
    public void destroyPrescriber() {
        try {
            fdbConnection.close();
        } catch (SQLException e) {
            throw new IllegalStateException("Could not close connection to FDB/\n" + e.getSQLState());
        }
    }

    @Override
    public List<FoodInteraction> queryFoodInteractionsOfDrug(Drug drug) {
        try {
            PreparedStatement pStmtToQueryFoodInteractions = fdbConnection.prepareStatement(
                    "SELECT GC.GCN_SEQNO, DF.FDCDE, DNAME, RESULT " +
                            "FROM RDFIMGC0 AS DF " +
                            "JOIN RGCNSEQ4 AS GC ON (DF.GCN_SEQNO = GC.GCN_SEQNO)" +
                            "JOIN RDFIMMA0 AS DFI ON (DF.FDCDE = DFI.FDCDE) " +
                            "WHERE GC.GCN_SEQNO = ?"
            );
            pStmtToQueryFoodInteractions.setInt(1, drug.getClinicalFormulationId());

            ResultSet foodInteractionsAsRst = pStmtToQueryFoodInteractions.executeQuery();
            List<FoodInteraction> foodInteractionsAsObjects = new ArrayList<>();
            while (foodInteractionsAsRst.next()) {
                FoodInteraction foodInteraction = new FoodInteraction.FoodInteractionBuilder(drug)
                        .setDrugFoodInteractionCode(foodInteractionsAsRst.getInt(2))
                        .setDrugFoodInteractionName(foodInteractionsAsRst.getString(3).trim())
                        .setDrugFoodInteractionResult(foodInteractionsAsRst.getString(4).trim())
                        .buildFoodInteraction();
                foodInteractionsAsObjects.add(foodInteraction);
            }
            return foodInteractionsAsObjects;
        } catch (SQLException e) {
            throw new IllegalStateException("SQL is bad for querying food interactions.\n" + e.getSQLState());
        }
    }

    @Override
    public List<Drug> queryDrugs(String prefix) {
        return queryManufacturerDrugs(prefix);
    }

    @Override
    public List<AllergyInteraction> queryAllergyInteractionsOfDrug(Drug drug, Patient patient) {
        return null;
    }

    void initializePrescriber() {
        initializePrescriber("jdbc:sqlserver://localhost:1433; databaseName=FDB;integratedSecurity=true");
    }

    private List<Drug> queryManufacturerDrugs(String prefix) {
        try {
            PreparedStatement pStmtToQueryDrugsBasedOnPrefix = fdbConnection.prepareStatement(
                    "SELECT TOP(100) t1.LN, t3.HICL_SEQNO, t1.GCN_SEQNO, t1.DIN, t1.IADDDTE, t1.IOBSDTE, t2.MFG " +
                            "FROM RICAIDC1 AS t1 " +
                            "JOIN RLBLRCA1 AS t2 ON (t1.ILBLRID = t2.ILBLRID) " +
                            "JOIN RGCNSEQ4 AS t3 ON (t1.GCN_SEQNO = t3.GCN_SEQNO) " +
                            "WHERE t1.LN LIKE ? " +
                            "ORDER BY t1.LN"
            );
            pStmtToQueryDrugsBasedOnPrefix.setString(1, prefix + "%");
            ResultSet drugsAsRst = pStmtToQueryDrugsBasedOnPrefix.executeQuery();

            List<Drug> drugsAsObjects = new ArrayList<>();

            //For each SQL result, create a Java object representing that drug
            while (drugsAsRst.next()) {
                Drug manufacturedDrug = new ManufacturedDrug.ManufacturedDrugBuilder()
                        .setDisplayName(drugsAsRst.getString(1).trim())
                        .setIngredientListIdentifier(drugsAsRst.getInt(2))
                        .setClinicalFormulationId(drugsAsRst.getInt(3))
                        .setCanadianDrugId(drugsAsRst.getString(4).trim())
                        .setAddDate(drugsAsRst.getDate(5))
                        .setObseleteDate(drugsAsRst.getDate(6))
                        .setManufacturerName(drugsAsRst.getString(7).trim())
                        .buildDrug();
                drugsAsObjects.add(manufacturedDrug);
            }
            return drugsAsObjects;
        } catch (SQLException e) {
            throw new IllegalStateException("SQL is bad for querying drugs.\n" + e.getSQLState());
        }
    }

}
