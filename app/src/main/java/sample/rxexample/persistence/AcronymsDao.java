package sample.rxexample.persistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sample.rxexample.model.SearchResult;
import sample.rxexample.utils.RealmUtils;

/**
 * Created by profiralexandr on 09/11/16.
 */

public class AcronymsDao {

    private RealmHelper mRealmHelper;

    public AcronymsDao(RealmHelper realmHelper) {
        mRealmHelper = realmHelper;
    }

    public AcronymsDao() {
        mRealmHelper = new RealmHelper(RealmUtils.getRealm());
    }

    public List<SearchResult> getAcronyms(String s) {
        List<SearchResult> results = mRealmHelper.getSearchResults();
        List<SearchResult> filteredResults = new ArrayList<>();
        for (SearchResult searchResult : results) {
            if (passAcronymCheck(searchResult, s)) {
                filteredResults.add(searchResult);
            }
        }

        Collections.sort(filteredResults, (o1, o2) -> {
            int result = o2.getFrequency() - o1.getFrequency();
            if (result == 0) {
                result = o2.getYear() - o1.getYear();
            }
            return result;
        });
        return filteredResults;
    }

    private boolean passAcronymCheck(SearchResult searchResult, String acronym) {
        char[] chars = acronym.toCharArray();
        String[] parts = searchResult.getName().split(" ");
        if (chars.length != parts.length) {
            return false;
        }

        for (int i = 0; i < chars.length; i++) {
            if (!parts[i].startsWith(String.valueOf(chars[i]))) {
                return false;
            }
        }

        return true;
    }

    public void saveResults(List<SearchResult> results) {
        mRealmHelper.saveToRealm(results);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        mRealmHelper.close();
    }
}
