package sample.rxexample.persistence;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import sample.rxexample.BuildConfig;
import sample.rxexample.model.SearchResult;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by profiralexandr on 25/10/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
@PrepareForTest({Realm.class, RealmResults.class, RealmQuery.class})
public class RealmHelperTest {

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    public static Realm mockRealm() {
        RealmQuery<SearchResult> mockQuery = MockSupport.mockRealmQuery();
        RealmResults<SearchResult> mockResults = MockSupport.mockRealmResults();
        return MockSupport.mockRealm(SearchResult.class, mockQuery, mockResults, getSearchResults());
    }

    private static List<SearchResult> getSearchResults() {
        ArrayList<SearchResult> searchResults = new ArrayList<>();
        searchResults.add(generateSearchResult("ac sc di", 6, 2005));
        searchResults.add(generateSearchResult("ae st dy", 2, 2001));
        searchResults.add(generateSearchResult("bn tl dd", 3, 1990));
        searchResults.add(generateSearchResult("bi tl dk", 3, 1992));
        searchResults.add(generateSearchResult("bo ta di", 4, 1995));
        searchResults.add(generateSearchResult("be tc ok", 2, 1886));
        return searchResults;
    }

    private static SearchResult generateSearchResult(String value, int frequency, int year) {
        SearchResult searchResult = new SearchResult();
        searchResult.setName(value);
        searchResult.setYear(year);
        searchResult.setFrequency(frequency);
        return searchResult;
    }

    @Test
    public void testAcronymResult_asd() {
        RealmHelper realmHelper = new RealmHelper(mockRealm());
        List<SearchResult> results = realmHelper.getAcronyms("asd");
        assertThat(results.size(), is(2));
    }

    @Test
    public void testAcronymResult_bte() {
        RealmHelper realmHelper = new RealmHelper(mockRealm());
        List<SearchResult> results = realmHelper.getAcronyms("bte");
        assertThat(results.size(), is(3));
    }

    @Test
    public void testAcronymResult_bto() {
        RealmHelper realmHelper = new RealmHelper(mockRealm());
        List<SearchResult> results = realmHelper.getAcronyms("bto");
        assertThat(results.size(), is(1));
    }

    @Test
    public void testAcronymResult_asd_frequency_order() {
        RealmHelper realmHelper = new RealmHelper(mockRealm());
        List<SearchResult> results = realmHelper.getAcronyms("asd");
        assertThat(results.get(0).getFrequency() > results.get(1).getFrequency(), is(true));
    }

    @Test
    public void testAcronymResult_bto_frequency_and_year_order() {
        RealmHelper realmHelper = new RealmHelper(mockRealm());
        List<SearchResult> results = realmHelper.getAcronyms("bto");
        assertThat(results.get(0).getFrequency() == results.get(1).getFrequency() &&
                results.get(0).getYear() >= results.get(1).getYear(), is(true));
    }

//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//    public static Realm mockRealmNameBeginsWith(String beginsWith) {
//        List<SearchResult> filteredResults = new ArrayList<>();
//        for (SearchResult searchResult : getSearchResults()) {
//            if (searchResult.getName().toLowerCase().startsWith(beginsWith)) {
//                filteredResults.add(searchResult);
//            }
//        }
//        RealmQuery<SearchResult> mockQuery = MockSupport.mockRealmQuery();
//        RealmResults<SearchResult> mockResults = MockSupport.mockRealmResults();
//        when(mockQuery.beginsWith("name", beginsWith, Case.INSENSITIVE)).thenReturn(mockQuery);
//        return MockSupport.mockRealm(SearchResult.class, mockQuery, mockResults, filteredResults);
//    }
}

