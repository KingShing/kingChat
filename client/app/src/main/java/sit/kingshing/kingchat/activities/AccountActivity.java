package sit.kingshing.kingchat.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import sit.kingshing.common.app.Activity;
import sit.kingshing.kingchat.R;
import sit.kingshing.kingchat.fragment.account.AccountFragment;

public class AccountActivity extends Activity {

    private Fragment curFragment;


    public static void show(Context context) {
        context.startActivity(new Intent(context, AccountActivity.class));
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_account;
    }


    @Override
    protected void initWidget() {
        super.initWidget();
        curFragment = new AccountFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lay_container, curFragment)
                .commit();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        curFragment.onActivityResult(requestCode, resultCode, data);
    }
}
