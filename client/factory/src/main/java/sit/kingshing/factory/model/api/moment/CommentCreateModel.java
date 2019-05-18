package sit.kingshing.factory.model.api.moment;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;

public class CommentCreateModel {
    // ID从客户端生产，一个UUID
    @Expose
    private String id; // Id
    @Expose
    private String speakerId;
    @Expose
    private String targetId;
    @Expose
    private String content;
    @Expose
    private int likeCount;
    @Expose
    private String feedId;



    public static boolean check(CommentCreateModel model){
        return model != null
                && !TextUtils.isEmpty(model.id)
                && !TextUtils.isEmpty(model.content)
                && !TextUtils.isEmpty(model.speakerId)
                && !TextUtils.isEmpty(model.targetId);
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
