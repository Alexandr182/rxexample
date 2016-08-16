package sample.rxexample.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import sample.rxexample.R;
import sample.rxexample.model.SearchResult;
import sample.rxexample.presenter.SearchPresenter;
import sample.rxexample.ui.adapter.SearchAdapter;
import sample.rxexample.ui.view.SearchView;

public class SearchActivity extends AppCompatActivity implements SearchView {

    @Bind(R.id.am_sv_search)
    android.support.v7.widget.SearchView mSearchSV;

    @Bind(R.id.am_rv_results)
    RecyclerView mResultsRV;

    private SearchPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initViews();
    }

    private void initData() {
        mPresenter = new SearchPresenter(this);
    }

    private void initViews() {
        mResultsRV.setLayoutManager(new LinearLayoutManager(this));
        mResultsRV.setAdapter(new SearchAdapter());
        setUpSearchListeners();
    }

    private void setUpSearchListeners() {
        mSearchSV.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mPresenter.searchFor(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mPresenter.searchFor(newText);
                return true;
            }
        });
    }

    @Override
    public void onResultsLoaded(List<SearchResult> searchResults) {
        mResultsRV.swapAdapter(new SearchAdapter(searchResults), true);
    }

    @Override
    public void onLoadingFailed() {
        Toast.makeText(this, "Loading failed!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachFromView();
    }
}
