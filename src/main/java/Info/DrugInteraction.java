package Info;

import org.apache.commons.lang3.Validate;

/**
 * A object describing some interaction with a specific drug. This interaction
 * is usually a harmful interaction like a allergic or food interaction.
 */
public final class DrugInteraction {

    private final Drug DRUG_INTERACTING;// Drug causing the interaction
    private final InteractionType TYPE_OF_INTERACTION;
    private final String INTERACTION_DESCRIPTION;

    private DrugInteraction(Drug drugInteracting, InteractionType typeOfInteraction, String interactionDescription) {
        Validate.notNull(drugInteracting, "The a drug in a drug interaction cannot be null.");
        Validate.notNull(typeOfInteraction, "The type of a drug interaction cannot be null.");
        Validate.notEmpty(interactionDescription, "The description of a drug interaction cannot be empty or null.");

        DRUG_INTERACTING = drugInteracting;
        TYPE_OF_INTERACTION = typeOfInteraction;
        INTERACTION_DESCRIPTION = interactionDescription;
    }

    /**
     * Creates a drug interaction corresponding to a harmful food-drug interaction
     * using the FDB database
     * 
     * @param drugInteracting            Drug causing the harmful reaction with the
     *                                   food.
     * @param foodInteractionDescription A description of what reaction occurs with
     *                                   the food. It is the property RESULT in the
     *                                   relation RDIMMA0
     * @throws NullPointerException  if the drug or description is null
     * @throws IllegalStateException if the description is the empty string
     */

    public static final DrugInteraction createFdbFoodInteraction(Drug drugInteracting, String foodInteractionDescription) {
        return new DrugInteraction(drugInteracting, InteractionType.DRUG_TO_FOOD, foodInteractionDescription);
    }

    /**
     * Creates a drug interaction corresponding to a harmful drug-allergy
     * interaction using the FDB database
     * 
     * @param drugInteracting               Drug causing the harmful allergic
     *                                      reaction
     * @param allergyInteractionDescription A description of what exactly the
     *                                      allergic reaction is
     * @throws NullPointerException  if the drug or description is null
     * @throws IllegalStateException if the description is the empty string
     */
    public static final DrugInteraction createFdbAllergyInteraction(Drug drugInteracting, String allergyInteractionDescription) {
        return new DrugInteraction(drugInteracting, InteractionType.DRUG_TO_ALLERGY, allergyInteractionDescription);
    }

    /**
     * @return The drug responcible for the interaction
     */
    public final Drug getDrugInteracting() {
        return DRUG_INTERACTING;
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