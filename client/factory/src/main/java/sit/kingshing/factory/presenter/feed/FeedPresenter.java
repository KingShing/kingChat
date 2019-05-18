package sit.kingshing.factory.presenter.feed;

import android.support.v7.util.DiffUtil;

import java.util.List;

import sit.kingshing.common.widget.recycler.RecyclerAdapter;
import sit.kingshing.factory.Factory;
import sit.kingshing.factory.data.feed.FeedDataSource;
import sit.kingshing.factory.data.feed.FeedRepository;
import sit.kingshing.factory.data.helper.FeedHelper;
import sit.kingshing.factory.model.db.Feed;
import sit.kingshing.factory.presenter.BaseSourcePresenter;
import sit.kingshing.factory.utils.DiffUiDataCallback;

//TODO
public class FeedPresenter extends BaseSourcePresenter<Feed, Feed, FeedDataSource, FeedContract.View>
        implements FeedContract.Presenter {


    public FeedPresenter(FeedContract.View view) {
        super(new FeedRepository(), view);
    }


    /**
     * 更新feed 列表
     */
    @Override
    public void freshFeedList(FeedContract.RefreshMethod method) {
        switch (method) {
            case ALL: {
                freshFeedListFromLocal();
                freshFeedListFromNetwork();
                break;
            }
            case LOCAL: {
                freshFeedListFromLocal();
                break;
            }
            case NETWORK: {
                freshFeedListFromNetwork();
                break;
            }
        }

    }


    private void freshFeedListFromLocal() {
        FeedContract.View view = getView();
        RecyclerAdapter<Feed> adapter = view.getRecyclerAdapter();

        //拿到内存feed数据
        List<Feed> memoryFeeds = adapter.getItems();

        //拿到本地数据库feed数据
        List<Feed> localFeeds = FeedHelper.findVisibleFeedFromLocal();


        if (memoryFeeds == null || memoryFeeds.size() <= 0) {
            adapter.add(localFeeds);
        } else {
            // 进行数据对比
            DiffUtil.Callback callback = new DiffUiDataCallback<>(memoryFeeds, localFeeds);
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
            // 调用基类方法进行界面刷新
            refreshData(result, localFeeds);
        }
    }

    private void freshFeedListFromNetwork() {
        Factory.runOnAsync(new Runnable() {
            @Override
            public void run() {
                //TODO 这里的调用 应该是下拉刷新 而不是在init里
                FeedHelper.freshVisibleFeed();
            }
        });
    }


    @Override
    public void onDataLoaded(List<Feed> feeds) {
        // 无论怎么操作，数据变更，最终都会通知到这里来
        final FeedContract.View view = getView();
        if (view == null)
            return;

        RecyclerAdapter<Feed> adapter = view.getRecyclerAdapter();
        List<Feed> old = adapter.getItems();

        // 进行数据对比
        DiffUtil.Callback callback = new DiffUiDataCallback<>(old, feeds);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        // 调用基类方法进行界面刷新
        refreshData(result, feeds);
    }

    @Override
    public void commentFeed(String content, String targetId) {
        //TODO
    }

    @Override
    public void praiseFeed(String feedId) {
        //TODO
    }

    @Override
    public void shareFeed(String FeedId, String targetId) {
        //TODO
    }


}
