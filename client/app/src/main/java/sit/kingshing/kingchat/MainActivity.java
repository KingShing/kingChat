package sit.kingshing.kingchat;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

import net.qiujuer.genius.ui.Ui;
import net.qiujuer.genius.ui.widget.FloatActionButton;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import sit.kingshing.common.app.Activity;
import sit.kingshing.common.widget.PortraitView;
import sit.kingshing.factory.persistence.Account;
import sit.kingshing.kingchat.activities.GroupCreateActivity;
import sit.kingshing.kingchat.activities.PersonalActivity;
import sit.kingshing.kingchat.activities.SearchActivity;
import sit.kingshing.kingchat.activities.UserActivity;
import sit.kingshing.kingchat.fragment.main.ActiveFragment;
import sit.kingshing.kingchat.fragment.main.ContactFragment;
import sit.kingshing.kingchat.fragment.main.GroupFragment;
import sit.kingshing.kingchat.helper.NavHelper;

public class MainActivity extends Activity implements BottomNavigationView.OnNavigationItemSelectedListener,
        NavHelper.OnTabChangedListener<Integer> {

    private static final String TAG = "MainActivity";
    private NavHelper<Integer> mNavHelper;

    @BindView(R.id.appbar)
    AppBarLayout mLayAppBar;

    @BindView(R.id.im_portrait)
    PortraitView mPortrait;

    @BindView(R.id.btn_action)
    FloatActionButton mAction;

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
        // 在群的界面的时候，点击顶部的搜索就进入群搜索界面
        // 其他都为人搜索的界面
        int type = Objects.equals(mNavHelper.getCurrentTab().extra, R.string.title_group) ?
                SearchActivity.TYPE_GROUP : SearchActivity.TYPE_USER;
        SearchActivity.show(this, type);
    }

    @OnClick(R.id.btn_action)
    void onActionClick() {
        //
        // 浮动按钮点击时，判断当前界面是群还是联系人界面
        // 如果是群，则打开群创建的界面
        if (Objects.equals(mNavHelper.getCurrentTab().extra, R.string.title_group)) {
            // 打开群创建界面
            GroupCreateActivity.show(this);
        } else {
            // 如果是其他，都打开添加用户的界面
            SearchActivity.show(this, SearchActivity.TYPE_USER);
        }

    }

    @OnClick(R.id.im_portrait)
    void onPortraitClick() {
        PersonalActivity.show(this, Account.getUserId());
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        if (Account.isComplete()) {
            // 判断用户信息是否完全，完全则走正常流程
            return super.initArgs(bundle);
        } else {
            UserActivity.show(this);
            return false;
        }
    }

    public static void show(Context context){
        context.startActivity(new Intent(context,MainActivity.class));
    }

    @Override
    protected void initData() {
        super.initData();


        mPortrait.setUp(Glide.with(this),Account.getUser().getPortrait());

        Menu menu = mBottomNavigationView.getMenu();
        menu.performIdentifierAction(R.id.action_home, 0);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        /* checkPerMission(); */
        mNavHelper = new NavHelper<>(this, getSupportFragmentManager(), R.id.lay_container, this);
        mNavHelper.add(R.id.action_home, new NavHelper.Tab<>(ActiveFragment.class, R.string.title_home))
                .add(R.id.action_contact, new NavHelper.Tab<>(ContactFragment.class, R.string.title_contact))
                .add(R.id.action_group, new NavHelper.Tab<>(GroupFragment.class, R.string.title_group));


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

        float transY = 0;
        float rotation = 0;
        if (Objects.equals(newTab.extra, R.string.title_home)) {
            transY = Ui.dipToPx(getResources(), 76);
        } else {
            if (Objects.equals(newTab.extra, R.string.title_group)) {
                mAction.setImageResource(R.drawable.ic_group_add);
                rotation = -360;
            } else {
                mAction.setImageResource(R.drawable.ic_contact_add);
                rotation = 360;
            }
        }


        // 旋转，Y轴位移，弹性差值器，时间
        mAction.animate()
                .rotation(rotation)
                .translationY(transY)
                .setInterpolator(new AnticipateOvershootInterpolator(1))
                .setDuration(480)
                .start();
    }


}
