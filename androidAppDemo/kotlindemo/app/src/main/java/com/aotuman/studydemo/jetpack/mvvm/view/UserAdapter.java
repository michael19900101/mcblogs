package com.aotuman.studydemo.jetpack.mvvm.view;

import com.aotuman.studydemo.R;
import com.aotuman.studydemo.jetpack.mvvm.model.User;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;


class UserAdapter extends BaseQuickAdapter<User, BaseViewHolder> {
    public UserAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, User user) {
        viewHolder.setText(R.id.tv_user_name, user.getName());
        viewHolder.setText(R.id.tv_user_age, String.valueOf(user.getAge()));
    }
}
