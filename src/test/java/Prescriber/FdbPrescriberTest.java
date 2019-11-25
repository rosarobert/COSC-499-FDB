package Prescriber;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Tests for FdbPrescriber class
 * <p>
 * Note, as it currently is, this class is probably going to contain mostly integration tests, but we could decide to
 * include all integration and unit tests in this file, or we could separate unit and integration tests. Also note that
 * we could separated tests for each big function in our API into separate documents
 */
public class FdbPrescriberTest {

    @BeforeMethod
    public void setUp() {

    }

    @AfterMethod
    public void tearDown() {

    }

    /**
     * Tests the simple query of querying all drugs in FDB
     */
    public void testQueryingAllDrugsWithNoPrefix() {
        Assert.fail();
    }

    /**
     * Tests a query that returns a large amount of drugs such as all drugs that start with 'a'
     */
    public void testQueryingLargeAmountOfDrugsWithAPrefix() {
        Assert.fail();
    }

    /**
     * Tests a query that returns a small amount of drugs such a prefix that is an actual name
     */
    public void testQueryingSmallAmountOfDrugsWithAPrefix() {
        Assert.fail();
    }


    /**
     * Tests a query that should return no drugs
     */
    public void testQueryingForDrugsBasedOnAPrefixThatReturnsNone() {
        Assert.fail();
    }

}