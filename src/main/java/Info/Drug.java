package Info;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.Validate;

/**
 * A container class for storing necessary infomation about a single Drug
 * <p>
 * This class is inspired based on the design of FDB, but could be used in other
 * circumstances
 * <p>
 * The main purpose of this class is to hold infomation needed to find other
 * interactions such as drug, food and allergic reactions.
 * <p>
 * The class does this using a map; the name of the id is the key and the id is
 * the value. Other classes then use these ids to figure out interactions the
 * drug has.
 * 
 * @see Prescriber
 * @see DrugInteraction
 * @see FoodInteraction
 * @see AllergyInteraction
 */
public final class Drug {

    // Name the user should see
    private final String DISPLAY_NAME;
    // Stores IDs specfic to an implementation. See class description
    private final Map<String, Integer> NAME_TO_ID;

    private Drug(String displayName) {
        Validate.notNull(displayName, "Display name of a drug cannot be null.");
        DISPLAY_NAME = displayName;
        NAME_TO_ID = new HashMap<>();
    }

    /**
     * Creates a drug based on the FDB database
     * 
     * @param displayName              Name displayed to the user. It LN in relation
     *                                 RICAIDC1
     * @param clinicalFormulationId    GCN_SEQNO in relation RICAIDC1
     * @param ingredientListIdentifier HICL_SEQNO in relation RGCNSEQ4
     * @return
     */
    public static final Drug createFdbDrug(String displayName, int clinicalFormulationId, int ingredientListIdentifier) {
        Drug drugCreated = new Drug(displayName);
        drugCreated.NAME_TO_ID.put("clinicalFormulationId", clinicalFormulationId);
        drugCreated.NAME_TO_ID.put("ingredientListIdentifier", ingredientListIdentifier);
        return drugCreated;
    }

    /**
     * Retrieves the name for this drug that should be displayed to the user
     */
    public final String getDisplayName() {
        return DISPLAY_NAME;
    }

    /**
     * Retrieves an id
     * 
     * @param name name of the id. This could be a name of a column in a database
     * @return the id corresponding to the given name
     */
    public final int getIdByName(String name) {
        return NAME_TO_ID.get(name);
    }

    /**
     * Determines if the drug has an id with a given name
     * 
     * @param name name to check
     * @return true if the drug has an id with the given name
     */
    public final boolean has(String name) {
        return NAME_TO_ID.containsKey(name);
    }
}