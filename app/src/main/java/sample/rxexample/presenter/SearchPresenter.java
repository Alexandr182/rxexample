package sample.rxexample.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import sample.rxexample.api.ApiHelper;
import sample.rxexample.model.SearchResult;
import sample.rxexample.model.response.FindAcronymResponse;
import sample.rxexample.persistence.RealmHelper;
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
        Call<List<FindAcronymResponse>> call = ApiHelper.getApi().findAcronyms(newText);
        call.enqueue(new Callback<List<FindAcronymResponse>>() {
            @Override
            public void onResponse(Call<List<FindAcronymResponse>> call, Response<List<FindAcronymResponse>> baseResponse) {
                List<FindAcronymResponse> response = baseResponse.body();
                if (!response.isEmpty()) {
                    mSearchView.onResultsLoaded(response.get(0).getSearchResults());
                } else {
                    mSearchView.onResultsLoaded(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<List<FindAcronymResponse>> call, Throwable t) {
                mSearchView.onLoadingFailed();
            }
        });
    }

    public void searchForWithRx(String text) {
        Observable.concat(findAcronymsFromRealm(text), findAcronymsFromApi(text))
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<SearchResult>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(List<SearchResult> results) {
                mSearchView.onResultsLoaded(results);
            }
        });
    }

    private Observable<List<SearchResult>> findAcronymsFromRealm(String name) {
        return Observable.create(subscriber -> {
            try {
                RealmHelper realmHelper = RealmHelper.getInstance();
                subscriber.onNext(realmHelper.getAcronyms(name));
            } catch (Exception e) {
                subscriber.onError(e);
                return;
            }
            subscriber.onCompleted();
        });
    }

    private Observable<List<SearchResult>> findAcronymsFromApi(String name) {
        return ApiHelper.getApi().observeAcronyms(name).map(new Func1<List<FindAcronymResponse>, List<SearchResult>>() {
            @Override
            public List<SearchResult> call(List<FindAcronymResponse> findAcronymResponses) {
                if (!findAcronymResponses.isEmpty()) {
                    return findAcronymResponses.get(0).getSearchResults();
                } else {
                    return new ArrayList<>();
                }
            }
        }).delay(1000, TimeUnit.MILLISECONDS).doOnNext(results -> {
            for (SearchResult searchResult : results) {
                searchResult.setAcronym(name);
            }
            RealmHelper realmHelper = new RealmHelper();
            realmHelper.saveToRealm(results);
        }).subscribeOn(Schedulers.io());
    }
}
