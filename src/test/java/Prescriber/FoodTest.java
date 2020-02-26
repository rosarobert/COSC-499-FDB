package Prescriber;

import Info.Drug;
import Info.DrugInteraction;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class FoodTest {

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
    public void testSizeOfQueryFoodInteractionsWithOnlyOneReturned() {
        List<Drug> queryDrugsResult = fdbPrescriber.queryDrugs("ELIXOPHYLLIN K1 ELIXIR");
        List<DrugInteraction> queryFoodResult = fdbPrescriber.queryFoodInteractionsOfDrug(queryDrugsResult.get(0));
        Assert.assertEquals(queryFoodResult.size(), 1);
    }

    @Test
    public void testNameOfQueryingFoodInteractionWithOnlyOneReturned() {
        List<Drug> queryDrugsResult = fdbPrescriber.queryDrugs("ELIXOPHYLLIN K1 ELIXIR");
        List<DrugInteraction> queryFoodResult = fdbPrescriber.queryFoodInteractionsOfDrug(queryDrugsResult.get(0));
        String foodInteractionResult = queryFoodResult.get(0).getInteractionDescription();
        Assert.assertEquals(foodInteractionResult, "FOOD MAY CAUSE VARIABLE ABSORPTION.");
    }

    @Test
    public void testSizeOfQueryFoodInteractionsWithNoneReturned() {
        List<Drug> queryDrugsResult = fdbPrescriber.queryDrugs("DIGITALIS PURP GRN 3-30CH");
        List<DrugInteraction> queryFoodResult = fdbPrescriber.queryFoodInteractionsOfDrug(queryDrugsResult.get(0));
        Assert.assertEquals(queryFoodResult.size(), 0);
    }

    @Test
    public void testNameOfQueryingFoodInteractionWithNoneReturned() {
        List<Drug> queryDrugsResult = fdbPrescriber.queryDrugs("DIGITALIS PURP GRN 3-30CH");
        List<DrugInteraction> queryFoodResult = fdbPrescriber.queryFoodInteractionsOfDrug(queryDrugsResult.get(0));
        Assert.assertTrue(queryFoodResult.isEmpty());
    }

    @Test
    public void testSizeOfQueryFoodInteractionsWithManyReturned() {
        List<Drug> queryDrugsResult = fdbPrescriber.queryDrugs("SYNTHROID 25 MCG TABLET");
        List<DrugInteraction> queryFoodResult = fdbPrescriber.queryFoodInteractionsOfDrug(queryDrugsResult.get(0));
        Assert.assertEquals(queryFoodResult.size(), 3);
    }

    @Test
    public void testNameOfQueryingFoodInteractionWithManyReturned() {
        List<Drug> queryDrugsResult = fdbPrescriber.queryDrugs("SYNTHROID 25 MCG TABLET");
        List<DrugInteraction> queryFoodResult = fdbPrescriber.queryFoodInteractionsOfDrug(queryDrugsResult.get(0));
        String foodInteractionResult = queryFoodResult.get(0).getInteractionDescription();
        for(int i = 1; i<queryFoodResult.size();i++)
            foodInteractionResult += ", " + queryFoodResult.get(i).getInteractionDescription();
        Assert.assertEquals(foodInteractionResult,
                "SOY DECREASES ABSORPTION OF LEVOTHYROXINE., FIBER DECREASES ABSORPTION OF LEVOTHYROXINE, COFFEE DECR ABSORPTION OF LEVOTHYROXINE TABS.");
    }
}
