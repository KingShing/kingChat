package sit.kingshing.factory.model.card;

import com.google.gson.annotations.Expose;

import java.util.Date;

import sit.kingshing.factory.model.db.Feed;
import sit.kingshing.factory.model.db.User;

public class FeedCard {
    @Expose
    private String id; // Id
    @Expose
    private String content;// 内容
    @Expose
    private Date createAt;// 创建时间
    @Expose
    private String pictures;
    @Expose
    private String pubId;

    public FeedCard(Feed feed) {
        this.id = feed.getId();
        this.content = feed.getContent();
        this.pubId = feed.getSender().getId();
        this.createAt = feed.getCreateDate();
        this.pictures = feed.getAttach();

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public String getPubId() {
        return pubId;
    }

    public void setPubId(String pubId) {
        this.pubId = pubId;
    }

    public Feed build(User sender) {
        Feed feed = new Feed();
        feed.setId(id);
        feed.setAttach(pictures);
        feed.setSender(sender);
        feed.setContent(content);
        feed.setCreateDate(createAt);
        return feed;
    }
}
