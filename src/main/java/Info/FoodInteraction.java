package Info;

/**
 * Container class for storing food information
 */
public class FoodInteraction {
    //Drug causing the harmful food interaction
    private final Drug DRUG_INTERACTING;
    //FDCDE in relation RDFIMFC0
    private final int DRUG_FOOD_INTERACTION_CODE;
    //DNAME in relation RDIMMA0
    private final String DRUG_FOOD_INTERACTION_NAME;
    //RESULT in relation RDIMMA0
    private final String DRUG_FOOD_INTERACTION_RESULT;

    private FoodInteraction(FoodInteractionBuilder builder) {
        DRUG_INTERACTING = builder.DRUG_ITERACTING;
        DRUG_FOOD_INTERACTION_CODE = builder.drugFoodInteractionCode;
        DRUG_FOOD_INTERACTION_NAME = builder.drugFoodInteractionName;
        DRUG_FOOD_INTERACTION_RESULT = builder.drugFoodInteractionResult;
    }

    public Drug getInteractingDrug() {
        return DRUG_INTERACTING;
    }

    public String getDrugFoodInteractionName() {
        return DRUG_FOOD_INTERACTION_NAME;
    }

    public String getDrugFoodInteractionResult() {
        return DRUG_FOOD_INTERACTION_RESULT;
    }


    public static class FoodInteractionBuilder {
        private final Drug DRUG_ITERACTING;
        private int drugFoodInteractionCode;
        private String drugFoodInteractionName;
        private String drugFoodInteractionResult;

        public FoodInteractionBuilder(Drug drug) {
            DRUG_ITERACTING = drug;
        }

        public FoodInteractionBuilder setDrugFoodInteractionCode(int drugFoodInteractionCode) {
            this.drugFoodInteractionCode = drugFoodInteractionCode;
            return this;
        }

        public FoodInteractionBuilder setDrugFoodInteractionName(String drugFoodInteractionName) {
            this.drugFoodInteractionName = drugFoodInteractionName;
            return this;
        }

        public FoodInteractionBuilder setDrugFoodInteractionResult(String drugFoodInteractionResult) {
            this.drugFoodInteractionResult = drugFoodInteractionResult;
            return this;
        }

        public FoodInteraction buildFoodInteraction() {
            return new FoodInteraction(this);
        }
    }
}