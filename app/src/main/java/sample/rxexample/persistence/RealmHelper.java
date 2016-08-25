package sample.rxexample.persistence;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import sample.rxexample.model.SearchResult;
import sample.rxexample.utils.RxExampleAplication;

/**
 * Created by profiralexandr on 17/08/16.
 */

public class RealmHelper {

    private static RealmHelper sInstance;
    private Realm mRealm;

    public RealmHelper() {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(RxExampleAplication.getInstance())
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();

        mRealm = Realm.getInstance(realmConfiguration);
    }

    public static RealmHelper getInstance() {
        if (sInstance == null) {
            sInstance = new RealmHelper();
        }
        return sInstance;
    }

    public void saveToRealm(List<SearchResult> results) {
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(results);
        mRealm.commitTransaction();
    }

    public List<SearchResult> getAcronyms(String name) {
        return mRealm.copyFromRealm(mRealm.where(SearchResult.class).findAll());
    }
}
