package Info;

import java.sql.Date;

public abstract class Drug {
    //GCN_SEQNO in relation RICAIDC1
    private final int CLINICAL_FORMULATION_ID;

    //HICL_SEQNO in relation RGCNSEQ4
    private final int INGREDIENT_LIST_IDENTIFIER;
    //LN in relation RICAIDC1
    private final String DISPLAY_NAME;
    //IADDDTE in relation RICAIDC1
    private final Date ADD_DATE;
    //IOBSDTE in relation RICAIDC1
    private final Date OBSELETE_DATE;

     Drug(DrugBuilder drugBuilder) {
        CLINICAL_FORMULATION_ID = drugBuilder.clinicalFormulationId;
        INGREDIENT_LIST_IDENTIFIER = drugBuilder.ingredientListIdentifier;
        DISPLAY_NAME = drugBuilder.displayName;
        ADD_DATE = drugBuilder.addDate;
        OBSELETE_DATE = drugBuilder.obseleteDate;
    }

    public int getClinicalFormulationId() {
         return CLINICAL_FORMULATION_ID;
    }

    public int getIngredientListIdentifier() {
         return INGREDIENT_LIST_IDENTIFIER;
    }

    public String getDisplayName() {
         return DISPLAY_NAME;
    }

    public Date getAddDate() {
         return ADD_DATE;
    }

    public Date getObseleteDate() {
         return OBSELETE_DATE;
    }


    /**
     * Class for building a Drug instance
     * <p>
     * Note: This builder class is implemented similar to the generic example in Chapter 2 Item 2 of Effective Java, 3rd
     * Edition, by Joshua Bloch
     */
    public abstract static class DrugBuilder<T extends DrugBuilder<T>> {

        private int clinicalFormulationId;
        private int ingredientListIdentifier;
        private String displayName;
        private Date addDate;
        private Date obseleteDate;

        public T setClinicalFormulationId(int clinicalFormulationId) {
            this.clinicalFormulationId = clinicalFormulationId;
            return self();
        }


        public T setIngredientListIdentifier(int ingredientListIdentifier) {
            this.ingredientListIdentifier = ingredientListIdentifier;
            return self();
        }

        public T setDisplayName(String displayName) {
            this.displayName = displayName.trim();
            return self();
        }

        public T setAddDate(Date addDate) {
            this.addDate = addDate;
            return self();
        }

        public T setObseleteDate(Date obseleteDate) {
            this.obseleteDate = obseleteDate;
            return self();
        }

        abstract T self();

        abstract Drug buildDrug();
    }

}
