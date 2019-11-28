package FdbApi;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class DrugNamingTest {

    private static DrugNaming DN;
    private static Connection con;

    //Requests a connection to the database
    @BeforeClass
    public static void init() throws Exception {
        DN = new DrugNaming();
        con = DN.connect();
    }
    //closes connection
    @AfterClass
    public static void end() throws Exception {
        DN.close();
    }

    @Test
    public void ManufacturedDrugNamingTest() {

        String result = null;
        String answer;

        //Test Drug with ROUTED_MED_ID: 1
        System.out.println("Test Manufactured Drug Naming");
        answer = "LN, HICL_SEQNO, GCN_SEQNO, DIN, IADDTE, IOBSDTE, MFG"
                +"\nPLACIDYL 200 MG CAPSULE, 1580, 3661, 00000019, 1992-12-16 00:00:00.0, 1999-09-08 00:00:00.0, ABBOTT LABS";
        try{
            result = DN.drugNamingManufactured("PLACIDYL 200 MG CAPSULE");
        }catch(SQLException e){
            e.printStackTrace();
        }
        Assert.assertEquals(result , answer);
    }
}
