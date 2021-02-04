package com.aotuman.studydemo.jetpack.mvvm.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aotuman.studydemo.jetpack.mvvm.model.Callback;
import com.aotuman.studydemo.jetpack.mvvm.model.User;
import com.aotuman.studydemo.jetpack.mvvm.model.UserRepository;

import java.util.List;

/**
 * UserListViewModel
 * 注意，没有持有View层的任何引用
 */
public class UserListViewModel extends ViewModel {

    /**
     * 用户信息
     */
    private MutableLiveData<List<User>> userListLiveData;

    /**
     * 进条度的显示
     */
    private MutableLiveData<Boolean> loadingLiveData;

    public UserListViewModel() {
        userListLiveData = new MutableLiveData<>();
        loadingLiveData = new MutableLiveData<>();
    }

    /**
     * 获取用户列表信息
     * 假装网络请求 2s后 返回用户信息
     */
    public void getUserInfo() {

        loadingLiveData.setValue(true);

        UserRepository.getUserRepository().getUsersFromServer(new Callback<List<User>>() {
            @Override
            public void onSuccess(List<User> users) {
                loadingLiveData.setValue(false);
                userListLiveData.setValue(users);
            }

            @Override
            public void onFailed(String msg) {
                loadingLiveData.setValue(false);
                userListLiveData.setValue(null);
            }
        });
    }

    /**
     * 返回LiveData类型
     */
    public LiveData<List<User>> getUserListLiveData() {
        return userListLiveData;
    }
    public LiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }
}
