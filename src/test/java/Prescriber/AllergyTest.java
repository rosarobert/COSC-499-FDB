package Prescriber;

import Info.Drug;
import Info.AllergyInteraction;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

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
        List<AllergyInteraction> queryAllergyResult = fdbPrescriber.queryAllergyInteractionsOfDrug(queryDrugsResult.get(0));
        Assert.assertEquals(queryAllergyResult.size(), 1);
    }

    @Test
    public void testNameOfQueryingAllergyInteractionWithOnlyOneReturned() {
        List<Drug> queryDrugsResult = fdbPrescriber.queryDrugs("DUKORAL SUSPENSION");
        List<AllergyInteraction> queryAllergyResult = fdbPrescriber.queryAllergyInteractionsOfDrug(queryDrugsResult.get(0));
        String allergyInteractionResult = queryAllergyResult.get(0).getDrugAllergyInteractionResult();
        Assert.assertEquals(allergyInteractionResult, "Gram Negative Bacilli (Non-Enteric) Vaccines");
    }

}
