package sit.kingshing.common.app;

import android.content.Context;

import sit.kingshing.factory.presenter.BaseContract;


public abstract class PresenterFragment<Presenter extends BaseContract.Presenter> extends Fragment
        implements BaseContract.View<Presenter> {

    protected Presenter mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // 在界面onAttach之后就触发初始化Presenter
        initPresenter();
    }

    /**
     * 初始化Presenter
     *
     * @return Presenter
     */
    protected abstract Presenter initPresenter();

    @Override
    public void showError(int str) {

        if (mPlaceHolder != null) {
            mPlaceHolder.triggerError(str);
        } else {
            Application.showToast(str);
        }

    }

    @Override
    public void showLoading() {
        if (mPlaceHolder != null) {
            mPlaceHolder.triggerLoading();
        }
        // TODO 显示一个Loading
    }

    @Override
    public void setPresenter(Presenter presenter) {
        // View中赋值Presenter
        mPresenter = presenter;
    }
}
