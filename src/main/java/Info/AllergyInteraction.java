package Info;

import org.apache.commons.lang3.Validate;

/**
 * Container class for storing display infomation about a allergy-drug
 * interaction
 * 
 * @see Drug
 */
public final class AllergyInteraction {
    // Drug causing the allergy
    private final Drug DRUG_INTERACTING;
    // Description of the allergy interaction
    private final String ALLERGY_INTERACTION_RESULT;

    private AllergyInteraction(Drug drugInteracting, String allergyInteractionResult) {
        Validate.notNull(drugInteracting, "The drug causing an allergic reaction cannot be null");
        Validate.notNull(allergyInteractionResult, "The result of an allergic reaction cannot be null");
        Validate.notEmpty(allergyInteractionResult, "The result of an allergic reaction cannot be an empty.");
        DRUG_INTERACTING = drugInteracting;
        ALLERGY_INTERACTION_RESULT = allergyInteractionResult;
    }

    /**
     * Creates an AllergyInteracting from the database FDB
     * 
     * @param drugInteracting          Drug causing the allergic interaction
     * @param allergyInteractionResult Description of the allegy interaction. It is
     *                                 either DAM_ALRGN_GRP_DESC in relation
     *                                 RDAMAGD1 or DAM_ALRGN_XSENSE_DESC in relation
     *                                 RDAMCSD1
     */
    public static final AllergyInteraction createFdbAllergyInteraction(Drug drugInteracting, String allergyInteractionResult) {
        return new AllergyInteraction(drugInteracting, allergyInteractionResult);
    }

    /**
     * @return the drug causing the allergy interaction
     */
    public final Drug getInteractingDrug() {
        return DRUG_INTERACTING;
    }

    /**
     * @return a description of the allergy interaction
     */
    public final String getAllergyInteractionResult() {
        return ALLERGY_INTERACTION_RESULT;
    }
}