package sit.kingshing.kingchat.fragment.search;


import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.qiujuer.genius.ui.Ui;
import net.qiujuer.genius.ui.compat.UiCompat;
import net.qiujuer.genius.ui.drawable.LoadingCircleDrawable;
import net.qiujuer.genius.ui.drawable.LoadingDrawable;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import sit.kingshing.common.app.PresenterFragment;
import sit.kingshing.common.widget.EmptyView;
import sit.kingshing.common.widget.PortraitView;
import sit.kingshing.common.widget.recycler.RecyclerAdapter;
import sit.kingshing.factory.model.card.UserCard;
import sit.kingshing.factory.presenter.contact.FollowContract;
import sit.kingshing.factory.presenter.contact.FollowPresenter;
import sit.kingshing.factory.search.SearchContract;
import sit.kingshing.factory.search.SearchUserPresenter;
import sit.kingshing.kingchat.R;
import sit.kingshing.kingchat.activities.PersonalActivity;
import sit.kingshing.kingchat.activities.SearchActivity;


public class SearchUserFragment extends PresenterFragment<SearchContract.Presenter>
        implements SearchContract.UserView,SearchActivity.SearchFragment {

    private RecyclerAdapter mAdapter;

    @BindView(R.id.empty)
    EmptyView mEmptyView;

    @BindView(R.id.recycler)
    RecyclerView recycler;



    public SearchUserFragment() {
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_search_user;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(mAdapter = new RecyclerAdapter<UserCard>() {
            @Override
            protected int getItemViewType(int position, UserCard userCard) {
                return R.layout.cell_search_list;
            }

            @Override
            protected ViewHolder<UserCard> onCreateViewHolder(View root, int viewType) {
                return new SearchUserFragment.ViewHolder(root);
            }
        });

        mEmptyView.bind(recycler);
        setPlaceHolder(mEmptyView);
    }

    @Override
    protected void initData() {
        super.initData();
        search("");
    }

    @Override
    public void search(String content) {
        mPresenter.search(content);

    }

    @Override
    protected SearchContract.Presenter initPresenter() {
        return new SearchUserPresenter(this);

    }

    @Override
    public void onSearchDone(List<UserCard> userCards) {
        mAdapter.replace(userCards);
        mPlaceHolder.triggerOkOrEmpty(mAdapter.getItemCount()>0);
    }


    class ViewHolder extends RecyclerAdapter.ViewHolder<UserCard> implements FollowContract.View {

        @BindView(R.id.im_portrait)
        PortraitView mPortraitView;

        @BindView(R.id.txt_name)
        TextView mTextView;

        @BindView(R.id.im_follow)
        ImageView mFollow;


        @OnClick(R.id.im_portrait)
        void onPortraitClick(){
            PersonalActivity.show(getContext(),mData.getId());
        }


        @OnClick(R.id.im_follow)
        void onfollowClick(){
            mPresenter.follow(mData.getId());
        }

        private FollowContract.FollowPresenter mPresenter;

        public ViewHolder(@NonNull View root) {
            super(root);
            //view 和 presenter绑定
            new FollowPresenter(this);
        }

        @Override
        protected void onBind(UserCard userCard) {
            mPortraitView.setUp(Glide.with(getContext()),userCard);
            mTextView.setText(userCard.getName());
            mFollow.setEnabled(!userCard.isFollow());
        }

        @Override
        public void onFollowSucceed(UserCard userCard) {
            // 更改当前界面状态
            if (mFollow.getDrawable() instanceof LoadingDrawable) {
                ((LoadingDrawable) mFollow.getDrawable()).stop();
                // 设置为默认的
                mFollow.setImageResource(R.drawable.sel_opt_done_add);
            }
            // 发起更新
            update(userCard);
        }

        @Override
        public void showError(int str) {
            // 更改当前界面状态
            if (mFollow.getDrawable() instanceof LoadingDrawable) {
                // 失败则停止动画，并且显示一个圆圈
                LoadingDrawable drawable = (LoadingDrawable) mFollow.getDrawable();
                drawable.setProgress(1);
                drawable.stop();
            }
        }

        @Override
        public void showLoading() {
            int minSize = (int) Ui.dipToPx(getResources(), 22);
            int maxSize = (int) Ui.dipToPx(getResources(), 30);
            // 初始化一个圆形的动画的Drawable
            LoadingDrawable drawable = new LoadingCircleDrawable(minSize, maxSize);
            drawable.setBackgroundColor(0);

            int[] color = new int[]{UiCompat.getColor(getResources(), R.color.white_alpha_208)};
            drawable.setForegroundColor(color);
            // 设置进去
            mFollow.setImageDrawable(drawable);
            // 启动动画
            drawable.start();
        }

        @Override
        public void setPresenter(FollowContract.FollowPresenter presenter) {
            mPresenter = presenter;
        }
    }
}
