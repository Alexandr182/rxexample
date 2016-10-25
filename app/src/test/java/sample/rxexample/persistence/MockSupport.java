package sample.rxexample.persistence;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.internal.RealmCore;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

public class MockSupport {

    static {
        mockStatic(RealmCore.class);
        mockStatic(Realm.class);
        mockStatic(RealmResults.class);
    }

    public static<T extends RealmObject> Realm mockRealm(Class pClass, RealmQuery<T> mockQuery, RealmResults<T> mockResults, List<T> mockSearchResults) {
        Realm mockRealm = mock(Realm.class);
        when(mockRealm.where(pClass)).thenReturn(mockQuery);
        when(mockQuery.findAll()).thenReturn(mockResults);
        when(mockRealm.copyFromRealm(mockResults)).thenReturn(mockSearchResults);
        return mockRealm;
    }

    @SuppressWarnings("unchecked")
    public static <T extends RealmObject> RealmQuery<T> mockRealmQuery() {
        return mock(RealmQuery.class);
    }

    @SuppressWarnings("unchecked")
    public static <T extends RealmObject> RealmResults<T> mockRealmResults() {
        return mock(RealmResults.class);
    }
}