package sit.kingshing.factory.presenter.feed;

import java.util.List;

import sit.kingshing.factory.presenter.BaseContract;

public interface FeedCreateContract {
    interface Presenter extends BaseContract.Presenter {
        // 创建
        void create(String content, List<String> picture);

    }

    interface View extends BaseContract.View<Presenter> {
        // 创建成功
        void onCreateSucceed();
    }
}