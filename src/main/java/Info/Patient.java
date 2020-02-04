package Info;


import java.util.NavigableSet;
import java.util.TreeSet;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;


public abstract class Patient<T, U> {
    //For QHR only
    private final int PATIENT_ID;
    private final String NAME_OF_PATIENT;

    // To be able to get the name of a drug based on its id or vice versa
    private final BiMap<T, String> ID_TO_DRUG_PRESCRIBED;

    // To be able to get the name of an allergy based on its id or vice versa
    private final BiMap<U, String> ID_TO_PATIENT_ALLERGY;

    // To make range queries on patient drugs easy
    private final NavigableSet<String> DRUGS_PRESCRIBED;

    // To make range queries on patient allergies easy
    private final NavigableSet<String> PATIENT_ALLERGIES;

    private Patient(int patientId, String nameOfPatient) {
        PATIENT_ID = patientId;
        NAME_OF_PATIENT = nameOfPatient;
        ID_TO_DRUG_PRESCRIBED = HashBiMap.create();
        ID_TO_PATIENT_ALLERGY = HashBiMap.create();
        DRUGS_PRESCRIBED = new TreeSet<>();
        PATIENT_ALLERGIES = new TreeSet<>();

    }

    public boolean addAllergy(U id, String name) {
        ID_TO_PATIENT_ALLERGY.put(id, name);
        PATIENT_ALLERGIES.add(name);
        return addAllergyToPatientInDatabase(id);
    }

    /**
     * Could add a Prescriber object to check for food allergies to or add Patient object to Presciber interface
     */
    public boolean prescribeDrug(T id, String name) {
        ID_TO_DRUG_PRESCRIBED.put(id, name);
        DRUGS_PRESCRIBED.add(name);
        return addDrugToPatientInDatabase(id);
    }

    /**
     * Updates patient database after prescribing a drug to them
     * 
     * @param id
     * @return
     */
    abstract boolean addDrugToPatientInDatabase(T id);

    abstract boolean addAllergyToPatientInDatabase(U id);

}