package Info;

import java.util.SortedSet;
import java.util.TreeSet;

public abstract class Patient {
    SortedSet<Drug> DRUGS_PRESCRIBED = new TreeSet<>();
    SortedSet<Allergy> PATIENT_ALLERGIES = new TreeSet<>();

    public final boolean prescribeDrug(Drug drugPrescribed) {
        return DRUGS_PRESCRIBED.add(drugPrescribed) && prescribeDrugInDatabase(drugPrescribed);
    }

    public final boolean addAllergy(Allergy allergy) {
        return PATIENT_ALLERGIES.add(allergy) && addPatientAllergyToDatabase(allergy);
    }

    public SortedSet<Drug> getDrugsPrescribed() {
        return DRUGS_PRESCRIBED;
    }

    public SortedSet<Allergy> getPatientAllergies() {
        return PATIENT_ALLERGIES;
    }
    
    abstract boolean prescribeDrugInDatabase(Drug drugPrescribed);

    abstract boolean addPatientAllergyToDatabase(Allergy allergy);

}