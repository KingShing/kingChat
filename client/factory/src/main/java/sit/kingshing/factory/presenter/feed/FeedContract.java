package sit.kingshing.factory.presenter.feed;

import sit.kingshing.factory.model.card.CommentCard;
import sit.kingshing.factory.presenter.BaseContract;

public interface FeedContract {

    interface Presenter extends BaseContract.Presenter {

        // 获取动态
        void freshFeedList(RefreshMethod method);

        //   评论
        void commentFeed(String content, String targetId);

        //   点赞
        void praiseFeed(String feedId);

        //   转发
        void shareFeed(String FeedId, String targetId);
    }


    interface View<ViewModel> extends BaseContract.RecyclerView<Presenter, ViewModel> {
        // 父类有适配器，数据更新不是问题

        void commentSuccess(CommentCard commentCard);

    }


    enum RefreshMethod{
        LOCAL,
        NETWORK,
        ALL,
    }
}
