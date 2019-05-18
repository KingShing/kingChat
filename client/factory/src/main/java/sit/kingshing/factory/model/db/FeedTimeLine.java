package sit.kingshing.factory.model.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

/**
 *  我的朋友圈列表
 */
@Table(database = AppDatabase.class)
public class FeedTimeLine extends BaseModel {

    @Column
    @PrimaryKey
    String id;

    // 谁的朋友圈
    @Column
    @ForeignKey(tableClass = User.class, stubbedRelationship = true)
    User ower;


    //我能够看到的所有动态
    @Column
    @ForeignKey(tableClass = Feed.class, stubbedRelationship = true)
    Feed feed;

    @Column
    Date updateDate;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getOwer() {
        return ower;
    }

    public void setOwer(User ower) {
        this.ower = ower;
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
