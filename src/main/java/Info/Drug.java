package Info;

public final class Drug implements Comparable<Drug> {

    private final int ID;
    private final String DISPLAY_NAME;

    private Drug(int id, String displayName) {
        ID = id;
        DISPLAY_NAME = displayName;
    }

    public static final Drug createFdbDrug(int din, String displayName) {
        return new Drug(din, displayName);
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

    @Override
    public int compareTo(Drug drugToCompare) {
        return getDisplayName().compareTo(drugToCompare.getDisplayName());
    }
}