package sit.kingshing.factory.presenter.feed;

import android.text.TextUtils;
import android.util.Log;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.List;

import sit.kingshing.factory.R;
import sit.kingshing.factory.data.DataSource;
import sit.kingshing.factory.data.helper.FeedHelper;
import sit.kingshing.factory.model.card.FeedCard;
import sit.kingshing.factory.presenter.BasePresenter;

public class FeedCreatePresenter extends BasePresenter<FeedCreateContract.View> implements FeedCreateContract.Presenter, DataSource.Callback<FeedCard> {
    public FeedCreatePresenter(FeedCreateContract.View view) {
        super(view);
    }

    @Override
    public void create(final String content, final List<String> pictures) {
        start();

        final FeedCreateContract.View view = getView();

        if (TextUtils.isEmpty(content)) {
            view.showError(R.string.data_feed_create_invalid_parameter);
        } else {
            //上传
            Log.d("FeedCreatePresenter", "create: " + pictures);
            FeedHelper.uploadAsynFeedPicture(pictures, content, FeedCreatePresenter.this);
        }
    }

    @Override
    public void onDataLoaded(FeedCard feedCard) {


        final FeedCreateContract.View view = getView();
        if (view == null)
            return;
        // 强制执行在主线程中
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.onCreateSucceed();
            }
        });
    }

    @Override
    public void onDataNotAvailable(final int strRes) {
        final FeedCreateContract.View view = getView();
        if (view == null)
            return;
        // 强制执行在主线程中
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.showError(strRes);
            }
        });
    }


}
