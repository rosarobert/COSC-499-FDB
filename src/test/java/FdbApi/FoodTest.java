package FdbApi;

import org.testng.Assert;
import org.testng.annotations.*;

import java.sql.Connection;
import java.sql.SQLException;

public class FoodTest {

    private static Food f;
    private static Connection con;

    //Requests a connection to the database
    @BeforeClass
    public static void init() throws Exception {
        f = new Food();
        con = f.connect();
    }
    //closes connection
    @AfterClass
    public static void end() throws Exception {
        f.close();
    }

    /**
     * Testing of GCN_SEQNO drugs to food
     * Cases: 34, 1, 'A', 6649
     */
    @Test
    public void GCNtoFDCDETest() {

        String result = null;
        String answer;

        //Test Drug with GCN_SEQNO: 34
        System.out.println("Test Drug with GCN_SEQNO: 34");
        answer = "GCN_SEQNO, FDCDE, DNAME, RESULT"
                +"\n34, 28, THEOPHYLLINE, FOOD MAY CAUSE VARIABLE ABSORPTION.";
        try{
            result = f.GCNtoFDCDE(34);
        }catch(SQLException e){
            e.printStackTrace();
        }
        Assert.assertEquals(result , answer);

        //Test Drug with GCN_SEQNO: 1
        System.out.println("Test Drug with GCN_SEQNO: 1");
        answer = "GCN_SEQNO, FDCDE, DNAME, RESULT";
        try{
            result = f.GCNtoFDCDE(1);
        }catch(SQLException e){
            e.printStackTrace();
        }
        Assert.assertEquals(result , answer);

        //Test Drug with GCN_SEQNO: A
        System.out.println("Test Drug with GCN_SEQNO: A");
        answer = "GCN_SEQNO, FDCDE, DNAME, RESULT";
        try{
            result = f.GCNtoFDCDE('A');
        }catch(SQLException e){
            e.printStackTrace();
        }
        Assert.assertEquals(result , answer);

        //Test Drug with GCN_SEQNO: 6649
        System.out.println("Test Drug with GCN_SEQNO: 6649");
        answer = "GCN_SEQNO, FDCDE, DNAME, RESULT"
                +"\n6649, 94, LEVOTHYROXINE, SOY DECREASES ABSORPTION OF LEVOTHYROXINE."
                +"\n6649, 149, LEVOTHYROXINE, FIBER DECREASES ABSORPTION OF LEVOTHYROXINE"
                +"\n6649, 150, LEVOTHYROXINE TABLETS, COFFEE DECR ABSORPTION OF LEVOTHYROXINE TABS.";
        try{
            result = f.GCNtoFDCDE(6649);
        }catch(SQLException e){
            e.printStackTrace();
        }
        Assert.assertEquals(result , answer);
    }

    /**
     * Testing of ROUTED_MED_ID drugs to food
     * Cases: 1, 2, 'A', 19044
     */
    @Test
    public void RoutedToFDCDETest() {

        String result = null;
        String answer;

        //Test Drug with ROUTED_MED_ID: 1
        System.out.println("Test Drug with ROUTED_MED_ID: 1");
        answer = "ROUTED_MED_ID, FDCDE, DNAME, RESULT"
                +"\n1, 67, SERTRALINE, GRAPEFRUIT MAY INCREASE SERUM DRUG CONC.";
        try{
            result = f.RoutedToFDCDE(1);
        }catch(SQLException e){
            e.printStackTrace();
        }
        Assert.assertEquals(result , answer);

        //Test Drug with ROUTED_MED_ID: 2
        System.out.println("Test Drug with ROUTED_MED_ID: 2");
        answer = "ROUTED_MED_ID, FDCDE, DNAME, RESULT";
        try{
            result = f.RoutedToFDCDE(2);
        }catch(SQLException e){
            e.printStackTrace();
        }
        Assert.assertEquals(result , answer);

        //Test Drug with ROUTED_MED_ID: A
        System.out.println("Test Drug with ROUTED_MED_ID: 'A'");
        answer = "ROUTED_MED_ID, FDCDE, DNAME, RESULT";
        try{
            result = f.RoutedToFDCDE('A');
        }catch(SQLException e){
            e.printStackTrace();
        }
        Assert.assertEquals(result , answer);

        //Test Drug with ROUTED_MED_ID: 19044
        System.out.println("Test Drug with ROUTED_MED_ID: 1");
        answer = "ROUTED_MED_ID, FDCDE, DNAME, RESULT"
                +"\n19044, 94, LEVOTHYROXINE, SOY DECREASES ABSORPTION OF LEVOTHYROXINE."
                +"\n19044, 149, LEVOTHYROXINE, FIBER DECREASES ABSORPTION OF LEVOTHYROXINE"
                +"\n19044, 150, LEVOTHYROXINE TABLETS, COFFEE DECR ABSORPTION OF LEVOTHYROXINE TABS.";
        try{
            result = f.RoutedToFDCDE(19044);
        }catch(SQLException e){
            e.printStackTrace();
        }
        Assert.assertEquals(result , answer);
    }
}
