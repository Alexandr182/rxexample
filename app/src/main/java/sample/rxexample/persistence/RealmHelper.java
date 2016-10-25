package sample.rxexample.persistence;

import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import sample.rxexample.model.SearchResult;

/**
 * Created by profiralexandr on 17/08/16.
 */

public class RealmHelper {
    private Realm mRealm;

    public RealmHelper(Realm realm) {
        mRealm = realm;
    }

    public void saveToRealm(List<SearchResult> results) {
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(results);
        mRealm.commitTransaction();
    }

    public void close() {
        mRealm.close();
    }

    public List<SearchResult> getAcronyms(String acronym) {
        List<SearchResult> results = mRealm.copyFromRealm(mRealm.where(SearchResult.class).findAll());
        return results;
    }
}
