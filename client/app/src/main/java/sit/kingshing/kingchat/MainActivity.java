package sit.kingshing.kingchat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import sit.kingshing.common.OneClass;
import sit.kingshing.common.app.Activity;

public class MainActivity extends Activity {


    private static final String TAG = "MainActivity";
    @BindView(R.id.one)
    public TextView mTextView;


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        TextView aa= findViewById(R.id.one);
        String res = mTextView==null? "null":"1";
        String res2 =  aa==null? "null":"1";
        Log.e(TAG, res);
        Log.e(TAG, res2+aa.getText());
    }
}
