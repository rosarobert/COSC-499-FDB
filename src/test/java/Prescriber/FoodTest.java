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
        Assert.assertEquals(queryFoodResult.size(), 1);
    }

    @Test
    public void testNameOfQueryingFoodInteractionWithOnlyOneReturned() {
        List<Drug> queryDrugsResult = fdbPrescriber.queryDrugs("ELIXOPHYLLIN K1 ELIXIR");
        List<FoodInteraction> queryFoodResult = fdbPrescriber.queryFoodInteractionsOfDrug(queryDrugsResult.get(0));
        Assert.assertEquals(queryFoodResult.get(0).getDrugFoodInteractionResult(), "FOOD MAY CAUSE VARIABLE ABSORPTION.");
    }

}
