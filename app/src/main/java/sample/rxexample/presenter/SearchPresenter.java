package sample.rxexample.presenter;

import android.text.TextUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sample.rxexample.api.ApiHelper;
import sample.rxexample.model.SearchResult;
import sample.rxexample.model.response.FindAcronymResponse;
import sample.rxexample.ui.view.SearchView;

/**
 * Created by profiralexandr on 17/08/16.
 */

public class SearchPresenter {
    private SearchView mSearchView;

    public SearchPresenter(SearchView searchView) {
        mSearchView = searchView;
    }

    public void detachFromView() {
        mSearchView = null;
    }

    public void searchFor(String newText) {
        Call<FindAcronymResponse> call = ApiHelper.getApi().findAcronyms(newText);
        call.enqueue(new Callback<FindAcronymResponse>() {
            @Override
            public void onResponse(Call<FindAcronymResponse> call, Response<FindAcronymResponse> baseResponse) {
                FindAcronymResponse response = baseResponse.body();
                if (!TextUtils.isEmpty(response.getAcronym())) {
                    mSearchView.onResultsLoaded(response.getSearchResults());
                } else {
                    mSearchView.onResultsLoaded(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<FindAcronymResponse> call, Throwable t) {
                mSearchView.onLoadingFailed();
            }
        });
    }
}
