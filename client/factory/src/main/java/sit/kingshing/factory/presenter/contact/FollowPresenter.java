package sit.kingshing.factory.presenter.contact;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import sit.kingshing.factory.data.DataSource;
import sit.kingshing.factory.data.helper.UserHelper;
import sit.kingshing.factory.model.card.UserCard;
import sit.kingshing.factory.presenter.BasePresenter;

public class FollowPresenter extends BasePresenter<FollowContract.View>
        implements FollowContract.FollowPresenter, DataSource.Callback<UserCard> {

    public FollowPresenter(FollowContract.View view) {
        super(view);
    }

    @Override
    public void follow(String id) {
        UserHelper.follow(id, this);
    }

    @Override
    public void onDataLoaded(final UserCard userCard) {
        final FollowContract.View  view = getView();
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                if (view != null) {
                    view.onFollowSucceed(userCard);
                }
            }
        });
    }

    @Override
    public void onDataNotAvailable(final int strRes) {
        final FollowContract.View  view = getView();
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                if (view != null) {
                    view.showError(strRes);
                }
            }
        });
    }
}
