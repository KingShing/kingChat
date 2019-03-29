package sit.kingshing.factory.search;


import java.util.List;

import sit.kingshing.factory.model.card.GroupCard;
import sit.kingshing.factory.model.card.UserCard;
import sit.kingshing.factory.presenter.BaseContract;


public interface SearchContract {
    interface Presenter extends BaseContract.Presenter {
        // 搜索内容
        void search(String content);
    }

    // 搜索人的界面
    interface UserView extends BaseContract.View<Presenter> {
        void onSearchDone(List<UserCard> userCards);
    }

    // 搜索群的界面
    interface GroupView extends BaseContract.View<Presenter> {
        void onSearchDone(List<GroupCard> groupCards);
    }

}
