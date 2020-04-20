package Prescriber;

import Apps.ConnectionConfiguration;
import Info.Allergy;
import Info.Drug;
import Info.DrugInteraction;
import Info.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A implementation of prescriber where we only parallelize {@link #findInteractions(Drug, Patient)}. That is, we do not
 * use pagination or manipulating relational algebra
 */
final class FdbPrescriberParallel implements Prescriber {

    private final Connection FDB_CONNECTION;
    private final int PAGE_SIZE;

    /**
     * @see #createFdbPrescriber()
     */
    FdbPrescriberParallel() {
        this(20);
    }

    /**
     * @see #createFdbPrescriber(int)
     */
    FdbPrescriberParallel(int pageSize) {
        PAGE_SIZE = pageSize;
        FDB_CONNECTION = ConnectionConfiguration.getJdbcConnection();
    }

    @Override
    public List<Drug> queryDrugs(String prefix) {
        return queryManufacturerDrugs(prefix);
    }

    @Override
    public List<Drug> queryDrugs(String pattern, int page) {
        List<Drug> allDrugs = queryDrugs(pattern);
        int startIndex = (page - 1) * PAGE_SIZE;
        int endIndex = page * PAGE_SIZE - 1;
        List<Drug> pageList = new ArrayList<>(20);
        for (int i = startIndex; i < endIndex; i++) {
            pageList.add(allDrugs.get(i));
        }
        return pageList;
    }

    @Override
    public List<DrugInteraction> findInteractions(Drug drugBeingPrescribed, Patient patient) {
        List<DrugInteraction> interactions = new CopyOnWriteArrayList<>();
        ExecutorService threadExecutor = Executors.newFixedThreadPool(3);
        threadExecutor.execute(() -> interactions.addAll(queryFoodInteractionsOfDrug(drugBeingPrescribed)));
        threadExecutor.execute(() -> interactions.addAll(queryAllergyInteractionsOfDrug(drugBeingPrescribed, patient)));
        threadExecutor.execute(() -> interactions.addAll(queryDrugInteractionsWithOtherDrugs(drugBeingPrescribed, patient)));
        threadExecutor.shutdown();
        return interactions;
    }

    @Override
    public boolean closePrescriber() {
        try {
            FDB_CONNECTION.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public void prescribeDrug(Drug drug, Patient patient) {
        patient.addDrug(drug);
    }

    /**
     * Finds all interactions between a drug being prescribed and the drugs currently prescribed to a patient
     *
     * @param drug    drying being prescribed
     * @param patient patient being prescribed
     * @return a list of harmful drug interactions
     */
    public List<DrugInteraction> queryDrugInteractionsWithOtherDrugs(Drug drug, Patient patient) {
        try {
            SortedSet<Drug> currentDrugs = patient.getDrugsPrescribed();

            // Create a two coma separated strings to use in prepared statement.
            // ingredientIdentifiers are ingredient list codes and identifiers of the unique
            // identifiers
            StringBuilder ingredientIdentifiers = new StringBuilder();
            StringBuilder identifiers = new StringBuilder();
            Iterator<Drug> otherDrugsIterator = patient.getDrugsPrescribed()
                    .iterator();
            while (otherDrugsIterator.hasNext()) {
                Drug currentDrug = otherDrugsIterator.next();
                currentDrugs.add(currentDrug);
                ingredientIdentifiers.append(currentDrug.getIngredientIdentifier());
                identifiers.append(currentDrug.getId());
                if (otherDrugsIterator.hasNext()) {
                    ingredientIdentifiers.append(",");
                    identifiers.append(", ");
                }
            }

            // Query to find interactions between a single drug drug and a list of drugs a
            // patient is currently taking
            PreparedStatement pStmtToQueryDrugToDrugInteractions = FDB_CONNECTION.prepareStatement(
                    "SELECT DISTINCT DIN,ADI_EFFTXT " + "FROM "
                            + "(SELECT DISTINCT HICL_SEQNO AS HICL1,C4.DDI_CODEX AS CODEX1 ,DDI_MONOX AS MONOX1,DDI_DES "
                            + "FROM RGCNSEQ4 AS GCN " + "JOIN RADIMGC4 AS C4 ON (GCN.GCN_SEQNO = C4.GCN_SEQNO) "
                            + "JOIN RADIMMA5 AS A5 ON (C4.DDI_CODEX = A5.DDI_CODEX) " + "WHERE HICL_SEQNO = ?"
                            + ") AS Table1 " + "CROSS JOIN "
                            + "(SELECT DISTINCT HICL_SEQNO AS HICL2, DIN, LN ,GCN.GCN_SEQNO ,C4.DDI_CODEX AS CODEX2 ,DDI_MONOX AS MONOX2 ,DDI_DES "
                            + "FROM RGCNSEQ4 AS GCN " + "JOIN RADIMGC4 AS C4 ON (GCN.GCN_SEQNO = C4.GCN_SEQNO) "
                            + "JOIN RADIMMA5 AS A5 ON (C4.DDI_CODEX = A5.DDI_CODEX) "
                            + "JOIN RICAIDC1 AS RIC ON (RIC.GCN_SEQNO = GCN.GCN_SEQNO)" + "WHERE HICL_SEQNO IN ("
                            + ingredientIdentifiers.toString() + ") AND DIN IN (" + identifiers.toString() + ")"
                            + ") AS TABLE2 " + "JOIN RADIMIE4 AS E4 ON (CODEX1 = E4.DDI_CODEX) "
                            + "JOIN RADIMEF0 AS F0 ON (E4.ADI_EFFTC = F0.ADI_EFFTC) "
                            + "JOIN RADIMSL1 AS L1 ON (DDI_SL = L1.DDI_SL) "
                            + "WHERE MONOX1 = MONOX2 and CODEX1 != CODEX2 " + "ORDER BY DIN");
            pStmtToQueryDrugToDrugInteractions.setInt(1, drug.getIngredientIdentifier());

            ResultSet drugToDrugInteractionsAsRst = pStmtToQueryDrugToDrugInteractions.executeQuery();
            List<DrugInteraction> drugToDrugInteractions = new ArrayList<>();
            Iterator<Drug> currentDrugsIterator = currentDrugs.iterator();
            Drug currentDrug = currentDrugsIterator.next();
            while (drugToDrugInteractionsAsRst.next()) {
                int idOfDrugInteracting = drugToDrugInteractionsAsRst.getInt(1);
                String interactionDescription = drugToDrugInteractionsAsRst.getString(2)
                        .trim();
                while (currentDrug.getId() < idOfDrugInteracting && currentDrugsIterator.hasNext()) {
                    currentDrug = currentDrugsIterator.next();
                }
                if (currentDrug.getId() == idOfDrugInteracting) {
                    DrugInteraction currentInteraction = DrugInteraction.createFdbDrugToDrugInteraction(drug,
                            currentDrug, interactionDescription);
                    drugToDrugInteractions.add(currentInteraction);
                }
            }
            return drugToDrugInteractions;
        } catch (SQLException e) {
            throw new IllegalStateException("SQL is bad for querying drug to drug interactions.\n" + e.getSQLState());
        }
    }

    /**
     * Finds all harmful food interactions that could occur when prescribed a given dryg
     *
     * @param drug drug being prescribed
     * @return a list of harmful interactions that could occur if you combine a food with the drug
     */
    public List<DrugInteraction> queryFoodInteractionsOfDrug(Drug drug) {
        try {
            PreparedStatement pStmtToQueryFoodInteractions = FDB_CONNECTION.prepareStatement("SELECT DISTINCT RESULT "
                    + "FROM RDFIMGC0 AS DF " + "JOIN RGCNSEQ4 AS GC ON (DF.GCN_SEQNO = GC.GCN_SEQNO)"
                    + "JOIN RDFIMMA0 AS DFI ON (DF.FDCDE = DFI.FDCDE) " + "WHERE GC.GCN_SEQNO = ?");
            pStmtToQueryFoodInteractions.setInt(1, drug.getGcnSeqno());

            ResultSet foodInteractionsAsRst = pStmtToQueryFoodInteractions.executeQuery();
            List<DrugInteraction> foodInteractionsAsObjects = new ArrayList<>();
            while (foodInteractionsAsRst.next()) {
                DrugInteraction foodInteraction = DrugInteraction.createFdbFoodInteraction(drug,
                        foodInteractionsAsRst.getString(1)
                                .trim());
                foodInteractionsAsObjects.add(foodInteraction);
            }
            return foodInteractionsAsObjects;
        } catch (SQLException e) {
            throw new IllegalStateException("SQL is bad for querying food interactions.\n" + e.getSQLState());
        }
    }

    /**
     * Finds all interactions between a drug being prescribed and the allergies a patient has
     *
     * @param drug    drug being prescribed
     * @param patient patient being prescribed a drug
     * @return a list of harmful interactions between the patient's allergies and the drug being prescribed
     */
    public List<DrugInteraction> queryAllergyInteractionsOfDrug(Drug drug, Patient patient) {
        try {
            // Create a coma separated string of allergy codes to use in prepared statement
            StringBuilder testers = new StringBuilder();
            Iterator<Allergy> allergyIterator = patient.getPatientAllergies()
                    .iterator();
            while (allergyIterator.hasNext()) {
                Allergy currentAllergy = allergyIterator.next();
                testers.append(currentAllergy.getId());
                if (allergyIterator.hasNext())
                    testers.append(",");
            }

            // Query all allergy interactions between a drug and a list of allergies
            PreparedStatement pStmtToQueryAllergyInteractions = FDB_CONNECTION.prepareStatement(
                    "SELECT HICL_SEQNO, L1.HIC_SEQN, L1.HIC, HIC_DESC, C0.DAM_ALRGN_GRP, DAM_ALRGN_GRP_DESC "
                            + "FROM RHICD5 AS D5 " + "JOIN RHICL1 AS L1 ON (D5.HIC_SEQN = L1.HIC_SEQN) "
                            + "JOIN RDAMGHC0 AS C0 ON (D5.HIC_SEQN = C0.HIC_SEQN) "
                            + "JOIN RDAMAGD1 AS GD1 ON (C0.DAM_ALRGN_GRP = GD1.DAM_ALRGN_GRP) "
                            + "WHERE HICL_SEQNO = ? AND C0.DAM_ALRGN_GRP IN (" + testers.toString() + ")");
            pStmtToQueryAllergyInteractions.setInt(1, drug.getIngredientIdentifier());

            ResultSet allergyInteractionsAsRst = pStmtToQueryAllergyInteractions.executeQuery();
            List<DrugInteraction> allergyInteractionsAsObjects = new ArrayList<>();
            while (allergyInteractionsAsRst.next()) {
                Allergy allergy = Allergy.createFdbAllergy(allergyInteractionsAsRst.getInt(5),
                        allergyInteractionsAsRst.getString(6));
                DrugInteraction allergyInteraction = DrugInteraction.createFdbAllergyInteraction(allergy, drug);
                allergyInteractionsAsObjects.add(allergyInteraction);
            }
            return allergyInteractionsAsObjects;
        } catch (SQLException e) {
            throw new IllegalStateException("SQL is bad for querying allergy interactions.\n" + e.getSQLState());
        }
    }

    /**
     * A specific class of drugs in FDB
     */
    private List<Drug> queryManufacturerDrugs(String prefix) {
        try {
            PreparedStatement pStmtToQueryDrugsBasedOnPrefix = FDB_CONNECTION.prepareStatement(
                    "SELECT t1.LN, t3.HICL_SEQNO, t1.GCN_SEQNO, t1.DIN, t1.IADDDTE, t1.IOBSDTE, t2.MFG "
                            + "FROM RICAIDC1 AS t1 " + "JOIN RLBLRCA1 AS t2 ON (t1.ILBLRID = t2.ILBLRID) "
                            + "JOIN RGCNSEQ4 AS t3 ON (t1.GCN_SEQNO = t3.GCN_SEQNO) " + "WHERE t1.LN LIKE ? "
                            + "ORDER BY t1.LN");
            pStmtToQueryDrugsBasedOnPrefix.setString(1, "%" + prefix + "%");
            ResultSet drugsAsRst = pStmtToQueryDrugsBasedOnPrefix.executeQuery();

            List<Drug> drugsAsObjects = new ArrayList<>();

            // For each SQL result, create a Java object representing that drug
            while (drugsAsRst.next()) {
                Drug drug = Drug.createFdbDrug(drugsAsRst.getInt(4), drugsAsRst.getInt(2), drugsAsRst.getInt(3),
                        drugsAsRst.getString(1)
                                .trim());
                drugsAsObjects.add(drug);
            }
            return drugsAsObjects;
        } catch (SQLException e) {
            throw new IllegalStateException("SQL is bad for querying drugs.\n" + e.getSQLState());
        }
    }

    @Override
    public List<Allergy> queryAllergies(String prefix) {
        try {
            PreparedStatement pStmtToQueryAllergiesBasedOnPrefix = FDB_CONNECTION.prepareStatement(
                    "SELECT DAM_ALRGN_GRP, DAM_ALRGN_GRP_DESC " + "FROM RDAMAGD1 " + "WHERE DAM_ALRGN_GRP_DESC LIKE ?");
            pStmtToQueryAllergiesBasedOnPrefix.setString(1, prefix + "%");
            ResultSet drugsAsRst = pStmtToQueryAllergiesBasedOnPrefix.executeQuery();

            List<Allergy> allergiesAsObjects = new ArrayList<>();

            // For each SQL result, create a Java object representing that Allergy
            while (drugsAsRst.next()) {
                Allergy allergy = Allergy.createFdbAllergy(drugsAsRst.getInt(1), drugsAsRst.getString(2)
                        .trim());
                allergiesAsObjects.add(allergy);
            }
            return allergiesAsObjects;
        } catch (SQLException e) {
            throw new IllegalStateException("SQL is bad for querying drugs.\n" + e.getSQLState());
        }
    }

}
