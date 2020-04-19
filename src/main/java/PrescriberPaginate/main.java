package PrescriberPaginate;

public class main {
    public static void main(String[] args) {
        String prefix = "can";
        PrescriberPaginate fdb = PrescriberPaginate.createFdbPrescriber();
       
        fdb.queryDrugs(prefix,5);
    }

}