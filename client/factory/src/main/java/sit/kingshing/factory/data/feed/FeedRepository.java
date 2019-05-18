package sit.kingshing.factory.data.feed;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import sit.kingshing.factory.data.BaseDbRepository;
import sit.kingshing.factory.model.db.Feed;
import sit.kingshing.factory.model.db.Feed_Table;

public class FeedRepository extends BaseDbRepository<Feed> implements FeedDataSource{

    @Override
    public void load(SucceedCallback<List<Feed>> callback) {
        super.load(callback);

        // 加载本地数据库数据
        SQLite.select()
                .from(Feed.class)
                .orderBy(Feed_Table.createDate, true)
                //.limit(100)
                .async()
                .queryListResultCallback(this)
                .execute();
    }

    @Override
    protected boolean isRequired(Feed feed) {
        // 全部
        return  true;
       // return  feed.getSender().getId().equals(Account.getUserId());
    }
}
