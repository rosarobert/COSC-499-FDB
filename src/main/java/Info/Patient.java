package Info;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class Patient implements Displayable{
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
    public void removeDrug(int input){
        List<Drug> list = new ArrayList<>();

        // push each element in the set into the list
        for (Drug a : getDrugsPrescribed())
            list.add(a);
        getDrugsPrescribed().remove(list.get(input));
    }
    public void removeAllergy(int input){
        List<Allergy> list = new ArrayList<>();

        // push each element in the set into the list
        for (Allergy a : getPatientAllergies())
            list.add(a);
        getPatientAllergies().remove(list.get(input));
    }


    public final String getName(){ return NAME;}

    @Override
    public String getDisplayName() {
        return getName();
    }
}