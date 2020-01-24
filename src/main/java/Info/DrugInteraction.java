package Info;

import org.apache.commons.lang3.Validate;

//TODO: Could added a Prescriber as a property 
/**
 * A object describing an interaction with a specific drug that is trying to be
 * prescribed by a {@link Prescriber}.
 */
public class DrugInteraction {

    private final Drug DRUG_BEING_PRESCRIBED;// Drug causing the interaction
    private final InteractionType TYPE_OF_INTERACTION;
    private final String INTERACTION_DESCRIPTION;

    DrugInteraction(Drug drugBeingPrescribed, InteractionType typeOfInteraction, String interactionDescription) {
        Validate.notNull(drugBeingPrescribed, "The a drug in a drug interaction cannot be null.");
        Validate.notNull(typeOfInteraction, "The type of a drug interaction cannot be null.");
        Validate.notEmpty(interactionDescription, "The description of a drug interaction cannot be empty or null.");

        DRUG_BEING_PRESCRIBED = drugBeingPrescribed;
        TYPE_OF_INTERACTION = typeOfInteraction;
        INTERACTION_DESCRIPTION = interactionDescription;
    }

    /**
     * Creates a drug interaction corresponding to a harmful food-drug interaction
     * using properties from the FDB database
     * 
     * @param drugBeingPrescribed        Drug that is being prescribed that causes a
     *                                   harmful reaction with the food.
     * @param foodInteractionDescription A description of what reaction occurs with
     *                                   the food. It is the property RESULT in the
     *                                   relation RDIMMA0
     * @throws NullPointerException  if the drug or description is null
     * @throws IllegalStateException if the description is the empty string
     */

    public static final DrugInteraction createFdbFoodInteraction(Drug drugBeingPrescribed, String foodInteractionDescription) {
        return new DrugInteraction(drugBeingPrescribed, InteractionType.DRUG_TO_FOOD, foodInteractionDescription);
    }

    /**
     * Creates a drug interaction corresponding to a harmful drug-allergy
     * interaction using properties from the FDB database
     * 
     * @param drugBeingPrescribed Drug causing the harmful allergic reaction
     * @throws NullPointerException  if the drug or description is null
     * @throws IllegalStateException if the description is the empty string
     */
    public static final DrugInteraction createFdbAllergyInteraction(Drug drugBeingPrescribed) {
        String description = "Patient is allergic to a ingredient in \"" + drugBeingPrescribed.getDisplayName() + "\"";
        return new DrugInteraction(drugBeingPrescribed, InteractionType.DRUG_TO_ALLERGY, description);
    }

    /**
     * @return The drug responsible for the interaction
     */
    public final Drug getDrugBeingPrescribed() {
        return DRUG_BEING_PRESCRIBED;
    }

    public final InteractionType getInteractionType() {
        return TYPE_OF_INTERACTION;
    }

    public final String getInteractionDescription() {
        return INTERACTION_DESCRIPTION;
    }

    public enum InteractionType {
        DRUG_TO_ALLERGY, DRUG_TO_FOOD, DRUG_TO_DRUG;
    }
}