package Info;

public final class Drug implements Displayable, Comparable<Drug> {

    private final int ID;
    private final int INGREDIENTS_ID;
    private final int GCN_SEQNO;
    private final String DISPLAY_NAME;

    private Drug(int id, int ingredientsId, int gcnSeqno, String displayName) {
        ID = id;
        INGREDIENTS_ID = ingredientsId;
        GCN_SEQNO = gcnSeqno;
        DISPLAY_NAME = displayName;
    }

    public static final Drug createFdbDrug(int din, int hicl, int gcn, String displayName) {
        return new Drug(din, hicl, gcn, displayName);
    }

    public final int getId() {
        return ID;
    }

    /**
     * Retrieves the name for this drug that should be displayed to the user
     */
    public final String getDisplayName() {
        return DISPLAY_NAME;
    }

    /**
     * Retrieves the ingredients list identifier, to find contents of drug
     */
    public final int getIngredientIdentifier() {
        return INGREDIENTS_ID;
    }

    /**
     * Retrieves the gcn, to find food interactions
     */
    public final int getGcnSeqno() {
        return GCN_SEQNO;
    }

    @Override
    public int compareTo(Drug drugToCompare) {
        return getId() - drugToCompare.getId();
    }

    public final String toString() {
        return new StringBuilder().append("Drug -> ")
                                  .append("DIN: " + getId())
                                  .append(" INGREDIENTS_ID: " + getIngredientIdentifier())
                                  .append(" GCN_SEQNO: " + getGcnSeqno())
                                  .append(" DISPLAY_NAME: " + getDisplayName())
                                  .toString();

    }
}