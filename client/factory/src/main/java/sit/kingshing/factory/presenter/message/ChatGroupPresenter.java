package sit.kingshing.factory.presenter.message;


import java.util.List;

import sit.kingshing.factory.data.helper.GroupHelper;
import sit.kingshing.factory.data.message.MessageGroupRepository;
import sit.kingshing.factory.model.db.Group;
import sit.kingshing.factory.model.db.Message;
import sit.kingshing.factory.model.db.view.MemberUserModel;
import sit.kingshing.factory.persistence.Account;

/**
 * 群聊天的逻辑
 *
 */
public class ChatGroupPresenter extends ChatPresenter<ChatContract.GroupView>
        implements ChatContract.Presenter {

    public ChatGroupPresenter(ChatContract.GroupView view, String receiverId) {
        // 数据源，View，接收者，接收者的类型
        super(new MessageGroupRepository(receiverId), view, receiverId, Message.RECEIVER_TYPE_GROUP);
    }

    @Override
    public void start() {
        super.start();

        // 拿群的信息
        Group group = GroupHelper.findFromLocal(mReceiverId);
        if (group != null) {
            // 初始化操作
            ChatContract.GroupView view = getView();

            boolean isAdmin = Account.getUserId().equalsIgnoreCase(group.getOwner().getId());
            view.showAdminOption(isAdmin);

            // 基础信息初始化
            view.onInit(group);

            // 成员初始化
            List<MemberUserModel> models = group.getLatelyGroupMembers();
            final long memberCount = group.getGroupMemberCount();
            // 没有显示的成员的数量
            long moreCount = memberCount - models.size();
            view.onInitGroupMembers(models, moreCount);
        }

    }
}