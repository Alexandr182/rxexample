package sample.rxexample.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sample.rxexample.R;
import sample.rxexample.model.SearchResult;

/**
 * Created by profiralexandr on 17/08/16.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private List<SearchResult> mSearchResults;

    public SearchAdapter(List<SearchResult> searchResults) {
        mSearchResults = searchResults;
        setHasStableIds(true);
    }

    public SearchAdapter() {
        this(new ArrayList<>());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_search_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nameTV.setText(mSearchResults.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mSearchResults.size();
    }

    @Override
    public long getItemId(int position) {
        return mSearchResults.get(position).getName().hashCode();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTV;

        ViewHolder(View itemView) {
            super(itemView);
            nameTV = (TextView) itemView;
        }
    }
}
