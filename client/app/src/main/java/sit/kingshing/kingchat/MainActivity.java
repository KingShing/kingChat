package sit.kingshing.kingchat;


import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import butterknife.BindView;

import butterknife.OnClick;
import sit.kingshing.common.app.Activity;

public class MainActivity extends Activity implements IView{

    private IPresenter mPresenter;

    @BindView(R.id.txt_result)
    TextView mTextView;

    @BindView(R.id.btn_submit)
    Button btn_submit;

    @BindView(R.id.edit_query)
    EditText edit_query;

    @Override
    protected int getContentLayoutId() {
        return R.layout.layout_test;
    }


    @OnClick(R.id.btn_submit)
    void onSubmit(){
        mPresenter.search();
    }

    @Override
    public String getInputString() {
        return edit_query.getText().toString();
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter  = new Presenter(this);
    }

    @Override
    public void setResultString(String res) {
        mTextView.setText(res);
    }
}
