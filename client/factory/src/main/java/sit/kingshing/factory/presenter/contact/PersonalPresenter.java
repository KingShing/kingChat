package sit.kingshing.factory.presenter.contact;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import sit.kingshing.factory.Factory;
import sit.kingshing.factory.data.DataSource;
import sit.kingshing.factory.data.helper.UserHelper;
import sit.kingshing.factory.model.card.UserCard;
import sit.kingshing.factory.model.db.User;
import sit.kingshing.factory.persistence.Account;
import sit.kingshing.factory.presenter.BasePresenter;

public class PersonalPresenter extends BasePresenter<PersonalContract.View>
        implements PersonalContract.Presenter, DataSource.Callback<UserCard> {

    private User user;

    public PersonalPresenter(PersonalContract.View view) {
        super(view);
    }


    @Override
    public void start() {
        super.start();

        // 个人界面用户数据优先从网络拉取
        Factory.runOnAsync(new Runnable() {
            @Override
            public void run() {
                PersonalContract.View view = getView();
                if (view != null) {
                    String id = view.getUserId();
                    User user = UserHelper.searchFirstOfNet(id);
                    onLoaded(view, user);
                }
            }
        });

    }


    private void onLoaded(final PersonalContract.View view, final User user) {
        this.user = user;
        // 是否就是我自己
        final boolean isSelf = user.getId().equalsIgnoreCase(Account.getUserId());
        // 是否已经关注
        final boolean isFollow = isSelf || user.isFollow();
        // 已经关注同时不是自己才能聊天
        final boolean allowSayHello = isFollow && !isSelf;

        // 切换到Ui线程
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.onLoadDone(user);
                view.setFollowStatus(isFollow);
                view.allowSayHello(allowSayHello);
            }
        });
    }

    @Override
    public User getUserPersonal() {
        return user;
    }

    @Override
    public void follow(String id) {
        UserHelper.follow(id, this);
    }

    @Override
    public void onDataLoaded(final UserCard userCard) {
        final PersonalContract.View view = getView();
        if (view == null)
            return;
        // 强制执行在主线程中
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.onFollowSuccess(userCard);
            }
        });

    }

    @Override
    public void onDataNotAvailable(final int strRes) {
        final PersonalContract.View view = getView();
        if (view == null)
            return;
        // 强制执行在主线程中
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.onFollowFailed(strRes);
            }
        });
    }
}
