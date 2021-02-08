package com.aotuman.studydemo.jetpack.company.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.aotuman.studydemo.jetpack.company.SimpleValueProtocol;
import com.aotuman.studydemo.jetpack.company.model.User;
import com.aotuman.studydemo.jetpack.company.model.UserRepository;
import com.aotuman.studydemo.jetpack.mvvm.model.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FormListViewModel extends FormViewModel implements SimpleValueProtocol {
    private final static String TAG = FormListViewModel.class.getSimpleName();
    private String text;
    // 控件自己维护一份需要提供对外可观察的数据
    private MutableLiveData<Object> liveData = new MutableLiveData<>();

    // 假设TextViewModel需要从UserRepository取数据
    private UserRepository userRepository = new UserRepository();

    private List<FormViewModel> childVMs = new ArrayList<>();

    public FormListViewModel() {
        // 假装15秒之后去请求数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d(TAG, "开始等待10秒");
                    TimeUnit.SECONDS.sleep(10);
                    getUserInfo();
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    public List<MutableLiveData<Object>> getLiveDataList() {
        List<MutableLiveData<Object>> liveDataList = new ArrayList<>();
        liveDataList.add(liveData);
        return liveDataList;
    }

    @Override
    public void updateValue(Object value) {
        liveData.postValue(value);
    }

    /**
     * 获取用户列表信息
     * 假装网络请求 2s后 返回用户信息
     */
    public void getUserInfo() {
        Log.d(TAG, "子线程开始获取用户列表数据");
        userRepository.getUsersFromServer(new Callback<List<User>>() {
            @Override
            public void onSuccess(List<User> users) {
                Log.d(TAG, "获取结果【成功】：");
                liveData.postValue(users);
            }

            @Override
            public void onFailed(String msg) {
                Log.d(TAG, "获取结果【失败】："+msg);
                liveData.postValue(msg);
            }
        });
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public List<FormViewModel> getChildVMs() {
        return childVMs;
    }

    public void setChildVMs(List<FormViewModel> childVMs) {
        this.childVMs = childVMs;
    }
}
