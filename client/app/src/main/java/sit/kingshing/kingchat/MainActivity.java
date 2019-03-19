package sit.kingshing.kingchat;


import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

import butterknife.BindView;

import butterknife.OnClick;
import sit.kingshing.common.app.Activity;
import sit.kingshing.common.widget.PortraitView;
import sit.kingshing.kingchat.fragment.main.ActiveFragment;
import sit.kingshing.kingchat.fragment.main.ContactFragment;
import sit.kingshing.kingchat.fragment.main.GroupFragment;
import sit.kingshing.kingchat.helper.NavHelper;

public class MainActivity extends Activity implements BottomNavigationView.OnNavigationItemSelectedListener,
        NavHelper.OnTabChangedListener<Integer> {

    private NavHelper<Integer> mNavHelper;

    @BindView(R.id.appBar)
    View mLayAppBar;

    @BindView(R.id.im_portrait)
    PortraitView mPortrait;


    @BindView(R.id.txt_title)
    TextView mTitle;

    @BindView(R.id.lay_container)
    FrameLayout mContainer;


    @BindView(R.id.navigation)
    BottomNavigationView mBottomNavigationView;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.im_search)
    void onSearchMenuClick() {

    }

    @OnClick(R.id.btn_action)
    void onActionClick() {

    }

    @OnClick(R.id.im_portrait)
    void onPortraitClick() {

    }

    @Override
    protected void initData() {
        super.initData();
        Menu menu = mBottomNavigationView.getMenu();
        menu.performIdentifierAction(R.id.action_home,0);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mNavHelper = new NavHelper(this, getSupportFragmentManager(), R.id.lay_container, this);
        mNavHelper.add(R.id.action_home, new NavHelper.Tab<Integer>(ActiveFragment.class, R.string.title_home))
                .add(R.id.action_contact, new NavHelper.Tab<Integer>(ContactFragment.class, R.string.title_contact))
                .add(R.id.action_group, new NavHelper.Tab<Integer>(GroupFragment.class, R.string.title_group));


        mBottomNavigationView.setOnNavigationItemSelectedListener(this);

        Glide.with(this).load(R.drawable.bg_src_morning).centerCrop().into(new ViewTarget<View, GlideDrawable>(mLayAppBar) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                this.view.setBackground(resource.getCurrent());
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return mNavHelper.performClickMenu(menuItem.getItemId());
    }

    /**
     * NavHelper 处理后，回调的方法
     *
     * @param newTab
     * @param oldTab
     */
    @Override
    public void onTabChanged(NavHelper.Tab<Integer> newTab, NavHelper.Tab<Integer> oldTab) {
        mTitle.setText(newTab.extra);
    }
}
