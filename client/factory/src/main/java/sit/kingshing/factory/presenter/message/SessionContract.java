package sit.kingshing.factory.presenter.message;


import sit.kingshing.factory.model.db.Session;
import sit.kingshing.factory.presenter.BaseContract;


public interface SessionContract {
    // 什么都不需要额外定义，开始就是调用start即可
    interface Presenter extends BaseContract.Presenter {

    }

    // 都在基类完成了
    interface View extends BaseContract.RecyclerView<Presenter, Session> {

    }
}
