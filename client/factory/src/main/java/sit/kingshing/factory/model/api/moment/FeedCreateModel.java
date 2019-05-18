package sit.kingshing.factory.model.api.moment;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;

import java.util.UUID;

public class FeedCreateModel {

    // ID从客户端生产，一个UUID
    @Expose
    private String id;
    @Expose
    private String content;// 内容 not null
    @Expose
    private String pictures;

    public FeedCreateModel( String content, String pictures) {
        this.id = UUID.randomUUID().toString();
        this.content = content;
        this.pictures = pictures;
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



    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }


    public static boolean check(FeedCreateModel model) {
        return model != null
                && !TextUtils.isEmpty(model.id)
                && !TextUtils.isEmpty(model.content);
    }


    @Override
    public String toString() {
        return "FeedCreateModel{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", pictures='" + pictures + '\'' +
                '}';
    }
}
