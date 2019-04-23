package sit.kingshing.kingchat.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import sit.kingshing.common.app.PresenterToolbarActivity;
import sit.kingshing.common.widget.GalleryView;
import sit.kingshing.common.widget.PortraitView;
import sit.kingshing.common.widget.recycler.RecyclerAdapter;
import sit.kingshing.factory.model.card.FeedCard;
import sit.kingshing.factory.presenter.BaseContract;
import sit.kingshing.kingchat.R;

/**
 * 朋友圈 TODO
 */
public class MomentActivity extends PresenterToolbarActivity {

    private Adapter mAdapter;

    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;


    public static void show(Context context){
        context.startActivity(new Intent(context,MomentActivity.class));
    }
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_moment;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new RecyclerAdapter() {
            @Override
            protected int getItemViewType(int position, Object o) {
                return R.layout.cell_moment;
            }

            @Override
            protected ViewHolder onCreateViewHolder(View root, int viewType) {
                return new MomentActivity.ViewHolder(root);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected BaseContract.Presenter initPresenter() {
        //TODO
        return null;
    }


    class ViewHolder extends RecyclerAdapter.ViewHolder<FeedCard>{

        @BindView(R.id.im_portrait)
        PortraitView mPortraitView;

        @BindView(R.id.txt_name)
        TextView name;

        @BindView(R.id.txt_time)
        TextView time;

        @BindView(R.id.txt_content)
        TextView content;

        @BindView(R.id.gallery)
        GalleryView gallery;

        @OnClick(R.id.im_praise)
        void praiseClick(){

        };

        @OnClick(R.id.im_comment)
        void commentClick(){

        };

        @OnClick(R.id.im_share)
        void shareClick(){

        };

        public ViewHolder(@NonNull View root) {
            super(root);
        }

        @Override
        protected void onBind(FeedCard feedCard) {

        }
    }
}
