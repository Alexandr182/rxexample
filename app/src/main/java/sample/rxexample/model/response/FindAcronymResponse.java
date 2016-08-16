package sample.rxexample.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import sample.rxexample.model.SearchResult;

/**
 * Created by profiralexandr on 17/08/16.
 */

public class FindAcronymResponse {
    @SerializedName("sf")
    private String acronym;

    @SerializedName("lfs")
    private List<SearchResult> searchResults;

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public List<SearchResult> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<SearchResult> searchResults) {
        this.searchResults = searchResults;
    }
}
