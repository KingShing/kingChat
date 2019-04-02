package sit.kingshing.kingchat.fragment.message;


import sit.kingshing.factory.model.db.Group;
import sit.kingshing.factory.presenter.message.ChatContract;
import sit.kingshing.kingchat.R;

/**
 * 群聊天界面实现
 */
public class ChatGroupFragment extends ChatFragment<Group>
        implements ChatContract.GroupView {


    public ChatGroupFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_chat_group;
    }

    @Override
    protected ChatContract.Presenter initPresenter() {
        return null;
    }

    @Override
    public void onInit(Group group) {

    }
}
