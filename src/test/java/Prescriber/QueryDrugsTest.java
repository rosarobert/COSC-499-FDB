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
        fdbPrescriber.initializePrescriber();
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
}
