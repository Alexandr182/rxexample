package sample.rxexample.persistence;

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
        return results;
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
