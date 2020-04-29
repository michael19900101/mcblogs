package com.aotuman.studydemo.searchablerecyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.aotuman.studydemo.R;
import com.aotuman.studydemo.databinding.SearchRvItemWordBinding;
import com.aotuman.studydemo.searchablerecyclerview.adapter.viewholder.WordViewHolder;
import com.aotuman.studydemo.searchablerecyclerview.models.WordModel;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

import java.util.Comparator;


public class ExampleAdapter extends SortedListAdapter<WordModel> {

    public interface Listener {
        void onExampleModelClicked(WordModel model);
    }

    private final Listener mListener;

    public ExampleAdapter(Context context, Comparator<WordModel> comparator, Listener listener) {
        super(context, WordModel.class, comparator);
        mListener = listener;
    }

    @NonNull
    @Override
    protected ViewHolder<? extends WordModel> onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent, int viewType) {
        SearchRvItemWordBinding binding = SearchRvItemWordBinding.inflate(inflater, parent, false);
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_rv_item_word, parent, false);
        return new WordViewHolder(binding, mListener);
    }
}
