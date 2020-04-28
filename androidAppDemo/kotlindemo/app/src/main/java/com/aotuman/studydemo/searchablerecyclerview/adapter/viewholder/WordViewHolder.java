package com.aotuman.studydemo.searchablerecyclerview.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.aotuman.studydemo.R;
import com.aotuman.studydemo.searchablerecyclerview.models.WordModel;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

public class WordViewHolder extends SortedListAdapter.ViewHolder<WordModel> {

    private TextView tvLeft;
    private TextView tvRight;

    public WordViewHolder(View itemView) {
        super(itemView);
        tvLeft = itemView.findViewById(R.id.tv_left);
        tvRight = itemView.findViewById(R.id.tv_right);
    }

    @Override
    protected void performBind(@NonNull WordModel item) {
        tvLeft.setText(String.valueOf(item.getRank()));
        tvRight.setText(item.getWord());
    }
}
