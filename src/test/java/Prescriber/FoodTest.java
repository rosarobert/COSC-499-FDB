package Prescriber;

import Info.Drug;
import Info.DrugInteraction;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class FoodTest {

    private FdbPrescriberUnoptimized fdbPrescriberUnoptimized;

    //Requests a connection to the database
    @BeforeClass
    public void init() throws Exception {
        fdbPrescriberUnoptimized = new FdbPrescriberUnoptimized();
    }

    //closes connection
    @AfterClass
    public void end() throws Exception {
        fdbPrescriberUnoptimized.closePrescriber();
    }

    @Test
    public void testSizeOfQueryFoodInteractionsWithOnlyOneReturned() {
        List<Drug> queryDrugsResult = fdbPrescriberUnoptimized.queryDrugs("ELIXOPHYLLIN K1 ELIXIR");
        List<DrugInteraction> queryFoodResult = fdbPrescriberUnoptimized.queryFoodInteractionsOfDrug(queryDrugsResult.get(0));
        Assert.assertEquals(queryFoodResult.size(), 1);
    }

    @Test
    public void testNameOfQueryingFoodInteractionWithOnlyOneReturned() {
        List<Drug> queryDrugsResult = fdbPrescriberUnoptimized.queryDrugs("ELIXOPHYLLIN K1 ELIXIR");
        List<DrugInteraction> queryFoodResult = fdbPrescriberUnoptimized.queryFoodInteractionsOfDrug(queryDrugsResult.get(0));
        String foodInteractionResult = queryFoodResult.get(0).getInteractionDescription();
        Assert.assertEquals(foodInteractionResult, "FOOD MAY CAUSE VARIABLE ABSORPTION.");
    }

    @Test
    public void testSizeOfQueryFoodInteractionsWithNoneReturned() {
        List<Drug> queryDrugsResult = fdbPrescriberUnoptimized.queryDrugs("DIGITALIS PURP GRN 3-30CH");
        List<DrugInteraction> queryFoodResult = fdbPrescriberUnoptimized.queryFoodInteractionsOfDrug(queryDrugsResult.get(0));
        Assert.assertEquals(queryFoodResult.size(), 0);
    }

    @Test
    public void testNameOfQueryingFoodInteractionWithNoneReturned() {
        List<Drug> queryDrugsResult = fdbPrescriberUnoptimized.queryDrugs("DIGITALIS PURP GRN 3-30CH");
        List<DrugInteraction> queryFoodResult = fdbPrescriberUnoptimized.queryFoodInteractionsOfDrug(queryDrugsResult.get(0));
        Assert.assertTrue(queryFoodResult.isEmpty());
    }

    @Test
    public void testSizeOfQueryFoodInteractionsWithManyReturned() {
        List<Drug> queryDrugsResult = fdbPrescriberUnoptimized.queryDrugs("SYNTHROID 25 MCG TABLET");
        List<DrugInteraction> queryFoodResult = fdbPrescriberUnoptimized.queryFoodInteractionsOfDrug(queryDrugsResult.get(0));
        Assert.assertEquals(queryFoodResult.size(), 3);
    }

    @Test
    public void testNameOfQueryingFoodInteractionWithManyReturned() {
        List<Drug> queryDrugsResult = fdbPrescriberUnoptimized.queryDrugs("SYNTHROID 25 MCG TABLET");
        List<DrugInteraction> queryFoodResult = fdbPrescriberUnoptimized.queryFoodInteractionsOfDrug(queryDrugsResult.get(0));
        String foodInteractionResult = queryFoodResult.get(0).getInteractionDescription();
        for(int i = 1; i<queryFoodResult.size();i++)
            foodInteractionResult += ", " + queryFoodResult.get(i).getInteractionDescription();
        Assert.assertEquals(foodInteractionResult,
                "COFFEE DECR ABSORPTION OF LEVOTHYROXINE TABS., FIBER DECREASES ABSORPTION OF LEVOTHYROXINE, SOY DECREASES ABSORPTION OF LEVOTHYROXINE.");
    }
}
