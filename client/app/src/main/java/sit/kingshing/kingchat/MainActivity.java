package sit.kingshing.kingchat;


import android.Manifest;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.master.permissionhelper.PermissionHelper;

import net.qiujuer.genius.ui.Ui;
import net.qiujuer.genius.ui.widget.FloatActionButton;

import java.util.Objects;

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

    private static final String TAG = "MainActivity";
    private PermissionHelper permissionHelper;
    private NavHelper<Integer> mNavHelper;

    @BindView(R.id.appBar)
    View mLayAppBar;

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
        menu.performIdentifierAction(R.id.action_home, 0);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        // checkPerMission();
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


    private void checkPerMission() {
        permissionHelper = new PermissionHelper(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);

        permissionHelper.request(new PermissionHelper.PermissionCallback() {
            @Override
            public void onPermissionGranted() {
                //这个方法是全都授权后将要进行的操作，我们在这调用call（）方法
                Log.d(TAG, "onPermissionGranted() called");
            }

            @Override
            public void onIndividualPermissionGranted(String[] grantedPermission) {
                //某个授权
                Log.d(TAG, "onIndividualPermissionGranted() called with: grantedPermission = [" + TextUtils.join(",", grantedPermission) + "]");
            }

            @Override
            public void onPermissionDenied() {
                //某个拒绝，我们弹出提示
                Toast.makeText(MainActivity.this, "you denied the permission", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onPermissionDenied() called");
            }

            @Override
            public void onPermissionDeniedBySystem() {
                //用户选择了"不再询问"后，点击"拒绝按钮"，执行此方法
                Log.d(TAG, "onPermissionDeniedBySystem() called");
            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissionHelper != null) {
            permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
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
