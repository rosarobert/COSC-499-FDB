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
}
