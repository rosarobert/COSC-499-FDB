package Utils;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CsvComparisionTest {
    String filePath1;
    String filePath2;
    String filePathToResult;

    @Test
    public void test1ComparingTwoOrderedFilesThatAreTheSame() throws IOException {
        filePath1 = "src/test/java/Utils/Test1File1.csv";
        filePath2 = "src/test/java/Utils/Test1File2.csv";
        filePathToResult = "src/test/java/Utils/results1.csv";

        CsvComparision.orderedFileComparison(filePath1, filePath2, ",", filePathToResult, 2);
        BufferedReader reader = new BufferedReader(new FileReader(filePathToResult));
        String result = reader.readLine();
        Assert.assertNull(result);
    }

    @Test
    public void test2ComparingTwoOrderedFilesWhereFirstHasOneMoreRowThanOther() throws IOException {
        filePath1 = "src/test/java/Utils/Test2File1.csv";
        filePath2 = "src/test/java/Utils/Test2File2.csv";
        filePathToResult = "src/test/java/Utils/results2.csv";

        CsvComparision.orderedFileComparison(filePath1, filePath2, ",", filePathToResult, 2);
        BufferedReader reader = new BufferedReader(new FileReader(filePathToResult));
        String result = reader.readLine();
        Assert.assertEquals(result, "hello,10,10,H,Test2File1.csv");
    }

    @Test
    public void test3ComparingTwoOrderedFilesWhereBothHaveOneRowUnique() throws IOException {
        filePath1 = "src/test/java/Utils/Test3File1.csv";
        filePath2 = "src/test/java/Utils/Test3File2.csv";
        filePathToResult = "src/test/java/Utils/results3.csv";

        CsvComparision.orderedFileComparison(filePath1, filePath2, ",", filePathToResult, 2);
        BufferedReader reader = new BufferedReader(new FileReader(filePathToResult));
        String result1 = reader.readLine();
        String result2 = reader.readLine();
        Assert.assertEquals(result1, "hello,10,10,H,Test3File1.csv");
        Assert.assertEquals(result2, "hello,20000,hello,hello,Test3File2.csv");
    }



}