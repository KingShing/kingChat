package sit.kingshing.kingchat.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.OnClick;
import sit.kingshing.common.app.PresenterToolbarActivity;
import sit.kingshing.common.widget.PortraitView;
import sit.kingshing.factory.model.db.User;
import sit.kingshing.factory.presenter.contact.PersonalContract;
import sit.kingshing.factory.presenter.contact.PersonalPresenter;
import sit.kingshing.kingchat.R;

public class PersonalActivity extends PresenterToolbarActivity<PersonalContract.Presenter> implements PersonalContract.View {
    private static final String BOUND_KEY_ID = "BOUND_KEY_ID";
    private String userId;

    @BindView(R.id.im_header)
    ImageView mHeader;
    @BindView(R.id.im_portrait)
    PortraitView mPortrait;
    @BindView(R.id.txt_name)
    TextView mName;
    @BindView(R.id.txt_desc)
    TextView mDesc;
    @BindView(R.id.txt_follows)
    TextView mFollows;
    @BindView(R.id.txt_following)
    TextView mFollowing;
    @BindView(R.id.btn_say_hello)
    Button mSayHello;

    private MenuItem mFollow;

    public static void show(Context context, String userId) {
        Intent intent = new Intent(context, PersonalActivity.class);
        intent.putExtra(BOUND_KEY_ID, userId);
        context.startActivity(intent);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_personal;
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        userId = bundle.getString(BOUND_KEY_ID);
        return !TextUtils.isEmpty(userId);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitle("");
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.personal, menu);
        mFollow = menu.findItem(R.id.action_follow);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_follow) {
            // 进行关注操作
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_say_hello)
    void onSayHelloClick() {
        // TODO
        //MessageActivity.show(this, null);
    }

    @Override
    protected PersonalContract.Presenter initPresenter() {
        return new PersonalPresenter(this);
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void onLoadDone(User user) {
        if (user==null) {
            return;
        }
        Glide.with(this)
                .load(user.getPortrait())
                .centerCrop()
                .into(mPortrait);
        mName.setText(user.getName());
        mDesc.setText(user.getDesc());
        mFollows.setText(String.format(getString(R.string.label_follows), user.getFollows()));
        mFollowing.setText(String.format(getString(R.string.label_following), user.getFollowing()));
        hideLoading();
    }

    @Override
    public void allowSayHello(boolean isAllow) {

    }

    @Override
    public void setFollowStatus(boolean isFollow) {

    }
}
