package Utils;

import java.io.*;

/**
 * A class for static methods which compare files
 */
public class CsvComparision {

    public static void main(String[] args) throws FileNotFoundException {
        String filePathToCarson = "results_manufactured.csv";
        String filePathToJen = "Query_Manufacturer.csv";

        orderedFileComparison(filePathToCarson, filePathToJen, ",", "result.csv", 3);
    }

    /**
     * Compares two files based on one ordered column. The columns the do not appear in the other file will be written
     * to a results file, and a column will be added that tells you which file this unique row was in
     * <p>
     * Note that if there are duplicate rows (rows with the same comparison column), this column will be written the
     * number of times it appears in one file more
     *
     * @param filePath1         file path to first file to compare
     * @param filePath2         file path to second file to compare
     * @param delimiter         token that separated values in one row. For example, in a CSV file this is a comma
     * @param filePathToResults where the results file is or where one will be generated if it doesnt exist
     * @param columnToCompareOn column to compare the two files on. Note columns start from column 1
     * @throws FileNotFoundException
     */
    public static void orderedFileComparison(String filePath1, String filePath2, String delimiter, String filePathToResults, int columnToCompareOn) throws FileNotFoundException {
        File file1 = new File(filePath1);
        File file2 = new File(filePath2);

        String file1Name = file1.getName();
        String file2Name = file2.getName();

        PrintWriter resultWriter = new PrintWriter(filePathToResults);

        try (BufferedReader reader1 = new BufferedReader(new FileReader(file1)); BufferedReader reader2 = new BufferedReader(new FileReader(file2))) {
            //Read the first line in each file
            String lineFromFile1 = reader1.readLine();
            String lineFromFile2 = reader2.readLine();

            while (lineFromFile1 != null && lineFromFile2 != null) {
                //Compare both lines lexigraphically by the column given
                int comparasionResult = compareColumn(lineFromFile1, lineFromFile2, delimiter, columnToCompareOn - 1);

                //If one value is less than the other, it means there are unique values in the file with the smaller value not in the other file
                if (comparasionResult == -1) {

                    resultWriter.write(lineFromFile1);
                    resultWriter.println(delimiter + file1Name);
                    lineFromFile1 = reader1.readLine();
                } else if (comparasionResult == 1) {
                    resultWriter.write(lineFromFile2);
                    resultWriter.println(delimiter + file2Name);
                    lineFromFile2 = reader2.readLine();
                } else if (comparasionResult == 0) {
                    lineFromFile1 = reader1.readLine();
                    lineFromFile2 = reader2.readLine();
                } else {
                    throw new IllegalStateException("Something went wrong, and I was too stupid to figure it out");
                }

            }

            while(lineFromFile1 != null) {
                resultWriter.write(lineFromFile1);
                resultWriter.println(delimiter + file1Name);
                lineFromFile1 = reader1.readLine();
            }

            while(lineFromFile2 != null){
                resultWriter.write(lineFromFile2);
                resultWriter.println(delimiter + file2Name);
                lineFromFile2 = reader2.readLine();
            }
            resultWriter.flush();
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    private static int compareColumn(String tuple1, String tuple2, String delimiter, int columnToCompareOn) {
        String[] tuple1Values = tuple1.split(delimiter);
        String[] tuple2Values = tuple2.split(delimiter);

        String valueFromTuple1 = tuple1Values[columnToCompareOn];
        String valueFromTuple2 = tuple2Values[columnToCompareOn];
        int comparasion = valueFromTuple1.compareToIgnoreCase(valueFromTuple2);
        if (comparasion < 0)
            return -1;
        else if (comparasion > 0)
            return 1;
        else
            return 0;
    }
}

