package sit.kingshing.kingchat;


import android.support.design.widget.BottomNavigationView;
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

public class MainActivity extends Activity {

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
    void onSearchMenuClick(){

    }
    @OnClick(R.id.btn_action)
    void onActionClick(){

    }

    @OnClick(R.id.im_portrait)
    void onPortraitClick(){

    }


    @Override
    protected void initWidget() {
        super.initWidget();


        Glide.with(this).load(R.drawable.bg_src_morning).centerCrop().into(new ViewTarget<View, GlideDrawable>(mLayAppBar) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                this.view.setBackground(resource.getCurrent());
            }
        });
    }
}
