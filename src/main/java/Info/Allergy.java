package Info;

public final class Allergy implements Displayable, Comparable<Allergy>{
    private final int ID;
    private final String NAME;
    
    private Allergy(int id, String name) {
        ID = id;
        NAME = name;
    }

    public static final Allergy createFdbAllergy(int damAllergenCode, String damAllergenName) {
        return new Allergy(damAllergenCode, damAllergenName);
    }

    public final int getId() {
        return ID;
    }

    public final String getName() {
        return NAME;
    }

    @Override
    public int compareTo(Allergy allergyToCompare) {
        return getId() - allergyToCompare.getId();
    }

    @Override
    public String getDisplayName() {
        return getName();
    }
}