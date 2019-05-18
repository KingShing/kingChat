package sit.kingshing.factory.model.card;

import com.google.gson.annotations.Expose;

import java.util.Date;

import sit.kingshing.factory.model.db.Comment;

public class CommentCard {
    @Expose
    private String id; // Id
    @Expose
    private String speakerId;
    @Expose
    private String targetId;
    @Expose
    private String content;
    @Expose
    private Date commentDate;
    @Expose
    private int likeCount;
    @Expose
    private String feedId;

    public CommentCard(Comment comment) {
        this.id = comment.getId();
        this.speakerId = comment.getSpeaker().getId();
        this.targetId = comment.getTarget().getId();
        this.content = comment.getContent();
        this.commentDate = comment.getCommentDate();
        this.likeCount = comment.getLikeCount();
        this.feedId = comment.getFeed().getId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpeakerId() {
        return speakerId;
    }

    public void setSpeakerId(String speakerId) {
        this.speakerId = speakerId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
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

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getFeedId() {
        return feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }
}
