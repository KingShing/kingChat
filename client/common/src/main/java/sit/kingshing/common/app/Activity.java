package sit.kingshing.common.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import java.util.List;

import butterknife.ButterKnife;
import sit.kingshing.common.widget.convention.PlaceHolderView;


public abstract class Activity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION = 0;

    protected PlaceHolderView mPlaceHolderView;
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
        if (initArgs(getIntent().getExtras())) {
           int layoutId =  getContentLayoutId();
           setContentView(layoutId);
            initBefore();
            initWidget();
            initData();

        } else {
            finish();
        }
    }

    /**
     * 初始化窗口
     */
    protected void initWindow() {

    }

    ;

    /**
     * @param bundle
     * @return
     */
    protected boolean initArgs(Bundle bundle) {
        return true;
    }

    //得到当前界面的资源id
    protected abstract int getContentLayoutId();


    /**
     * 初始化控件之前
     */
    protected  void initBefore(){

    }

    /**
     * 初始化控件
     */
    protected void initWidget() {
        ButterKnife.bind(this);
    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();//当点击导航返回时，finsh当前界面
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        List<android.support.v4.app.Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null && fragments.size() > 0) {
            for ( android.support.v4.app.Fragment fragment : fragments) {
                if (fragment instanceof sit.kingshing.common.app.Fragment) {
                    if (((sit.kingshing.common.app.Fragment) fragment).onBackPressed()) {
                        return;
                    }
                }
            }
        }

        super.onBackPressed();
        finish();
    }


    /**
     * 设置占位布局
     *
     * @param placeHolderView 继承了占位布局规范的View
     */
    public void setPlaceHolderView(PlaceHolderView placeHolderView) {
        this.mPlaceHolderView = placeHolderView;
    }


    /**
     * 点击返回键返回桌面而不是退出程序
     *
     * @param keyCode keyCode
     * @param event event
     * @return boolean
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            //overridePendingTransition(, );
            // TODO 动画
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
