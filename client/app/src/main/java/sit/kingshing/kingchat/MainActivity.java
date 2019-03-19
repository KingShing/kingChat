package sit.kingshing.kingchat;


import android.widget.TextView;


import butterknife.BindView;

import sit.kingshing.common.app.Activity;

public class MainActivity extends Activity {


    @BindView(R.id.two)
    TextView mTextView;


    @Override
    protected int getContentLayoutId() {
        return R.layout.layout_test;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mTextView.setText("OK");
    }
}
