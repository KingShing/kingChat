package sit.kingshing.factory.presenter.account;


import sit.kingshing.factory.presenter.BaseContract;


public interface LoginContract {
    interface View extends BaseContract.View<Presenter> {
        // 登录成功
        void loginSuccess();
    }


    interface Presenter extends BaseContract.Presenter {
        // 发起一个登录
        void login(String phone, String password);
    }

}
