package sit.kingshing.factory.model.api.moment;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;

public class FTLCreateModel {
    // ID从客户端生产，一个UUID
    @Expose
    private String id;
    @Expose
    private String userId;
    @Expose
    private String feedId;

    public static boolean check(FTLCreateModel model){
        return model != null
                && !TextUtils.isEmpty(model.id)
                && !TextUtils.isEmpty(model.userId)
                && !TextUtils.isEmpty(model.feedId);
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
}
