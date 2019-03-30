package sit.kingshing.factory.presenter.contact;


import android.support.annotation.StringRes;

import sit.kingshing.factory.model.card.UserCard;
import sit.kingshing.factory.model.db.User;
import sit.kingshing.factory.presenter.BaseContract;

public interface PersonalContract {
    interface Presenter extends BaseContract.Presenter {
        // 获取用户信息
        User getUserPersonal();

        //关注别人
        void follow(String id);
    }

    interface View extends BaseContract.View<Presenter> {
        String getUserId();

        // 加载数据完成
        void onLoadDone(User user);

        // 是否发起聊天
        void allowSayHello(boolean isAllow);

        //关注成功
        void onFollowSuccess(UserCard userCard);

        //关注失败
        void onFollowFailed(@StringRes int errStr);

        // 设置关注状态
        void setFollowStatus(boolean isFollow);
    }
}
