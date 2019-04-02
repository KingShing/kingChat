package sit.kingshing.kingchat.fragment.main;


import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.OnClick;
import sit.kingshing.common.app.PresenterFragment;
import sit.kingshing.common.widget.EmptyView;
import sit.kingshing.common.widget.PortraitView;
import sit.kingshing.common.widget.recycler.RecyclerAdapter;
import sit.kingshing.factory.model.db.User;
import sit.kingshing.factory.presenter.contact.ContactContract;
import sit.kingshing.factory.presenter.contact.ContactPresenter;
import sit.kingshing.kingchat.R;
import sit.kingshing.kingchat.activities.MessageActivity;
import sit.kingshing.kingchat.activities.PersonalActivity;


public class ContactFragment extends PresenterFragment<ContactContract.Presenter> implements ContactContract.View {
    @BindView(R.id.empty)
    EmptyView mEmptyView;

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    // 适配器，User，可以直接从数据库查询数据
    private RecyclerAdapter<User> mAdapter;

    public ContactFragment() {
    }


    @Override
    protected void onFirstInit() {
        super.onFirstInit();
        mPresenter.start();
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        // 初始化Recycler
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(mAdapter = new RecyclerAdapter<User>() {
            @Override
            protected int getItemViewType(int position, User userCard) {
                // 返回cell的布局id
                return R.layout.cell_contact_list;
            }

            @Override
            protected ViewHolder<User> onCreateViewHolder(View root, int viewType) {
                return new ContactFragment.ViewHolder(root);
            }
        });

        // 点击事件监听
        mAdapter.setListener(new RecyclerAdapter.AdapterListenerImpl<User>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, User user) {
                // 跳转到聊天界面
                MessageActivity.show(getContext(), user);
            }
        });

        // 初始化占位布局
        mEmptyView.bind(mRecycler);
        setPlaceHolder(mEmptyView);
    }

    @Override
    protected ContactContract.Presenter initPresenter() {
        return new ContactPresenter(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_contact;
    }

    @Override
    public RecyclerAdapter<User> getRecyclerAdapter() {
        return mAdapter;
    }

    @Override
    public void onAdapterDataChanged() {
        mPlaceHolder.triggerOkOrEmpty(mAdapter.getItemCount() > 0);
    }

    class ViewHolder extends RecyclerAdapter.ViewHolder<User> {

        @BindView(R.id.im_portrait)
        PortraitView mPortraitView;
        @BindView(R.id.txt_name)
        TextView mName;
        @BindView(R.id.txt_desc)
        TextView mDesc;

        @OnClick(R.id.cell_contact_list)
        void onclick() {
            PersonalActivity.show(getContext(),mData.getId());
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(User user) {
            mPortraitView.setUp(Glide.with(getContext()), user);
            mName.setText(user.getName());
            mDesc.setText(user.getDesc());
        }
    }
}
