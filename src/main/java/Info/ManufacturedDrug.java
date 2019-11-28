package Info;

/**
 * Container class for storing manufactured drug information
 */
public class ManufacturedDrug {
    private final int GCN_SENO;
    private final int HICL_SEQNO;
    private final String DIN;
    private final String MFG;
    private final String IADDDTE;
    private final String IOBSDTE;
    private final String LN;


    private ManufacturedDrug(ManufactoredDrugBuilder builder) {
        GCN_SENO = builder.gcnSeqno;
        LN = builder.ln;
        DIN = builder.din;
        HICL_SEQNO = builder.hiclSeqno;
        MFG = builder.mfg;
        IADDDTE = builder.iadddte;
        IOBSDTE = builder.iobsdte;
    }

    public int getGCN_SENO() {
        return GCN_SENO;
    }

    public int getHICL_SEQNO() {
        return HICL_SEQNO;
    }

    public String getDIN() {
        return DIN;
    }

    public String getMFG() {
        return MFG;
    }

    public String getIADDDTE() {
        return IADDDTE;
    }

    public String getIOBSDTE() {
        return IOBSDTE;
    }

    public String getLN() {
        return LN;
    }

    /**
     * Class for building a Manufactured Drug instance
     *
     * @implNote This builder class is implemented similar to the examples in Chapter 2 Item 2 of Effective Java, 3rd
     * Edition, by Joshua Bloch
     */
    public static class ManufactoredDrugBuilder {
        private int gcnSeqno;
        private int hiclSeqno;
        private String din;
        private String mfg;
        private String iadddte;
        private String iobsdte;
        private String ln;

        public ManufactoredDrugBuilder setClinicalFormulationId(int gcnSeqno) {
            this.gcnSeqno = gcnSeqno;
            return this;
        }

        public ManufactoredDrugBuilder setLabelName(String ln) {
            this.ln = ln;
            return this;
        }

        public ManufactoredDrugBuilder setIngredientListIdentifier(int hiclSeqno) {
            this.hiclSeqno = hiclSeqno;
            return this;
        }

        public ManufactoredDrugBuilder setCanadianDrugIdentificationNumber(String din) {
            this.din = din;
            return this;
        }

        public ManufactoredDrugBuilder setManufacturerName(String mfg) {
            this.mfg = mfg;
            return this;
        }

        public ManufactoredDrugBuilder setIddfAddDate(String iadddte) {
            this.iadddte = iadddte;
            return this;
        }

        public ManufactoredDrugBuilder setIddsObsoleteDate(String iobsdte) {
            this.iobsdte = iobsdte;
            return this;
        }

        public ManufacturedDrug buildDrug() {
            if (gcnSeqno != 0 && hiclSeqno != 0 && din != null && mfg != null & ln != null)
                return new ManufacturedDrug(this);
            else
                throw new IllegalStateException("Some of the drug import attributes are null or 0");
        }
    }
}