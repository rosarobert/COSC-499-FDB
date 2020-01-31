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
    public void testSizeOfQueryDrugToDrugInteractionsWithOnlyOneReturned() {
        List<Drug> queryNewDrugResult = fdbPrescriber.queryDrugs("PRENATAL/POSTPARTUM VIT/MIN");
        List<Drug> queryCurrentDrugResult = fdbPrescriber.queryDrugs("CARDIOQUIN 275MG TABLET");
        List<DrugToDrugInteraction> queryDrugToDrugInteractionResult = fdbPrescriber.queryDrugInteractionsWithOtherDrugs(queryNewDrugResult.get(0),queryCurrentDrugResult);
        Assert.assertEquals(queryDrugToDrugInteractionResult.size(), 1);
    }

    @Test
    public void testNameOfQueryingDrugToDrugInteractionWithOnlyOneReturned() {
        List<Drug> queryNewDrugResult = fdbPrescriber.queryDrugs("PRENATAL/POSTPARTUM VIT/MIN");
        List<Drug> queryCurrentDrugResult = fdbPrescriber.queryDrugs("CARDIOQUIN 275MG TABLET");
        List<DrugToDrugInteraction> queryDrugToDrugInteractionResult = fdbPrescriber.queryDrugInteractionsWithOtherDrugs(queryNewDrugResult.get(0), queryCurrentDrugResult);
        String drugToDrugClinicalEffectTextResult = queryDrugToDrugInteractionResult.get(0).getInteractionDescription();
        Assert.assertEquals(drugToDrugClinicalEffectTextResult, "Mixed effects of the latter drug");
    }

    @Test
    public void testSizeOfQueryDrugToDrugInteractionsWithZeroReturned() {
        List<Drug> queryNewDrugResult = fdbPrescriber.queryDrugs("ADDERALL XR 10 MG CAPSULE");
        List<Drug> queryCurrentDrugResult = fdbPrescriber.queryDrugs("TYLENOL WITH CODEINE ELIXIR");
        List<DrugToDrugInteraction> queryDrugToDrugInteractionResult = fdbPrescriber.queryDrugInteractionsWithOtherDrugs(queryNewDrugResult.get(0),queryCurrentDrugResult);
        Assert.assertEquals(queryDrugToDrugInteractionResult.size(), 0);
    }

    @Test
    public void testNameOfQueryingDrugToDrugInteractionWithZeroReturned() {
        List<Drug> queryNewDrugResult = fdbPrescriber.queryDrugs("ADDERALL XR 10 MG CAPSULE");
        List<Drug> queryCurrentDrugResult = fdbPrescriber.queryDrugs("TYLENOL WITH CODEINE ELIXIR");
        List<DrugToDrugInteraction> queryDrugToDrugInteractionResult = fdbPrescriber.queryDrugInteractionsWithOtherDrugs(queryNewDrugResult.get(0), queryCurrentDrugResult);
        String drugToDrugClinicalEffectTextResult = null;
        if(!queryDrugToDrugInteractionResult.isEmpty()){
            drugToDrugClinicalEffectTextResult = queryDrugToDrugInteractionResult.get(0).getInteractionDescription();
        }
        Assert.assertEquals(drugToDrugClinicalEffectTextResult, null);
    }

    @Test
    public void testSizeOfQueryDrugToDrugInteractionsWithManyReturned() {
        List<Drug> queryNewDrugResult = fdbPrescriber.queryDrugs("PRENATAL/POSTPARTUM VIT/MIN");
        List<Drug> queryCurrentDrugResult = fdbPrescriber.queryDrugs("APO-QUIN-G 325 MG TABLET");
        queryCurrentDrugResult.add(fdbPrescriber.queryDrugs("BIO BALANCED CALC/MAG TAB").get(0));
        List<DrugToDrugInteraction> queryDrugToDrugInteractionResult = fdbPrescriber.queryDrugInteractionsWithOtherDrugs(queryNewDrugResult.get(0),queryCurrentDrugResult);
        Assert.assertEquals(queryDrugToDrugInteractionResult.size(), 3);
    }

    @Test
    public void testNameOfQueryDrugToDrugInteractionsWithManyReturned() {
        List<Drug> queryNewDrugResult = fdbPrescriber.queryDrugs("PRENATAL/POSTPARTUM VIT/MIN");
        List<Drug> queryCurrentDrugResult = fdbPrescriber.queryDrugs("APO-QUIN-G 325 MG TABLET");
        queryCurrentDrugResult.add(fdbPrescriber.queryDrugs("BIO BALANCED CALC/MAG TAB").get(0));
        List<DrugToDrugInteraction> queryDrugToDrugInteractionResult = fdbPrescriber.queryDrugInteractionsWithOtherDrugs(queryNewDrugResult.get(0),queryCurrentDrugResult);
        String drugToDrugClinicalEffectTextResult = "";
        for(int i = 0; i < queryDrugToDrugInteractionResult.size(); i++){
            drugToDrugClinicalEffectTextResult += "(" + queryDrugToDrugInteractionResult.get(i).getDrugBeingPrescribed().getDisplayName() + " - ";
            drugToDrugClinicalEffectTextResult += queryDrugToDrugInteractionResult.get(i).getDrugAlreadyPrescribed().getDisplayName() + " : ";
            drugToDrugClinicalEffectTextResult += queryDrugToDrugInteractionResult.get(i).getInteractionDescription() + ")\n";
        }
        Assert.assertEquals(drugToDrugClinicalEffectTextResult,"(PRENATAL/POSTPARTUM VIT/MIN - BIO BALANCED CALC/MAG TAB : Decreased effect of the former drug)\n"
        +"(PRENATAL/POSTPARTUM VIT/MIN - BIO BALANCED CALC/MAG TAB : Mixed effects of the latter drug)\n"
        +"(PRENATAL/POSTPARTUM VIT/MIN - APO-QUIN-G 325 MG TABLET : Mixed effects of the latter drug)\n");
    }
}
