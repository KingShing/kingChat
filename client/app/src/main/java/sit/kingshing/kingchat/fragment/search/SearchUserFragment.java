package sit.kingshing.kingchat.fragment.search;


import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
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
        ImageView mImageView;


        private FollowContract.FollowPresenter mPresenter;

        public ViewHolder(@NonNull View root) {
            super(root);
            //view 和 presenter绑定
            new FollowPresenter(this);
        }

        @Override
        protected void onBind(UserCard userCard) {

            Glide.with(getContext())
                    .load(userCard.getPortrait())
                    .centerCrop()
                    .into(mImageView);
            mTextView.setText(userCard.getName());
            mImageView.setEnabled(userCard.isFollow());
        }

        @Override
        public void onFollowSucceed(UserCard userCard) {

        }

        @Override
        public void showError(int str) {

        }

        @Override
        public void showLoading() {

        }

        @Override
        public void setPresenter(FollowContract.FollowPresenter presenter) {
            mPresenter = presenter;
        }
    }
}
