package Info;

public class ManufacturedDrug extends Drug {
    //DIN in relation RICAIDC1
    private final String CANADIAN_DRUG_ID;
    //MFG in relation RLBLRCA1
    private final String MANUFACTURER_NAME;

    private ManufacturedDrug(ManufacturedDrugBuilder drugBuilder) {
        super(drugBuilder);
        CANADIAN_DRUG_ID = drugBuilder.canadianDrugId;
        MANUFACTURER_NAME = drugBuilder.manufacturerName;
    }


    public static class ManufacturedDrugBuilder extends DrugBuilder<ManufacturedDrugBuilder> {
        private String canadianDrugId;
        private String manufacturerName;

        public ManufacturedDrugBuilder setCanadianDrugId(String canadianDrugId) {
            this.canadianDrugId = canadianDrugId;
            return self();
        }

        public ManufacturedDrugBuilder setManufacturerName(String manufacturerName) {
            this.manufacturerName = manufacturerName;
            return self();
        }

        ManufacturedDrugBuilder self() {
            return this;
        }

        @Override
        public Drug buildDrug() {
            return new ManufacturedDrug(this);
        }
    }

}
