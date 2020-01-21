package Info;

/**
 * Container class for storing allergy information
 */
public class AllergyInteraction {
    //Drug causing the harmful allergy interaction
    private final Drug DRUG_INTERACTING;
    //DAM_ALRGN_GRP in relation RDAMGHC0 or DAM_ALRGN_XSENSE in relation RDAMXHC0
    private final int DRUG_ALLERGY_INTERACTION_CODE;
    //DAM_ALRGN_GRP_DESC in relation RDAMAGD1 or DAM_ALRGN_XSENSE_DESC in relation RDAMCSD1
    private final String DRUG_ALLERGY_INTERACTION_RESULT;

    private AllergyInteraction(AllergyInteractionBuilder builder) {
        DRUG_INTERACTING = builder.DRUG_INTERACTING;
        DRUG_ALLERGY_INTERACTION_CODE = builder.drugAllergyInteractionCode;
        DRUG_ALLERGY_INTERACTION_RESULT = builder.drugAllergyInteractionResult;
    }

    public Drug getInteractingDrug() {
        return DRUG_INTERACTING;
    }

    public int getDrugAllergyInteractionCode() {
        return DRUG_ALLERGY_INTERACTION_CODE;
    }

    public String getDrugAllergyInteractionResult() {
        return DRUG_ALLERGY_INTERACTION_RESULT;
    }


    public static class AllergyInteractionBuilder {
        private final Drug DRUG_INTERACTING;
        private int drugAllergyInteractionCode;
        private String drugAllergyInteractionResult;

        public AllergyInteractionBuilder(Drug drug) {
            DRUG_INTERACTING = drug;
        }

        public AllergyInteractionBuilder setDrugAllergyInteractionCode(int drugAllergyInteractionCode) {
            this.drugAllergyInteractionCode = drugAllergyInteractionCode;
            return this;
        }

        public AllergyInteractionBuilder setDrugAllergyInteractionResult(String drugAllergyInteractionResult) {
            this.drugAllergyInteractionResult = drugAllergyInteractionResult;
            return this;
        }

        public AllergyInteraction buildAllergyInteraction() {
            return new AllergyInteraction(this);
        }
    }
}