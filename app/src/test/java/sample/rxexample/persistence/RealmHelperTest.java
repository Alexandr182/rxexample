package sample.rxexample.persistence;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import sample.rxexample.BuildConfig;
import sample.rxexample.model.SearchResult;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by profiralexandr on 25/10/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
@PrepareForTest({Realm.class, RealmResults.class, RealmQuery.class})
public class RealmHelperTest {

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    private Realm mockRealm;

    @Before
    public void setup() {
        mockRealm = MockSupport.mockRealm(getSearchResults());
    }

    private static List<SearchResult> getSearchResults() {
        ArrayList<SearchResult> searchResults = new ArrayList<>();
        searchResults.add(generateSearchResult("asd", "active script directory", 2001, 1));
        searchResults.add(generateSearchResult("asd", "ae st dy", 2002, 5));
        searchResults.add(generateSearchResult("bte", "bingo total disk", 3001, 4));
        searchResults.add(generateSearchResult("bto", "beacon taco ok", 2101, 2));
        return searchResults;
    }

    private static SearchResult generateSearchResult(String acronym, String value, int year, int frequency) {
        SearchResult searchResult = new SearchResult();
        searchResult.setAcronym(acronym);
        searchResult.setName(value);
        searchResult.setSinceYear(year);
        searchResult.setFrequency(frequency);
        return searchResult;
    }

    @Test
    public void testAcronymResult() {
        RealmHelper realmHelper = new RealmHelper(mockRealm);
        List<SearchResult> results = realmHelper.getAcronyms("asd");
        assertThat(results.size(), is(1));
    }
}

