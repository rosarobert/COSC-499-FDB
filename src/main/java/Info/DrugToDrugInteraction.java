package Info;

public class DrugToDrugInteraction extends DrugInteraction {
    private final Drug DRUG_PRESENT;

    DrugToDrugInteraction(Drug drugPrescribed, Drug drugPresent, String description) {
        super(drugPrescribed, InteractionType.DRUG_TO_DRUG, description);
        DRUG_PRESENT = drugPresent;
    }

    public static DrugToDrugInteraction createFdbDrugToDrugInteraction(Drug drugPrescribed, Drug drugPresent, String description) {
        return new DrugToDrugInteraction(drugPrescribed, drugPresent, description);
    }

    public Drug getDrugPresent()  {
        return DRUG_PRESENT;
    }
}