package com.aotuman.studydemo.searchablerecyclerview.adapter.viewholder;

import androidx.annotation.NonNull;

import com.aotuman.studydemo.databinding.SearchRvItemWordBinding;
import com.aotuman.studydemo.searchablerecyclerview.adapter.ExampleAdapter;
import com.aotuman.studydemo.searchablerecyclerview.models.WordModel;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

public class WordViewHolder extends SortedListAdapter.ViewHolder<WordModel> {

    private SearchRvItemWordBinding wordBinding;

    public WordViewHolder(SearchRvItemWordBinding binding, ExampleAdapter.Listener listener) {
        super(binding.getRoot());
        this.wordBinding = binding;
        binding.setListener(listener);
    }

    @Override
    protected void performBind(@NonNull WordModel item) {
        wordBinding.setModel(item);
    }
}
