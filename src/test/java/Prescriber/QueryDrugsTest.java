package Prescriber;

import Info.Drug;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class QueryDrugsTest {

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
    public void testSizeOfQueryDrugsWhenOnlyOneDrugReturned() {
        List<Drug> queryDrugsResult = fdbPrescriber.queryDrugs("PLACIDYL 200 MG CAPSULE");
        Assert.assertEquals(queryDrugsResult.size(), 1);
    }

    @Test
    public void testNameOfDrugWhenQueryingDrugsWithOnlyOneDrugReturned() {
        List<Drug> queryDrugsResult = fdbPrescriber.queryDrugs("PLACIDYL 200 MG CAPSULE");
        Assert.assertEquals(queryDrugsResult.get(0).getDisplayName(), "PLACIDYL 200 MG CAPSULE");
    }

    @Test
    public void testSizeOfQueryDrugsWhenNoDrugReturned() {
        List<Drug> queryDrugsResult = fdbPrescriber.queryDrugs("zx");
        Assert.assertEquals(queryDrugsResult.size(), 0);
    }

    @Test
    public void testSizeOfQueryDrugsWhenManyDrugReturned() {
        List<Drug> queryDrugsResult = fdbPrescriber.queryDrugs("PMS_DIGOXIN");
        Assert.assertEquals(queryDrugsResult.size(), 3);
    }

    @Test
    public void testNameOfDrugWhenQueryingDrugsWithManyDrugReturned() {
        List<Drug> queryDrugsResult = fdbPrescriber.queryDrugs("PMS_DIGOXIN");
        String result = queryDrugsResult.get(0).getDisplayName();
        for(int i = 0; i < queryDrugsResult.size(); i++)
            result += ", " + queryDrugsResult.get(0).getDisplayName();
        Assert.assertEquals(result, "PMS-DIGOXIN 0.0625 MG TAB, PMS-DIGOXIN 0.0625 MG TAB, PMS-DIGOXIN 0.0625 MG TAB, PMS-DIGOXIN 0.0625 MG TAB");
    }
}
