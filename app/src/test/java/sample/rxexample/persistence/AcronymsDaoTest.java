package sample.rxexample.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import sample.rxexample.model.SearchResult;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;


/**
 * Created by profiralexandr on 09/11/16.
 */

@RunWith(MockitoJUnitRunner.class)
public class AcronymsDaoTest {

    private static final String FAKE_STRING = "HELLO WORLD";

    @Mock
    RealmHelper mMockRealmHelper;

    private AcronymsDao getAcronymsDao() {
        when(mMockRealmHelper.getSearchResults()).thenReturn(getSearchResults());
        return new AcronymsDao(mMockRealmHelper);
    }

    private static List<SearchResult> getSearchResults() {
        ArrayList<SearchResult> searchResults = new ArrayList<>();
        searchResults.add(generateSearchResult("ac sc di", 6, 2005));
        searchResults.add(generateSearchResult("ae st dy", 2, 2001));
        searchResults.add(generateSearchResult("bn tl dd", 3, 1990));
        searchResults.add(generateSearchResult("bi tl dk", 3, 1992));
        searchResults.add(generateSearchResult("bo ta di", 2, 1995));
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
        List<SearchResult> results = getAcronymsDao().getAcronyms("asd");
        assertThat(results.size(), is(2));
    }

    @Test
    public void testAcronymResult_bte() {
        List<SearchResult> results = getAcronymsDao().getAcronyms("btd");
        assertThat(results.size(), is(3));
    }

    @Test
    public void testAcronymResult_bto() {
        List<SearchResult> results = getAcronymsDao().getAcronyms("bto");
        assertThat(results.size(), is(1));
    }

    @Test
    public void testAcronymResult_asd_frequency_order() {
        List<SearchResult> results = getAcronymsDao().getAcronyms("asd");
        assertThat(results.get(0).getFrequency() > results.get(1).getFrequency(), is(true));
    }

    @Test
    public void testAcronymResult_bto_frequency_and_year_order() {
        List<SearchResult> results = getAcronymsDao().getAcronyms("btd");
        assertThat(results.get(0).getFrequency() == results.get(1).getFrequency() &&
                results.get(0).getYear() >= results.get(1).getYear(), is(true));
    }
}
