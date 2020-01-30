package Prescriber;

import Info.Drug;
import Info.DrugInteraction;
import Info.DrugToDrugInteraction;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class DrugInteractionTest {
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
        List<Drug> queryNewDrugResult = fdbPrescriber.queryDrugs("PRENATAL/POSTPARTUM VIT/MIN");
        List<Drug> queryCurrentDrugResult = fdbPrescriber.queryDrugs("CARDIOQUIN 275MG TABLET");
        List<DrugToDrugInteraction> queryDrugToDrugInteractionResult = fdbPrescriber.queryDrugInteractionsWithOtherDrugs(queryNewDrugResult.get(0),queryCurrentDrugResult);
        Assert.assertEquals(queryDrugToDrugInteractionResult.size(), 1);
    }

    @Test
    public void testNameOfQueryingAllergyInteractionWithOnlyOneReturned() {
        List<Drug> queryNewDrugResult = fdbPrescriber.queryDrugs("PRENATAL/POSTPARTUM VIT/MIN");
        List<Drug> queryCurrentDrugResult = fdbPrescriber.queryDrugs("CARDIOQUIN 275MG TABLET");
        List<DrugToDrugInteraction> queryDrugToDrugInteractionResult = fdbPrescriber.queryDrugInteractionsWithOtherDrugs(queryNewDrugResult.get(0), queryCurrentDrugResult);
        String drugToDrugClinicalEffectTextResult = queryDrugToDrugInteractionResult.get(0).getInteractionDescription();
        Assert.assertEquals(drugToDrugClinicalEffectTextResult, "Mixed effects of the latter drug");
    }
}
