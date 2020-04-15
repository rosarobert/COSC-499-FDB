package PrescriberOptimized;

public class main {
    public static void main(String[] args) {
        String prefix = "can";
        PrescriberOptimized fdb = PrescriberOptimized.createFdbPrescriber();
       
        fdb.queryDrugs(prefix,4);
        
    }

}