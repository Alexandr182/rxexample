package sample.rxexample.persistence;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.internal.RealmCore;
import sample.rxexample.model.SearchResult;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

public class MockSupport {

    public static Realm mockRealm(List<SearchResult> searchResults) {
        mockStatic(RealmCore.class);
        mockStatic(Realm.class);
        mockStatic(RealmResults.class);

        Realm mockRealm = mock(Realm.class);
        RealmQuery<SearchResult> mockQuery = mockRealmQuery();
        RealmResults<SearchResult> mockResults = mockRealmResults();

        when(mockRealm.copyFromRealm(mockResults)).thenReturn(searchResults);
        when(mockQuery.findAll()).thenReturn(mockResults);
        when(mockRealm.where(SearchResult.class)).thenReturn(mockQuery);

        return mockRealm;
    }

    @SuppressWarnings("unchecked")
    private static <T extends RealmObject> RealmQuery<T> mockRealmQuery() {
        return mock(RealmQuery.class);
    }

    @SuppressWarnings("unchecked")
    private static <T extends RealmObject> RealmResults<T> mockRealmResults() {
        return mock(RealmResults.class);
    }
}