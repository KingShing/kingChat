package sit.kingshing.factory.data.feed;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import sit.kingshing.factory.data.helper.DbHelper;
import sit.kingshing.factory.data.helper.UserHelper;
import sit.kingshing.factory.data.user.UserDispatcher;
import sit.kingshing.factory.model.card.FeedCard;
import sit.kingshing.factory.model.db.Feed;

public class FeedDispatcher implements FeedCenter{
    private static FeedCenter instance;
    // 单线程池；处理卡片一个个的消息进行处理
    private final Executor executor = Executors.newSingleThreadExecutor();

    public static FeedCenter instance() {
        if (instance == null) {
            synchronized (UserDispatcher.class) {
                if (instance == null)
                    instance = new FeedDispatcher();
            }
        }
        return instance;
    }

    @Override
    public void dispatch(FeedCard... cards) {
        if (cards == null || cards.length == 0)
            return;

        // 丢到单线程池中
        executor.execute(new FeedCardHandler(cards));
    }



    /**
     * 线程调度的时候会触发run方法
     */
    private class FeedCardHandler implements Runnable {
        private final FeedCard[] cards;

        FeedCardHandler(FeedCard[] cards) {
            this.cards = cards;
        }

        // feed卡片有可能是推送过来的，也有可能是直接造的
        // 推送来的代表服务器一定有，我们可以查询到（本地有可能有，有可能没有）
        // 如果是直接造的，那么先存储本地，后发送网络
        // 发送消息流程：写消息->存储本地->发送网络->网络返回->刷新本地状态

        @Override
        public void run() {
            // 单被线程调度的时候触发
            List<Feed> feeds = new ArrayList<>();
            for (FeedCard card : cards) {
                // 进行过滤操作
                if (card == null || TextUtils.isEmpty(card.getId()))
                    continue;
                // 添加操作
                feeds.add(card.build(UserHelper.search(card.getPubId())));
            }
            // 进行数据库存储，并分发通知, 异步的操作
            DbHelper.save(Feed.class, feeds.toArray(new Feed[0]));
        }
    }
}
