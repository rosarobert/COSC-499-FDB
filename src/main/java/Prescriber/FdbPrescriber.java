package Prescriber;


import Info.Allergy;
import Info.Drug;
import Info.Food;
import Info.Patient;

import java.util.List;

/**
 * A implementation of {@link Prescriber} using the FDB database
 */
class FdbPrescriber implements Prescriber {


    @Override
    public List<Drug> queryDrugs(String prefix) {
        return null;
    }

    @Override
    public List<Drug> queryDrugInteractionsWithOtherDrugs(Drug drug) {
        return null;
    }

    @Override
    public List<Food> queryFoodInteractionsOfDrug(Drug drug) {
        return null;
    }

    @Override
    public List<Allergy> queryAllergyInteractionsOfDrug(Drug drug, Patient patient) {
        return null;
    }
}
