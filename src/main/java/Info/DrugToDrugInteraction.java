package Info;

/**
 * A container class for storing information about harmful interactions between
 * a drug already prescribed (DRUG_ALREADY_PRESCRIBED)  by a {@link Prescriber}
 * and a drug attempting to be prescribed by a Prescriber .
 * 
 * Every DrugToDrug interaction involves a drug already prescribed
 * (DRUG_PRESENT) and a drug begin added (DRUG_BEING_PRESCRIBED in superclass).
 * 
 * The reason for this is to model something that is being prescribed to a
 * patient a drug; the patient will already have a set of drugs prescribed and a
 * doctor may want to add a new drug. This class is for storing information
 * about harmful interactions that may occur when prescribing a new drug
 */
public class DrugToDrugInteraction extends DrugInteraction {
    private final Drug DRUG_ALREADY_PRESCRIBED;

    /**
     * Creates a object that described a harmful interaction between two drugs.
     * 
     * @param drugBeingPrescribed drug being prescribed that causes a harmful
     *                            reaction with a drug already prescribed
     * @param drugPresent         drug that causes a harmful reaction with the drug begin prescribed
     * @param description
     */
    private DrugToDrugInteraction(Drug drugBeingPrescribed, Drug drugAlreadyPrescribed, String description) {
        super(drugBeingPrescribed, InteractionType.DRUG_TO_DRUG, description);
        DRUG_ALREADY_PRESCRIBED = drugAlreadyPrescribed;
    }

    public static DrugToDrugInteraction createFdbDrugToDrugInteraction(Drug drugPrescribed, Drug drugPresent, String description) {
        return new DrugToDrugInteraction(drugPrescribed, drugPresent, description);
    }

    public Drug getDrugAlreadyPrescribed() {
        return DRUG_ALREADY_PRESCRIBED;
    }
}