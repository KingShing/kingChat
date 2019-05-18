package sit.kingshing.factory.model.card;

import com.google.gson.annotations.Expose;

import java.util.Date;

import sit.kingshing.factory.model.db.FeedTimeLine;

public class FeedTimeLineCard {
    @Expose
    private String id; // Id
    @Expose
    private String userId;
    @Expose
    private String feedId;

    @Expose
    private Date createAt;// 创建时间

    public FeedTimeLineCard(FeedTimeLine ftl) {
        this.id = ftl.getId();
        this.userId = ftl.getOwer().getId();
        this.feedId = ftl.getFeed().getId();
        this.createAt = ftl.getUpdateDate();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFeedId() {
        return feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
