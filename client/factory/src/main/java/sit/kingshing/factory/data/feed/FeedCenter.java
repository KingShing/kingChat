package sit.kingshing.factory.data.feed;

import sit.kingshing.factory.model.card.FeedCard;

public interface FeedCenter {

    // 分发处理一堆feed卡片的信息，并更新到数据库
    void dispatch(FeedCard... feedCard);


}
