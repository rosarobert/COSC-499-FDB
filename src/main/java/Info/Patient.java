package Info;

import java.util.SortedSet;
import java.util.TreeSet;

public class Patient {
    String NAME;
    SortedSet<Drug> DRUGS_PRESCRIBED = new TreeSet<>();
    SortedSet<Allergy> PATIENT_ALLERGIES = new TreeSet<>();

    public final String setName(String name){ return NAME = name;}

    public final boolean addDrug(Drug drug) { return DRUGS_PRESCRIBED.add(drug); }

    public final boolean addAllergy(Allergy allergy) { return PATIENT_ALLERGIES.add(allergy); }

    public SortedSet<Drug> getDrugsPrescribed() {
        return DRUGS_PRESCRIBED;
    }

    public SortedSet<Allergy> getPatientAllergies() {
        return PATIENT_ALLERGIES;
    }

    public final String getName(){ return NAME;}

}