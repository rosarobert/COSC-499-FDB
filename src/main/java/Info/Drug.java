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

    // Stores IDs specfic to an implementation. See class description
    private final Map<String, Integer> NAME_TO_ID;
    // Name the user should see
    private final String DISPLAY_NAME;

    Drug(DrugBuilder drugBuilder) {
        NAME_TO_ID = drugBuilder.NAME_TO_ID;
        DISPLAY_NAME = drugBuilder.displayName;
    }

    /**
     * Retrieves the name for this drug that should be displayed to the user
     */
    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    /**
     * Retrieves an id
     * 
     * @param name name of the id. This could be a name of a column in a database
     * @return the id corresponding to the given name
     */
    public int getIdByName(String name) {
        return NAME_TO_ID.get(name);
    }

    /**
     * Determines if the drug has an id with a given name
     * 
     * @param name name to check
     * @return true if the drug has an id with the given name
     */
    public boolean has(String name) {
        return NAME_TO_ID.containsKey(name);
    }

    /**
     * Class for building a Drug instance
     * <p>
     * Note: This builder class is implemented similar to the examples in Chapter 2
     * Item 2 of Effective Java, 3rd Edition, by Joshua Bloch
     */
    public final static class DrugBuilder {
        private final Map<String, Integer> NAME_TO_ID = new HashMap<>();
        private String displayName;

        public DrugBuilder setDisplayName(String displayName) {
            Validate.notNull(displayName, "Display name cannot be null.");
            Validate.notEmpty(displayName, "Display name must be a non-empty string");
            this.displayName = displayName.trim();
            return this;
        }

        public DrugBuilder addId(String nameOfId, int id) {
            Validate.notNull(nameOfId, "The name of an id cannot be null");
            Validate.notEmpty(displayName, "The name of an id cannot be an empty string");
            NAME_TO_ID.put(nameOfId, id);
            return this;
        }

        public Drug build() {
            Validate.notNull(displayName, "You cannot build a drug without a name.");
            return new Drug(this);
        }
    }

}
