package Prescriber;

import Info.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * A implementation of {@link Prescriber} using the FDB database
 */
class FdbPrescriber implements Prescriber {

    private final Connection FDB_CONNECTION;

    FdbPrescriber(String jdbcUrl, String username, String password) {
        try {
            FDB_CONNECTION = DriverManager.getConnection(jdbcUrl, username, password);
        } catch (SQLException e) {
            throw new IllegalStateException("Could not connect to fdb.\n" + e.getSQLState());
        }

    }

    FdbPrescriber(String username, String password) {
        this("jdbc:sqlserver://localhost:1433; databaseName=FDB;", username, password);
    }

    FdbPrescriber() {
        try {
            FDB_CONNECTION = DriverManager
                    .getConnection("jdbc:sqlserver://localhost:1433; databaseName=FDB;integratedSecurity=true");
        } catch (SQLException e) {
            throw new IllegalStateException("Could not connect via localhost to fdb.\n" + e.getSQLState());
        }
    }

    @Override
    public List<DrugToDrugInteraction> queryDrugInteractionsWithOtherDrugs(Drug drug, Iterable<Drug> otherDrugs) {
        try {
            List<Drug> currentDrugs = new ArrayList<>();
            StringBuilder testers = new StringBuilder();
            Iterator<Drug> otherDrugsIterator = otherDrugs.iterator();
            while (otherDrugsIterator.hasNext()) {
                Drug nextNext = otherDrugsIterator.next();
                currentDrugs.add(nextNext);
                testers.append(nextNext.getIdByName("ingredientListIdentifier"));
                if (otherDrugsIterator.hasNext())
                    testers.append(",");
            }

            PreparedStatement pStmtToQueryDrugToDrugInteractions = FDB_CONNECTION
                    .prepareStatement(
                            "SELECT DISTINCT Table1.DDI_DES " + ",ADI_EFFTXT " + "FROM "
                            + "(SELECT DISTINCT HICL_SEQNO AS HICL1 " + ",C4.DDI_CODEX AS CODEX1 "
                            + ",DDI_MONOX AS MONOX1 " + ",DDI_DES " + "FROM RGCNSEQ4 AS GCN "
                            + "JOIN RADIMGC4 AS C4 ON (GCN.GCN_SEQNO = C4.GCN_SEQNO) "
                            + "JOIN RADIMMA5 AS A5 ON (C4.DDI_CODEX = A5.DDI_CODEX) "
                            + "WHERE HICL_SEQNO = ?) AS Table1 " + "CROSS JOIN "
                            + "(SELECT DISTINCT HICL_SEQNO AS HICL2 " + ",C4.DDI_CODEX AS CODEX2 "
                            + ",DDI_MONOX AS MONOX2 " + ",DDI_DES " + "FROM RGCNSEQ4 AS GCN "
                            + "JOIN RADIMGC4 AS C4 ON (GCN.GCN_SEQNO = C4.GCN_SEQNO) "
                            + "JOIN RADIMMA5 AS A5 ON (C4.DDI_CODEX = A5.DDI_CODEX) " + "WHERE HICL_SEQNO IN ( "
                            + testers.toString() + " )) AS TABLE2 " + "JOIN RADIMIE4 AS E4 ON (CODEX1 = E4.DDI_CODEX) "
                            + "JOIN RADIMEF0 AS F0 ON (E4.ADI_EFFTC = F0.ADI_EFFTC) "
                            + "JOIN RADIMSL1 AS L1 ON (DDI_SL = L1.DDI_SL) "
                            + "WHERE MONOX1 = MONOX2 and CODEX1 != CODEX2");
            pStmtToQueryDrugToDrugInteractions.setInt(1, drug.getIdByName("ingredientListIdentifier"));

            ResultSet drugToDrugInteractionsAsRst = pStmtToQueryDrugToDrugInteractions.executeQuery();
            List<DrugToDrugInteraction> drugToDrugInteractionsAsObjects = new ArrayList<>();
            while (drugToDrugInteractionsAsRst.next()) {
                DrugToDrugInteraction drugToDrugInteraction = DrugToDrugInteraction.createFdbDrugToDrugInteraction(drug,
                        currentDrugs.get(0), drugToDrugInteractionsAsRst.getString(2).trim());
                drugToDrugInteractionsAsObjects.add(drugToDrugInteraction);
            }
            return drugToDrugInteractionsAsObjects;
        } catch (SQLException e) {
            throw new IllegalStateException("SQL is bad for querying drug to drug interactions.\n" + e.getSQLState());
        }
    }

    @Override
    public void destroyPrescriber() {
        try {
            FDB_CONNECTION.close();
        } catch (SQLException e) {
            throw new IllegalStateException("Could not close connection to FDB/\n" + e.getSQLState());
        }
    }

    @Override
    public List<DrugInteraction> queryFoodInteractionsOfDrug(Drug drug) {
        try {
            PreparedStatement pStmtToQueryFoodInteractions = FDB_CONNECTION.prepareStatement(
                    "SELECT RESULT " + "FROM RDFIMGC0 AS DF " + "JOIN RGCNSEQ4 AS GC ON (DF.GCN_SEQNO = GC.GCN_SEQNO)"
                            + "JOIN RDFIMMA0 AS DFI ON (DF.FDCDE = DFI.FDCDE) " + "WHERE GC.GCN_SEQNO = ?");
            pStmtToQueryFoodInteractions.setInt(1, drug.getIdByName("clinicalFormulationId"));

            ResultSet foodInteractionsAsRst = pStmtToQueryFoodInteractions.executeQuery();
            List<DrugInteraction> foodInteractionsAsObjects = new ArrayList<>();
            while (foodInteractionsAsRst.next()) {
                DrugInteraction foodInteraction = DrugInteraction.createFdbFoodInteraction(drug,
                        foodInteractionsAsRst.getString(1).trim());
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
    public List<DrugInteraction> queryAllergyInteractionsOfDrug(Drug drug, Iterable<Integer> allergyCodes) {
        try {
            StringBuilder testers = new StringBuilder();
            Iterator<Integer> otherAllergyIterator = allergyCodes.iterator();
            while (otherAllergyIterator.hasNext()) {
                int nextNext = otherAllergyIterator.next();
                testers.append(nextNext);
                if (otherAllergyIterator.hasNext())
                    testers.append(",");
            }

            PreparedStatement pStmtToQueryAllergyInteractions = FDB_CONNECTION.prepareStatement(
                    "SELECT HICL_SEQNO, L1.HIC_SEQN, L1.HIC, HIC_DESC, C0.DAM_ALRGN_GRP, DAM_ALRGN_GRP_DESC "
                            + "FROM RHICD5 AS D5 " + "JOIN RHICL1 AS L1 ON (D5.HIC_SEQN = L1.HIC_SEQN) "
                            + "JOIN RDAMGHC0 AS C0 ON (D5.HIC_SEQN = C0.HIC_SEQN) "
                            + "JOIN RDAMAGD1 AS GD1 ON (C0.DAM_ALRGN_GRP = GD1.DAM_ALRGN_GRP) "
                            + "WHERE HICL_SEQNO = ? AND C0.DAM_ALRGN_GRP IN ("+testers.toString()+")");
            pStmtToQueryAllergyInteractions.setInt(1, drug.getIdByName("ingredientListIdentifier"));

            ResultSet allergyInteractionsAsRst = pStmtToQueryAllergyInteractions.executeQuery();
            List<DrugInteraction> allergyInteractionsAsObjects = new ArrayList<>();
            while (allergyInteractionsAsRst.next()) {
                DrugInteraction allergyInteraction = DrugInteraction.createFdbAllergyInteraction(drug);
                allergyInteractionsAsObjects.add(allergyInteraction);
            }
            return allergyInteractionsAsObjects;
        } catch (SQLException e) {
            throw new IllegalStateException("SQL is bad for querying allergy interactions.\n" + e.getSQLState());
        }
    }

    private List<Drug> queryManufacturerDrugs(String prefix) {
        try {
            PreparedStatement pStmtToQueryDrugsBasedOnPrefix = FDB_CONNECTION.prepareStatement(
                    "SELECT TOP(100) t1.LN, t3.HICL_SEQNO, t1.GCN_SEQNO, t1.DIN, t1.IADDDTE, t1.IOBSDTE, t2.MFG "
                            + "FROM RICAIDC1 AS t1 " + "JOIN RLBLRCA1 AS t2 ON (t1.ILBLRID = t2.ILBLRID) "
                            + "JOIN RGCNSEQ4 AS t3 ON (t1.GCN_SEQNO = t3.GCN_SEQNO) " + "WHERE t1.LN LIKE ? "
                            + "ORDER BY t1.LN");
            pStmtToQueryDrugsBasedOnPrefix.setString(1, prefix + "%");
            ResultSet drugsAsRst = pStmtToQueryDrugsBasedOnPrefix.executeQuery();

            List<Drug> drugsAsObjects = new ArrayList<>();

            // For each SQL result, create a Java object representing that drug
            while (drugsAsRst.next()) {
                Drug drug = Drug.createFdbDrug(drugsAsRst.getString(1).trim(),  drugsAsRst.getInt(4), drugsAsRst.getInt(3),
                        drugsAsRst.getInt(2));
                /*
                 * Drug manufacturedDrug = new ManufacturedDrug.ManufacturedDrugBuilder()
                 * .setDisplayName(drugsAsRst.getString(1).trim())
                 * .setIngredientListIdentifier(drugsAsRst.getInt(2))
                 * .setClinicalFormulationId(drugsAsRst.getInt(3))
                 * .setCanadianDrugId(drugsAsRst.getString(4).trim())
                 * .setAddDate(drugsAsRst.getDate(5)) .setObseleteDate(drugsAsRst.getDate(6))
                 * .setManufacturerName(drugsAsRst.getString(7).trim()) .buildDrug();
                 */
                drugsAsObjects.add(drug);
            }
            return drugsAsObjects;
        } catch (SQLException e) {
            throw new IllegalStateException("SQL is bad for querying drugs.\n" + e.getSQLState());
        }
    }

}
