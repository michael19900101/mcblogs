package com.aotuman.studydemo.searchablerecyclerview.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.aotuman.studydemo.R;
import com.aotuman.studydemo.searchablerecyclerview.adapter.viewholder.WordViewHolder;
import com.aotuman.studydemo.searchablerecyclerview.models.WordModel;
import com.github.wrdlbrnft.modularadapter.ModularAdapter;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

import java.util.Comparator;
import java.util.List;


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
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_rv_item_word, parent, false);
        return new WordViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ModularAdapter.ViewHolder<? extends WordModel> holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        WordViewHolder wordViewHolder = (WordViewHolder) holder;
        wordViewHolder.itemView.setOnClickListener(v -> mListener.onExampleModelClicked(getItem(position)));
    }
}
