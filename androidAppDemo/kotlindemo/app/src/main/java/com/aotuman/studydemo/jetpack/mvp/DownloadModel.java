package com.aotuman.studydemo.jetpack.mvp;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.aotuman.studydemo.utils.HttpUtil;

public class DownloadModel implements IDownloadModel {

    private IDowndownPresenter mIDowndownPresenter;

    final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 300:
                    int percent = msg.arg1;
                    if (percent < 100) {
                        mIDowndownPresenter.downloadProgress(percent);
                    } else {
                        mIDowndownPresenter.downloadSuccess(Constants.LOCAL_FILE_PATH);
                    }
                    break;
                case 404:
                    mIDowndownPresenter.downloadFail();
                    break;
                default:
                    break;
            }
        }
    };


    public DownloadModel(IDowndownPresenter IDowndownPresenter) {
        mIDowndownPresenter = IDowndownPresenter;
    }

    @Override
    public void download(String url) {
        HttpUtil.HttpGet(url, new DownloadCallback(mHandler));
    }

}
