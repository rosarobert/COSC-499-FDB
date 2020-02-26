package Prescriber;

import Info.Allergy;
import Info.Drug;
import Info.DrugInteraction;
import Info.Patient;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class AllergyTest {
    private FdbPrescriber fdbPrescriber;

    //Requests a connection to the database
    @BeforeClass
    public void init() throws Exception {
        fdbPrescriber = new FdbPrescriber();
    }

    //closes connection
    @AfterClass
    public void end() throws Exception {
        fdbPrescriber.closePrescriber();
    }

    @Test
    public void testSizeOfQueryAllergyInteractionsWithOnlyOneReturned() {
        List<Drug> queryDrugsResult = fdbPrescriber.queryDrugs("DUKORAL SUSPENSION");
        Allergy allergy = Allergy.createFdbAllergy(518,"Gram Negative Bacilli (Non-Enteric) Vaccines");
        Patient patient = new Patient();
        patient.addAllergy(allergy);
        List<DrugInteraction> queryAllergyResult = fdbPrescriber.queryAllergyInteractionsOfDrug(queryDrugsResult.get(0), patient);
        Assert.assertEquals(queryAllergyResult.size(), 1);
    }

    @Test
    public void testNameOfQueryingAllergyInteractionWithOnlyOneReturned() {
        List<Drug> queryDrugsResult = fdbPrescriber.queryDrugs("DUKORAL SUSPENSION");
        Allergy allergy = Allergy.createFdbAllergy(518,"Gram Negative Bacilli (Non-Enteric) Vaccines");
        Patient patient = new Patient();
        patient.addAllergy(allergy);
        List<DrugInteraction> queryAllergyResult = fdbPrescriber.queryAllergyInteractionsOfDrug(queryDrugsResult.get(0), patient);
        String allergyInteractionResult = queryAllergyResult.get(0).getInteractionDescription();
        Assert.assertEquals(allergyInteractionResult, "Patient is allergic to Gram Negative Bacilli (Non-Enteric) Vaccines which is an  ingredient in \"DUKORAL SUSPENSION\"");
    }

    @Test
    public void testSizeOfQueryAllergyInteractionsWithZeroReturned() {
        List<Drug> queryDrugsResult = fdbPrescriber.queryDrugs("DIGITALIS PURP GRN 3-30CH");
        List<Allergy> allergies = new ArrayList<>();
        allergies.add(Allergy.createFdbAllergy(175,"Iodine and Iodide Containing Products"));
        allergies.add(Allergy.createFdbAllergy(294,"Beta-Adrenergic Agents"));
        Patient patient = new Patient();
        patient.addAllergy(allergies.get(0));
        patient.addAllergy(allergies.get(1));
        List<DrugInteraction> queryAllergyResult = fdbPrescriber.queryAllergyInteractionsOfDrug(queryDrugsResult.get(0), patient);
        Assert.assertEquals(queryAllergyResult.size(), 0);
    }

    @Test
    public void testNameOfQueryingAllergyInteractionWithZeroReturned() {
        List<Drug> queryDrugsResult = fdbPrescriber.queryDrugs("DIGITALIS PURP GRN 3-30CH");
        List<Allergy> allergies = new ArrayList<>();
        allergies.add(Allergy.createFdbAllergy(175,"Iodine and Iodide Containing Products"));
        allergies.add(Allergy.createFdbAllergy(294, "Beta-Adrenergic Agents"));
        Patient patient = new Patient();
        patient.addAllergy(allergies.get(0));
        patient.addAllergy(allergies.get(1));
        List<DrugInteraction> queryAllergyResult = fdbPrescriber.queryAllergyInteractionsOfDrug(queryDrugsResult.get(0), patient);
        String allergyInteractionResult = null;
        if(!queryAllergyResult.isEmpty())
            allergyInteractionResult = queryAllergyResult.get(0).getInteractionDescription();
        Assert.assertEquals(allergyInteractionResult, null);
    }

    @Test
    public void testSizeOfQueryAllergyInteractionsWithManyReturned() {
        List<Drug> queryDrugsResult = fdbPrescriber.queryDrugs("ALERT TABLET");
        List<Allergy> allergies = new ArrayList<>();
        allergies.add(Allergy.createFdbAllergy(140,"Xanthines"));
        allergies.add(Allergy.createFdbAllergy(861, "Coffee"));
        allergies.add(Allergy.createFdbAllergy(900583,"Guarana"));
        Patient patient = new Patient();
        patient.addAllergy(allergies.get(0));
        patient.addAllergy(allergies.get(1));
        patient.addAllergy(allergies.get(2));
        List<DrugInteraction> queryAllergyResult = fdbPrescriber.queryAllergyInteractionsOfDrug(queryDrugsResult.get(0), patient);
        Assert.assertEquals(queryAllergyResult.size(), 3);
    }

    @Test
    public void testNameOfQueryingAllergyInteractionWithManyReturned() {
        List<Drug> queryDrugsResult = fdbPrescriber.queryDrugs("ALERT TABLET");
        List<Allergy> allergies = new ArrayList<>();
        allergies.add(Allergy.createFdbAllergy(140,"Xanthines"));
        allergies.add(Allergy.createFdbAllergy(861, "Coffee"));
        allergies.add(Allergy.createFdbAllergy(900583,"Guarana"));
        Patient patient = new Patient();
        patient.addAllergy(allergies.get(0));
        patient.addAllergy(allergies.get(1));
        patient.addAllergy(allergies.get(2));
        List<DrugInteraction> queryAllergyResult = fdbPrescriber.queryAllergyInteractionsOfDrug(queryDrugsResult.get(0), patient);
        String allergyInteractionResult = null;
        if(!queryAllergyResult.isEmpty())
            allergyInteractionResult = queryAllergyResult.get(0).getInteractionDescription();
        Assert.assertEquals(allergyInteractionResult, "Patient is allergic to Xanthines which is an  ingredient in \"ALERT TABLET\"");
    }

    @Test
    public void testSizeOfQueryAllergyInteractionsWithManyButOnlyOneReturned() {
        List<Drug> queryDrugsResult = fdbPrescriber.queryDrugs("ALERT TABLET");
        List<Allergy> allergies = new ArrayList<>();
        allergies.add(Allergy.createFdbAllergy(140,"Xanthines"));
        allergies.add(Allergy.createFdbAllergy(53,"Haemophilus influenzae Vaccines"));
        allergies.add(Allergy.createFdbAllergy(900654,null));
        Patient patient = new Patient();
        patient.addAllergy(allergies.get(0));
        patient.addAllergy(allergies.get(1));
        patient.addAllergy(allergies.get(2));
        List<DrugInteraction> queryAllergyResult = fdbPrescriber.queryAllergyInteractionsOfDrug(queryDrugsResult.get(0), patient);
        Assert.assertEquals(queryAllergyResult.size(), 1);
    }

    @Test
    public void testNameOfQueryingAllergyInteractionWithManyButOnlyOneReturned() {
        List<Drug> queryDrugsResult = fdbPrescriber.queryDrugs("ALERT TABLET");
        List<Allergy> allergies = new ArrayList<>();
        allergies.add(Allergy.createFdbAllergy(140,"Xanthines"));
        allergies.add(Allergy.createFdbAllergy(53,"Haemophilus influenzae Vaccines"));
        allergies.add(Allergy.createFdbAllergy(900654,null));
        Patient patient = new Patient();
        patient.addAllergy(allergies.get(0));
        patient.addAllergy(allergies.get(1));
        patient.addAllergy(allergies.get(2));
        List<DrugInteraction> queryAllergyResult = fdbPrescriber.queryAllergyInteractionsOfDrug(queryDrugsResult.get(0), patient);
        String allergyInteractionResult = null;
        if(!queryAllergyResult.isEmpty())
            allergyInteractionResult = queryAllergyResult.get(0).getInteractionDescription();
        Assert.assertEquals(allergyInteractionResult, "Patient is allergic to Xanthines which is an  ingredient in \"ALERT TABLET\"");
    }
}
