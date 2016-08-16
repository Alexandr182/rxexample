package sample.rxexample.ui.view;

import java.util.List;

import sample.rxexample.model.SearchResult;

/**
 * Created by profiralexandr on 17/08/16.
 */

public interface SearchView {
    void onResultsLoaded(List<SearchResult> searchResults);

    void onLoadingFailed();
}
