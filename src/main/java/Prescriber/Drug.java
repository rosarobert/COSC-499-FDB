package Prescriber;

/**
 * Container class for storing drug information
 */
public class Drug {
    private final int CLINICAL_FORMATION_ID;
    private final String BRAND_NAME;
    private final String LABEL_NAME;
    private final String CANADIAN_PRODUCT_NAME;
    private final String DESCRIPTION;

    private Drug(final DrugBuilder builder) {
        CLINICAL_FORMATION_ID = builder.clinicalFormulationId;
        BRAND_NAME = builder.brandName;
        LABEL_NAME = builder.labelName;
        CANADIAN_PRODUCT_NAME = builder.candadianProductName;
        DESCRIPTION = builder.description;
    }

    int getClinicalFormationId() {
        return CLINICAL_FORMATION_ID;
    }

    public String getBrandName() {
        return BRAND_NAME;
    }

    public String getLableName() {
        return LABEL_NAME;
    }

    public String getCanadianProductName() {
        return CANADIAN_PRODUCT_NAME;
    }

    public String getDescription() {
        return DESCRIPTION;
    }

    /**
     * Class for building a Drug instance
     * 
     * @implNote This builder class is implemented similar to the examples in
     *           Chapter 2 Item 2 of Effective Java, 3rd Edition, by Joshua Bloch
     */
    static class DrugBuilder {
        private int clinicalFormulationId;
        private String brandName;
        private String labelName;
        private String candadianProductName;
        private String description;

        DrugBuilder setClinicalFormulationId(int clinicalFormulationId) {
            this.clinicalFormulationId = clinicalFormulationId;
            return this;
        }

        DrugBuilder setBrandName(String brandName) {
            this.brandName = brandName;
            return this;
        }

        DrugBuilder setLabelName(String labelName) {
            this.labelName = labelName;
            return this;
        }

        DrugBuilder setCanadianProductName(String canadianProductName) {
            this.candadianProductName = canadianProductName;
            return this;
        }

        DrugBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        Drug build() {
            return new Drug(this);
        }
    }
}