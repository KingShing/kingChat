package sit.kingshing.common.widget;


import android.content.Context;
import android.util.AttributeSet;

import com.bumptech.glide.RequestManager;

import de.hdodenhof.circleimageview.CircleImageView;
import sit.kingshing.common.R;
import sit.kingshing.factory.model.Author;

public class PortraitView extends CircleImageView {
    public PortraitView(Context context) {
        super(context);
    }

    public PortraitView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PortraitView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public void setUp(RequestManager manager, Author user) {
        if (user == null) {
            return;
        }
       setUp(manager, user.getPortrait());
    }

    public void setUp(RequestManager manager, String uri) {
        if (uri == null)
            uri = "";

        manager.load(uri)
                .dontAnimate()//控件中不能使用动画，会使头像显示延迟
                .placeholder(R.drawable.default_portrait)
                .centerCrop()
                .into(this);
    }

    public void setUp(RequestManager manager, int uri) {
        manager.load(uri)
                .dontAnimate()//控件中不能使用动画，会使头像显示延迟
                .placeholder(R.drawable.default_portrait)
                .centerCrop()
                .into(this);
    }
}
