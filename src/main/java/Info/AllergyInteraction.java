package Info;

import org.apache.commons.lang3.Validate;

/**
 * Container class for storing display infomation about a allergy-drug
 * interaction
 * 
 * @see Drug
 */
public class AllergyInteraction {
    // Drug causing the allergy
    private final Drug DRUG_INTERACTING;
    // Description of the allergy interaction
    private final String ALLERGY_INTERACTION_RESULT;

    private AllergyInteraction(AllergyInteractionBuilder builder) {
        DRUG_INTERACTING = builder.drugInteracting;
        ALLERGY_INTERACTION_RESULT = builder.allergyInteractionResult;
    }

    public Drug getInteractingDrug() {
        return DRUG_INTERACTING;
    }

    public String getAllergyInteractionResult() {
        return ALLERGY_INTERACTION_RESULT;
    }

    /**
     * Class for building a AllergyInteraction instance
     * <p>
     * Note: This builder class is implemented similar to the examples in Chapter 2
     * Item 2 of Effective Java, 3rd Edition, by Joshua Bloch
     */
    public static class AllergyInteractionBuilder {
        private Drug drugInteracting;
        private String allergyInteractionResult;

        public AllergyInteractionBuilder setDrugInteracting(Drug drugInteracting) {
            Validate.notNull(drugInteracting, "The drug causing an allergic reaction cannot be null");
            this.drugInteracting = drugInteracting;
            return this;
        }

        public AllergyInteractionBuilder setAllergyInteractionResult(String allergyInteractionResult) {
            Validate.notNull(allergyInteractionResult, "The result of an allergic reaction cannot be null");
            Validate.notEmpty(allergyInteractionResult, "The result of an allergic reaction cannot be an empty.");
            this.allergyInteractionResult = allergyInteractionResult;
            return this;
        }

        public AllergyInteraction buildAllergyInteraction() {
            Validate.notNull(drugInteracting, "Cannot build an AllergyInteraction without a interacting drug");
            Validate.notNull(allergyInteractionResult, "Cannot build AllergyInteraction without a result.");
            return new AllergyInteraction(this);
        }
    }
}