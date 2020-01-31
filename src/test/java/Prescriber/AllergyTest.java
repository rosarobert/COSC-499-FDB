package Prescriber;

import Info.Drug;
import Info.DrugInteraction;
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
        fdbPrescriber.destroyPrescriber();
    }

    @Test
    public void testSizeOfQueryAllergyInteractionsWithOnlyOneReturned() {
        List<Drug> queryDrugsResult = fdbPrescriber.queryDrugs("DUKORAL SUSPENSION");
        List<Integer> DAM_ALRG_CODES = new ArrayList<>();
        DAM_ALRG_CODES.add(518);
        List<DrugInteraction> queryAllergyResult = fdbPrescriber.queryAllergyInteractionsOfDrug(queryDrugsResult.get(0), DAM_ALRG_CODES);
        Assert.assertEquals(queryAllergyResult.size(), 1);
    }

    @Test
    public void testNameOfQueryingAllergyInteractionWithOnlyOneReturned() {
        List<Drug> queryDrugsResult = fdbPrescriber.queryDrugs("DUKORAL SUSPENSION");
        List<Integer> DAM_ALRG_CODES = new ArrayList<>();
        DAM_ALRG_CODES.add(518);
        List<DrugInteraction> queryAllergyResult = fdbPrescriber.queryAllergyInteractionsOfDrug(queryDrugsResult.get(0), DAM_ALRG_CODES);
        String allergyInteractionResult = queryAllergyResult.get(0).getInteractionDescription();
        Assert.assertEquals(allergyInteractionResult, "Patient is allergic to a ingredient in \"DUKORAL SUSPENSION\"");
    }

    @Test
    public void testSizeOfQueryAllergyInteractionsWithZeroReturned() {
        List<Drug> queryDrugsResult = fdbPrescriber.queryDrugs("DIGITALIS PURP GRN 3-30CH");
        List<Integer> DAM_ALRG_CODES = new ArrayList<>();
        DAM_ALRG_CODES.add(175);
        DAM_ALRG_CODES.add(294);
        List<DrugInteraction> queryAllergyResult = fdbPrescriber.queryAllergyInteractionsOfDrug(queryDrugsResult.get(0), DAM_ALRG_CODES);
        Assert.assertEquals(queryAllergyResult.size(), 0);
    }

    @Test
    public void testNameOfQueryingAllergyInteractionWithZeroReturned() {
        List<Drug> queryDrugsResult = fdbPrescriber.queryDrugs("DIGITALIS PURP GRN 3-30CH");
        List<Integer> DAM_ALRG_CODES = new ArrayList<>();
        DAM_ALRG_CODES.add(175);
        DAM_ALRG_CODES.add(294);
        List<DrugInteraction> queryAllergyResult = fdbPrescriber.queryAllergyInteractionsOfDrug(queryDrugsResult.get(0), DAM_ALRG_CODES);
        String allergyInteractionResult = null;
        if(!queryAllergyResult.isEmpty())
            allergyInteractionResult = queryAllergyResult.get(0).getInteractionDescription();
        Assert.assertEquals(allergyInteractionResult, null);
    }

    @Test
    public void testSizeOfQueryAllergyInteractionsWithManyReturned() {
        List<Drug> queryDrugsResult = fdbPrescriber.queryDrugs("ALERT TABLET");
        List<Integer> DAM_ALRG_CODES = new ArrayList<>();
        DAM_ALRG_CODES.add(140);
        DAM_ALRG_CODES.add(861);
        DAM_ALRG_CODES.add(900583);
        List<DrugInteraction> queryAllergyResult = fdbPrescriber.queryAllergyInteractionsOfDrug(queryDrugsResult.get(0), DAM_ALRG_CODES);
        Assert.assertEquals(queryAllergyResult.size(), 3);
    }

    @Test
    public void testNameOfQueryingAllergyInteractionWithManyReturned() {
        List<Drug> queryDrugsResult = fdbPrescriber.queryDrugs("ALERT TABLET");
        List<Integer> DAM_ALRG_CODES = new ArrayList<>();
        DAM_ALRG_CODES.add(140);
        DAM_ALRG_CODES.add(861);
        DAM_ALRG_CODES.add(900583);
        List<DrugInteraction> queryAllergyResult = fdbPrescriber.queryAllergyInteractionsOfDrug(queryDrugsResult.get(0), DAM_ALRG_CODES);
        String allergyInteractionResult = null;
        if(!queryAllergyResult.isEmpty())
            allergyInteractionResult = queryAllergyResult.get(0).getInteractionDescription();
        Assert.assertEquals(allergyInteractionResult, "Patient is allergic to a ingredient in \"ALERT TABLET\"");
    }

    @Test
    public void testSizeOfQueryAllergyInteractionsWithManyButOnlyOneReturned() {
        List<Drug> queryDrugsResult = fdbPrescriber.queryDrugs("ALERT TABLET");
        List<Integer> DAM_ALRG_CODES = new ArrayList<>();
        DAM_ALRG_CODES.add(140);
        DAM_ALRG_CODES.add(53);
        DAM_ALRG_CODES.add(900654);
        List<DrugInteraction> queryAllergyResult = fdbPrescriber.queryAllergyInteractionsOfDrug(queryDrugsResult.get(0), DAM_ALRG_CODES);
        Assert.assertEquals(queryAllergyResult.size(), 1);
    }

    @Test
    public void testNameOfQueryingAllergyInteractionWithManyButOnlyOneReturned() {
        List<Drug> queryDrugsResult = fdbPrescriber.queryDrugs("ALERT TABLET");
        List<Integer> DAM_ALRG_CODES = new ArrayList<>();
        DAM_ALRG_CODES.add(140);
        DAM_ALRG_CODES.add(53);
        DAM_ALRG_CODES.add(900654);
        List<DrugInteraction> queryAllergyResult = fdbPrescriber.queryAllergyInteractionsOfDrug(queryDrugsResult.get(0), DAM_ALRG_CODES);
        String allergyInteractionResult = null;
        if(!queryAllergyResult.isEmpty())
            allergyInteractionResult = queryAllergyResult.get(0).getInteractionDescription();
        Assert.assertEquals(allergyInteractionResult, "Patient is allergic to a ingredient in \"ALERT TABLET\"");
    }
}
