package sit.kingshing.kingchat.fragment.search;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import sit.kingshing.common.app.PresenterFragment;
import sit.kingshing.common.widget.EmptyView;
import sit.kingshing.common.widget.PortraitView;
import sit.kingshing.common.widget.recycler.RecyclerAdapter;
import sit.kingshing.factory.model.card.GroupCard;
import sit.kingshing.factory.search.SearchContract;
import sit.kingshing.factory.search.SearchGroupPresenter;
import sit.kingshing.kingchat.R;
import sit.kingshing.kingchat.activities.PersonalActivity;
import sit.kingshing.kingchat.activities.SearchActivity;

/**
 * 搜索群的界面实现
 */
public class SearchGroupFragment extends PresenterFragment<SearchContract.Presenter>
        implements SearchActivity.SearchFragment, SearchContract.GroupView {

    @BindView(R.id.empty)
    EmptyView mEmptyView;

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    private RecyclerAdapter<GroupCard> mAdapter;

    public SearchGroupFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_search_group;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        // 初始化Recycler
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(mAdapter = new RecyclerAdapter<GroupCard>() {
            @Override
            protected int getItemViewType(int position, GroupCard userCard) {
                // 返回cell的布局id
                return R.layout.cell_search_group_list;
            }

            @Override
            protected ViewHolder<GroupCard> onCreateViewHolder(View root, int viewType) {
                return new SearchGroupFragment.ViewHolder(root);
            }
        });

        // 初始化占位布局
        mEmptyView.bind(mRecycler);
        setPlaceHolder(mEmptyView);
    }

    @Override
    protected void initData() {
        super.initData();
        // 发起首次搜索
        search("");
    }

    @Override
    public void search(String content) {
        mPresenter.search(content);
    }

    @Override
    protected SearchContract.Presenter initPresenter() {
        return new SearchGroupPresenter(this);
    }

    @Override
    public void onSearchDone(List<GroupCard> groupCards) {
        // 数据成功的情况下返回数据
        mAdapter.replace(groupCards);
        // 如果有数据，则是OK，没有数据就显示空布局
        mPlaceHolder.triggerOkOrEmpty(mAdapter.getItemCount() > 0);
    }

    /**
     * 每一个Cell的布局操作
     */
    class ViewHolder extends RecyclerAdapter.ViewHolder<GroupCard> {
        @BindView(R.id.im_portrait)
        PortraitView mPortraitView;

        @BindView(R.id.txt_name)
        TextView mName;

        @BindView(R.id.im_join)
        ImageView mJoin;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(GroupCard groupCard) {
            mPortraitView.setUp(Glide.with(SearchGroupFragment.this), groupCard.getPicture());
            mName.setText(groupCard.getName());
            // 加入时间判断是否加入群
            mJoin.setEnabled(groupCard.getJoinAt() == null);
        }

        @OnClick(R.id.im_join)
        void onJoinClick() {
            // 进入创建者的个人界面
            PersonalActivity.show(getContext(), mData.getOwnerId());
        }
    }
}