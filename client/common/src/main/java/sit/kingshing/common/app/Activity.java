package sit.kingshing.common.app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


public abstract class Activity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
        if (initArgs(getIntent().getExtras())) {
           int layoutId =  getContentLayoutId();
           setContentView(layoutId);
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
}
