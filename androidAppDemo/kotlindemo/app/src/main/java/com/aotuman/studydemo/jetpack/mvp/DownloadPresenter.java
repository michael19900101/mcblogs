package com.aotuman.studydemo.jetpack.mvp;

/**
 * 我们在DownloadPresenter的构造方法中，同时实例化了Model和View，这样Presenter中就同时包含了两者；
 * 这样，在Presenter具体实现中，业务相关的操作由Model去完成（例如download），
 * 视图相关的操作由View去完成（如setView等）。
 * Presenter 作为桥梁的作用就这样体现出来了，巧妙的将View和Model的具体实现连接了起来。
 */
public class DownloadPresenter implements IDowndownPresenter {
    private IDownloadView mIDownloadView;
    private IDownloadModel mIDownloadModel;


    public DownloadPresenter(IDownloadView IDownloadView) {
        mIDownloadView = IDownloadView;
        mIDownloadModel = new DownloadModel(this);
    }

    @Override
    public void download(String url) {
        mIDownloadView.showProgressBar(true);
        mIDownloadModel.download(url);
    }

    @Override
    public void downloadSuccess(String result) {
        mIDownloadView.showProgressBar(false);
        mIDownloadView.setView(result);
    }

    @Override
    public void downloadProgress(int progress) {
        mIDownloadView.setProcessProgress(progress);
    }

    @Override
    public void downloadFail() {
        mIDownloadView.showProgressBar(false);
        mIDownloadView.showFailToast();
    }
}