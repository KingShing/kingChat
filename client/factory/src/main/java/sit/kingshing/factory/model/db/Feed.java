package sit.kingshing.factory.model.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import java.util.Date;
import java.util.Objects;

@Table(database = AppDatabase.class)
public class Feed extends BaseDbModel<Feed> {
    // feed状态
    public static final int STATUS_DONE = 0; // 正常状态
    public static final int STATUS_CREATED = 1; // 创建状态
    public static final int STATUS_FAILED = 2; // 发送失败状态

    @Column
    @PrimaryKey
    String id;

    @Column
    String content;

    @Column
    String attach;

    @Column
    @ForeignKey(tableClass = User.class, stubbedRelationship = true)
    User sender;

    @Column
    Date createDate;

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

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public boolean isSame(Feed old) {
        // 主要关注Id即可
        return this == old || Objects.equals(id, old.id);
    }

    @Override
    public boolean isUiContentSame(Feed old) {
        // 显示的内容是否一样，主要内容 附件，发送者，创建时间
        return this == old || (
                Objects.equals(content, old.content)
                        && Objects.equals(attach, old.attach)
                        && Objects.equals(sender, old.sender)
                        && Objects.equals(createDate, old.createDate)
        );
    }


    @Override
    public String toString() {
        return "Feed{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", attach='" + attach + '\'' +
                ", sender=" + sender +
                ", createDate=" + createDate +
                '}';
    }
}
