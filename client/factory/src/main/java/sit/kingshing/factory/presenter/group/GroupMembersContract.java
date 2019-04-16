package sit.kingshing.factory.presenter.group;


import sit.kingshing.factory.model.db.view.MemberUserModel;
import sit.kingshing.factory.presenter.BaseContract;

/**
 * 群成员的契约
 *
 */
public interface GroupMembersContract {
    interface Presenter extends BaseContract.Presenter {
        // 具有一个刷新的方法
        void refresh();
    }

    // 界面
    interface View extends BaseContract.RecyclerView<Presenter, MemberUserModel> {
        // 获取群的ID
        String getGroupId();
    }
}
