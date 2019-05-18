package sit.kingshing.kingchat.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.LinkedList;

import butterknife.BindView;
import sit.kingshing.common.app.Application;
import sit.kingshing.common.app.PresenterToolbarActivity;
import sit.kingshing.factory.Factory;
import sit.kingshing.factory.presenter.feed.FeedCreateContract;
import sit.kingshing.factory.presenter.feed.FeedCreatePresenter;
import sit.kingshing.kingchat.R;
import sit.kingshing.kingchat.activities.adapter.BaseAdapter;
import sit.kingshing.kingchat.fragment.media.GalleryFragment;
import sit.kingshing.kingchat.util.ResourcesUtil;


public class FeedCreateActivity
        extends PresenterToolbarActivity<FeedCreateContract.Presenter>
        implements FeedCreateContract.View {
    public static final int MAX_PICTURE_SIZE = 9;
    private BaseAdapter<String> mAdapter;

    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.txt_content)
    EditText mEditText;

    private GalleryFragment mGalleryFragment;

    public static void show(Context context) {
        context.startActivity(new Intent(context, FeedCreateActivity.class));
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_feed_create;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter = new BaseAdapter<String>() {
            @Override
            public int getItemViewType(int position, String s) {
                return R.layout.cell_image;
            }

            @Override
            public ViewHolder<String> onCreateViewHolder(View parent, int type) {
                return new VH(parent);
            }

            //重写次方法是因为： 当10一个图片时，最后一个为add_icon,这个应该不显示,视为无效数据
            @Override
            public int getItemCount() {
                int size = super.getItemCount();
                return size == MAX_PICTURE_SIZE + 1 ? MAX_PICTURE_SIZE : size;
            }
        };
        mAdapter.setListener(new BaseAdapter.BaseAdapterListener<String>() {

            @Override
            public void onItemClick(BaseAdapter.ViewHolder viewHolder, String path) {
                mGalleryFragment = new GalleryFragment();
                final int position = viewHolder.getAdapterPosition();
                if (isLastPicture(position) && mAdapter.getAdapterItemSize() < MAX_PICTURE_SIZE + 1) {
                    //show GalleryFragment and  add one picture
                    mGalleryFragment.setListener(new GalleryFragment.onSelectedImageListener() {
                        @Override
                        public void selectedImage(String path) {
                            mAdapter.insertItem(position, path);
                            mGalleryFragment.onDestroyView();
                        }
                    }).show(getSupportFragmentManager(), this.getClass().getSimpleName());
                } else {
                    Application.showToast("position:" + position + " 看全图 TODO");
                }
            }

            @Override
            public void onItemLongClick(BaseAdapter.ViewHolder viewHolder, String path) {
                //长按直接删除该图片
                final int position = viewHolder.getAdapterPosition();
                if (!isLastPicture(position)) {
                    mAdapter.remove(position);
                }
            }
        });
        mAdapter.addItem(ResourcesUtil.getResourcesUri(this, R.drawable.ic_add));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    public void onCreateSucceed() {
        if (mPlaceHolderView != null)
            mPlaceHolderView.triggerOk();
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }

        MomentActivity.show(this);
        hideLoading();
        finish();
    }


    private boolean isLastPicture(int position) {
        return position + 1 == mAdapter.getAdapterItemSize();
    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // 初始化菜单
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.content_publish, menu);
        // 找到发布菜单
        MenuItem publishItem = menu.findItem(R.id.action_publish);
        publishItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //获取编辑的信息
                LinkedList<String> paths = mAdapter.getItemList();
                if (paths.size() > 1 && paths.size() <= MAX_PICTURE_SIZE + 1) {
                    paths.removeLast();//去掉 add_icon
                    Log.d("FeedCreateActivity", "pictures size: " + paths.size());
                } else {
                    paths = null;
                    Log.w("FeedCreateActivity", " warning: picture counts is not normal ");
                }
                String content = mEditText.getText().toString();
                mPresenter.create(content, paths);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected FeedCreateContract.Presenter initPresenter() {
        return new FeedCreatePresenter(this);
    }


    static class VH extends BaseAdapter.ViewHolder<String> {

        @BindView(R.id.im_image)
        ImageView imageView;

        public VH(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(String path) {
            Glide.with(Factory.app())
                    .load(path)
                    .dontAnimate()
                    .centerCrop()
                    .into(imageView);
        }
    }
}
