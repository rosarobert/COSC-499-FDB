package Prescriber;

import Info.Drug;
import Info.DrugInteraction;
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
        Drug[] testtt = {queryCurrentDrugResult.get(0)};
        List<DrugInteraction> queryDrugToDrugInteractionResult = fdbPrescriber.queryDrugInteractionsWithOtherDrugs(queryNewDrugResult.get(0),testtt);
        Assert.assertEquals(queryDrugToDrugInteractionResult.size(), 1);
    }

    @Test
    public void testNameOfQueryingAllergyInteractionWithOnlyOneReturned() {
        List<Drug> queryNewDrugResult = fdbPrescriber.queryDrugs("PRENATAL/POSTPARTUM VIT/MIN");
        List<Drug> queryCurrentDrugResult = fdbPrescriber.queryDrugs("CARDIOQUIN 275MG TABLET");
        Drug[] testtt = {queryCurrentDrugResult.get(0)};
        List<DrugInteraction> queryDrugToDrugInteractionResult = fdbPrescriber.queryDrugInteractionsWithOtherDrugs(queryNewDrugResult.get(0), testtt);
        String drugToDrugInteractionDescResult = queryDrugToDrugInteractionResult.get(0).getDrugToDrugInteractionDesc();
        String drugToDrugClinicalEffectTextResult = queryDrugToDrugInteractionResult.get(0).getDrugToDrugClinicalEffectText();
        Assert.assertEquals(drugToDrugInteractionDescResult, "ALUMINUM AND MAGNESIUM ANTACIDS/QUINIDINE; QUININE");
        Assert.assertEquals(drugToDrugClinicalEffectTextResult, "Mixed effects of the latter drug");
    }
}
