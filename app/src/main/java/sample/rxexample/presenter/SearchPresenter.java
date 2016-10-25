package sample.rxexample.presenter;

import java.util.ArrayList;
import java.util.List;

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
import sample.rxexample.utils.RealmUtils;

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

    public void searchForAcronym(String text) {
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
                RealmHelper realmHelper = new RealmHelper(RealmUtils.getRealm());
                subscriber.onNext(realmHelper.getAcronyms(name));
                realmHelper.close();
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
        }).doOnNext(results -> {
            for (SearchResult searchResult : results) {
                searchResult.setAcronym(name);
            }
            RealmHelper realmHelper = new RealmHelper(RealmUtils.getRealm());
            realmHelper.saveToRealm(results);
            realmHelper.close();
        }).subscribeOn(Schedulers.io());
    }
}
