package sit.kingshing.kingchat.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import sit.kingshing.common.app.PresenterToolbarActivity;
import sit.kingshing.common.widget.PortraitView;
import sit.kingshing.common.widget.nineGrideView.LGNineGrideView;
import sit.kingshing.common.widget.recycler.RecyclerAdapter;
import sit.kingshing.factory.Factory;
import sit.kingshing.factory.model.card.CommentCard;
import sit.kingshing.factory.model.db.Feed;
import sit.kingshing.factory.model.db.User;
import sit.kingshing.factory.persistence.Account;
import sit.kingshing.factory.presenter.feed.FeedContract;
import sit.kingshing.factory.presenter.feed.FeedPresenter;
import sit.kingshing.kingchat.R;

/**
 * 朋友圈 TODO
 */
public class MomentActivity extends PresenterToolbarActivity<FeedContract.Presenter>
        implements FeedContract.View<Feed> {

    private RecyclerAdapter<Feed> mAdapter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.im_portrait)
    PortraitView im_portrait;
    @BindView(R.id.txt_name)
    TextView name;

    @BindView(R.id.im_header)
    ImageView im_header;


    public static void show(Context context) {
        context.startActivity(new Intent(context, MomentActivity.class));
    }

    @Override
    protected void onNewIntent(Intent intent) {//只有栈内有activity实例才会被调用
        super.onNewIntent(intent);
        mPresenter.freshFeedList(FeedContract.RefreshMethod.LOCAL);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_moment;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mRecyclerView.setLayoutManager( new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter = new RecyclerAdapter<Feed>() {
            @Override
            protected int getItemViewType(int position, Feed feed) {
                return R.layout.cell_moment;
            }

            @Override
            protected ViewHolder onCreateViewHolder(View root, int viewType) {
                return new MomentActivity.ViewHolder(root);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {

        // 初始化菜单
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.feed_create, menu);

        // 找到发布菜单
        MenuItem publishItem = menu.findItem(R.id.action_publish);

        publishItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                FeedCreateActivity.show(MomentActivity.this);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void initData() {
        super.initData();
        //初始化顶部个人信息
        initPersonData();
        //初始化朋友圈feed
        mPresenter.freshFeedList(FeedContract.RefreshMethod.ALL);
    }

    private void initPersonData() {
        User self = Account.getUser();
        im_portrait.setUp(Glide.with(this), self.getPortrait());
        name.setText(self.getName());

    }


    @Override
    public RecyclerAdapter<Feed> getRecyclerAdapter() {
        return this.mAdapter;
    }

    @Override
    public void onAdapterDataChanged() {
        mRecyclerView.scrollToPosition(0);
    }

    @Override
    protected FeedContract.Presenter initPresenter() {
        return new FeedPresenter(this);
    }

    @Override
    public void commentSuccess(CommentCard commentCard) {

    }


    static class ViewHolder extends RecyclerAdapter.ViewHolder<Feed> {

        @BindView(R.id.im_portrait)
        PortraitView mPortraitView;

        @BindView(R.id.txt_name)
        TextView name;

        @BindView(R.id.txt_time)
        TextView time;

        @BindView(R.id.txt_content)
        TextView content;

        @BindView(R.id.nineGrideView)
        LGNineGrideView nineGrideView;

        @OnClick(R.id.im_praise)
        void praiseClick() {

        }

        ;

        @OnClick(R.id.im_comment)
        void commentClick() {

        }

        ;

        @OnClick(R.id.im_share)
        void shareClick() {

        }

        ;

        public ViewHolder(@NonNull View root) {
            super(root);

        }

        @Override
        protected void onBind(Feed feed) {
            // final String pubId = feedCard.getPubId();
            Log.i("onBind-Feed", feed.toString());
            User sender = feed.getSender();
            sender.load();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm");
            String ltime = format.format(feed.getCreateDate());

            mPortraitView.setUp(Glide.with(Factory.app()), sender.getPortrait());
            name.setText(feed.getSender().getName());
            time.setText(ltime);
            final List<String> convert = convert(feed.getAttach());
            Log.i("onBind-covert-list: ", convert.size() + "");
            nineGrideView.setUrls(convert);
            content.setText(feed.getContent());
        }
    }


    public static List<String> convert(String strs) {
        if (strs == null) return null;
        strs = strs.trim();
        final ArrayList<String> strings = new ArrayList<>();
        String res[] = Factory.getGson().fromJson(strs, String[].class);
        return Arrays.asList(res);
    }

}
