package sit.kingshing.factory.presenter.contact;

import sit.kingshing.factory.model.card.UserCard;
import sit.kingshing.factory.presenter.BaseContract;

public interface FollowContract {

    interface FollowPresenter extends BaseContract.Presenter {
        void follow(String id);
    }


    interface View extends BaseContract.View<FollowPresenter>{
        //成功返回关注的人的信息
        void onFollowSucceed(UserCard userCard);
    }

}
