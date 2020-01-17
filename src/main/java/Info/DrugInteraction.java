package Info;

public class DrugInteraction {
    //Drug being prescribed causing the interaction
    private final Drug DRUG_PRESCRIBED;
    //Drug patient already taking the interaction is occurring with
    private final Drug DRUG_INTERACTING_WITH;
    //DDI_DES in relation RADIMMA5
    private final String DRUG_TO_DRUG_INTERACTION_DESC;
    //ADI_EFFTXT in relation RADIMEF0
    private final String DRUG_TO_DRUG_CLINICAL_EFFECT_TEXT;

    private DrugInteraction(DrugInteractionBuilder builder) {
        DRUG_PRESCRIBED = builder.DRUG_PRESCRIBED;
        DRUG_INTERACTING_WITH = builder.drugInteractingWith;
        DRUG_TO_DRUG_INTERACTION_DESC = builder.drugToDrugInteractionDesc;
        DRUG_TO_DRUG_CLINICAL_EFFECT_TEXT = builder.drugToDrugClinicalEffectText;
    }

    public Drug getPrescribedDrug() {
        return DRUG_PRESCRIBED;
    }

    public Drug getDrugInteractingWith() {
        return DRUG_INTERACTING_WITH;
    }

    public String getDrugToDrugInteractionDesc() { return DRUG_TO_DRUG_INTERACTION_DESC; }

    public String getDrugToDrugClinicalEffectText() {
        return DRUG_TO_DRUG_CLINICAL_EFFECT_TEXT;
    }

    public static class DrugInteractionBuilder {
        private final Drug DRUG_PRESCRIBED;
        private Drug drugInteractingWith;
        private String drugToDrugInteractionDesc;
        private String drugToDrugClinicalEffectText;

        public DrugInteractionBuilder(Drug drug) {
            DRUG_PRESCRIBED = drug;
        }

        public DrugInteractionBuilder setDrugInteractingWith(Drug drug) {
            this.drugInteractingWith = drug;
            return this;
        }

        public DrugInteractionBuilder setDrugToDrugInteractionDesc(String drugToDrugInteractionDesc) {
            this.drugToDrugInteractionDesc = drugToDrugInteractionDesc;
            return this;
        }

        public DrugInteractionBuilder setDrugToDrugClinicalEffectText(String drugToDrugClinicalEffectText) {
            this.drugToDrugClinicalEffectText = drugToDrugClinicalEffectText;
            return this;
        }

        public DrugInteraction buildDrugInteraction() {
            return new DrugInteraction(this);
        }
    }
}