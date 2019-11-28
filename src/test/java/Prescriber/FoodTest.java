package Prescriber;

import Info.Drug;
import Info.FoodInteraction;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.List;

public class FoodTest {

    private FdbPrescriber fdbPrescriber;
    private int size = 0;
    private String foodInteractionResult = null;

    //Requests a connection to the database
    @BeforeClass
    public void init() throws Exception {
        fdbPrescriber = new FdbPrescriber();
        fdbPrescriber.initializePrescriber();
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
        if(!queryFoodResult.isEmpty()) {
            size = queryFoodResult.size();
        }
        Assert.assertEquals(size, 1);
    }

    @Test
    public void testNameOfQueryingFoodInteractionWithOnlyOneReturned() {
        List<Drug> queryDrugsResult = fdbPrescriber.queryDrugs("ELIXOPHYLLIN K1 ELIXIR");
        List<FoodInteraction> queryFoodResult = fdbPrescriber.queryFoodInteractionsOfDrug(queryDrugsResult.get(0));
        if(!queryFoodResult.isEmpty()) {
            foodInteractionResult = queryFoodResult.get(0).getDrugFoodInteractionResult();
        }
        Assert.assertEquals(foodInteractionResult, "FOOD MAY CAUSE VARIABLE ABSORPTION.");
    }

    @Test
    public void testSizeOfQueryFoodInteractionsWithNoneReturned() {
        List<Drug> queryDrugsResult = fdbPrescriber.queryDrugs("DIGITALIS PURP GRN 3-30CH");
        List<FoodInteraction> queryFoodResult = fdbPrescriber.queryFoodInteractionsOfDrug(queryDrugsResult.get(0));
        if(!queryFoodResult.isEmpty()) {
            size = queryFoodResult.size();
        }
        Assert.assertEquals(size, 0);
    }

    @Test
    public void testNameOfQueryingFoodInteractionWithNoneReturned() {
        List<Drug> queryDrugsResult = fdbPrescriber.queryDrugs("DIGITALIS PURP GRN 3-30CH");
        List<FoodInteraction> queryFoodResult = fdbPrescriber.queryFoodInteractionsOfDrug(queryDrugsResult.get(0));
        if(!queryFoodResult.isEmpty()) {
            foodInteractionResult = queryFoodResult.get(0).getDrugFoodInteractionResult();
        }
        Assert.assertEquals(foodInteractionResult, null);
    }

}
