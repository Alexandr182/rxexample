package sample.rxexample.utils;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by profiralexandr on 25/10/16.
 */

public class RealmUtils {
    public static Realm getRealm() {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(RxExampleAplication.getInstance())
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        return Realm.getInstance(realmConfiguration);
    }
}
