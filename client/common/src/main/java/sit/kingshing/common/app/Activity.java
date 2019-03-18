package sit.kingshing.common.app;

import android.os.Bundle;
import android.view.View;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import sit.kingshing.common.R;

public abstract class Activity extends AppCompatActivity {




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
        List<androidx.fragment.app.Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (androidx.fragment.app.Fragment fragment : fragments) {
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
