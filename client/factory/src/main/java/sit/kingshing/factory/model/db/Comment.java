package sit.kingshing.factory.model.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

@Table(database = AppDatabase.class)
public class Comment extends BaseModel {

    // 评论状态
    public static final int STATUS_DONE = 0; // 正常状态
    public static final int STATUS_CREATED = 1; // 创建状态
    public static final int STATUS_FAILED = 2; // 发送失败状态

    @Column
    @PrimaryKey()
    String id;

    @Column
    @ForeignKey(tableClass = User.class, stubbedRelationship = true)
    User speaker;

    @Column
    @ForeignKey(tableClass = User.class, stubbedRelationship = true)
    User target;

    @Column
    String content;

    @Column
    Date commentDate;

    @Column
    int status;

    @Column
    int likeCount;


    @Column
    @ForeignKey(tableClass = Feed.class)
    Feed feed;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getSpeaker() {
        return speaker;
    }

    public void setSpeaker(User speaker) {
        this.speaker = speaker;
    }

    public User getTarget() {
        return target;
    }

    public void setTarget(User target) {
        this.target = target;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }
}
