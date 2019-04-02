package sit.kingshing.factory.presenter.contact;

import sit.kingshing.factory.model.db.User;
import sit.kingshing.factory.presenter.BaseContract;

public interface ContactContract {

    interface Presenter extends BaseContract.Presenter{

    }


    interface View extends BaseContract.RecyclerView<Presenter, User>{

    }
}
