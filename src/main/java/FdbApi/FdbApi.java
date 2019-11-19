package FdbApi;

import java.util.List;

public interface FdbApi {
    /**
     * Returns all drugs that start with the given prefix. This includes drugs with brand name, label name, and 
     * @param prefix string that all drug names should start with 
     * @return list of all drugs that start with the given prefix
     */
    List<Drug> queryDrugs(String prefix);    
    
}