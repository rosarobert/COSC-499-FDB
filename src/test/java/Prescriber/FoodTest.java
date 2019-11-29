package Prescriber;

import Info.Drug;
import Info.FoodInteraction;
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
        fdbPrescriber.destroyPrescriber();
    }

    @Test
    public void testSizeOfQueryFoodInteractionsWithOnlyOneReturned() {
        List<Drug> queryDrugsResult = fdbPrescriber.queryDrugs("ELIXOPHYLLIN K1 ELIXIR");
        List<FoodInteraction> queryFoodResult = fdbPrescriber.queryFoodInteractionsOfDrug(queryDrugsResult.get(0));
        Assert.assertEquals(queryFoodResult.size(), 1);
    }

    @Test
    public void testNameOfQueryingFoodInteractionWithOnlyOneReturned() {
        List<Drug> queryDrugsResult = fdbPrescriber.queryDrugs("ELIXOPHYLLIN K1 ELIXIR");
        List<FoodInteraction> queryFoodResult = fdbPrescriber.queryFoodInteractionsOfDrug(queryDrugsResult.get(0));
        String foodInteractionResult = queryFoodResult.get(0).getDrugFoodInteractionResult();
        Assert.assertEquals(foodInteractionResult, "FOOD MAY CAUSE VARIABLE ABSORPTION.");
    }

    @Test
    public void testSizeOfQueryFoodInteractionsWithNoneReturned() {
        List<Drug> queryDrugsResult = fdbPrescriber.queryDrugs("DIGITALIS PURP GRN 3-30CH");
        List<FoodInteraction> queryFoodResult = fdbPrescriber.queryFoodInteractionsOfDrug(queryDrugsResult.get(0));
        Assert.assertEquals(queryFoodResult.size(), 0);
    }

    @Test
    public void testNameOfQueryingFoodInteractionWithNoneReturned() {
        List<Drug> queryDrugsResult = fdbPrescriber.queryDrugs("DIGITALIS PURP GRN 3-30CH");
        List<FoodInteraction> queryFoodResult = fdbPrescriber.queryFoodInteractionsOfDrug(queryDrugsResult.get(0));
        Assert.assertTrue(queryFoodResult.isEmpty());
    }

}
