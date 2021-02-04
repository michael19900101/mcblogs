package com.aotuman.studydemo.jetpack.mvvm.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.aotuman.studydemo.R;
import com.aotuman.studydemo.jetpack.mvvm.model.User;
import com.aotuman.studydemo.jetpack.mvvm.viewmodel.UserListViewModel;

import java.util.List;

/**
 *              ViewModel
 *             ↗          ↘ ↖
 *           ↙              ↘ ↖
 *         View              Model
 *
 * Model，模型层，即数据模型，用于获取和存储数据。
 * View，视图，即Activity/Fragment
 * ViewModel，视图模型，负责业务逻辑。
 *
 * MVVM 的本质是 数据驱动，把解耦做的更彻底，viewModel不持有view 。
 *
 * View 产生事件，使用 ViewModel进行逻辑处理后，通知Model更新数据，
 * Model把更新的数据给ViewModel，ViewModel自动通知View更新界面，而不是主动调用View的方法。
 */
public class UserListActivity extends AppCompatActivity {

    private UserListViewModel mUserListViewModel;
    private ProgressBar mProgressBar;
    private RecyclerView mRvUserList;
    private UserAdapter mUserAdapter;

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, UserListActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        initView();

        initViewModel();

        getData();

        observeLivaData();
    }

    private void initView() {
        mProgressBar = findViewById(R.id.pb_loading_users);
        mRvUserList = findViewById(R.id.rv_user_list);

        mRvUserList.setLayoutManager(new LinearLayoutManager(this));
        mUserAdapter = new UserAdapter(R.layout.item_user_mvvm);
        mRvUserList.setAdapter(mUserAdapter);
    }

    private void initViewModel() {
        ViewModelProvider viewModelProvider = new ViewModelProvider(this);
        mUserListViewModel = viewModelProvider.get(UserListViewModel.class);
    }

    /**
     * 获取数据，调用ViewModel的方法获取
     */
    private void getData() {
        mUserListViewModel.getUserInfo();
    }

    /**
     * 观察ViewModel的数据，且此数据 是 View 直接需要的，不需要再做逻辑处理
     */
    private void observeLivaData() {
        mUserListViewModel.getUserListLiveData().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                if (users == null) {
                    Toast.makeText(UserListActivity.this, "获取user失败！", Toast.LENGTH_SHORT).show();
                    return;
                }
                mUserAdapter.setNewInstance(users);
            }
        });

        mUserListViewModel.getLoadingLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                mProgressBar.setVisibility(aBoolean? View.VISIBLE:View.GONE);
            }
        });
    }


}
