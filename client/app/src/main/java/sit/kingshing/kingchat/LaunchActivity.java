package sit.kingshing.kingchat;

import sit.kingshing.common.app.Activity;
import sit.kingshing.kingchat.fragment.assist.PermissionsFragment;

public class LaunchActivity extends Activity {

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (PermissionsFragment.haveAllPermissions(getSupportFragmentManager(),getApplicationContext())) {
            MainActivity.show(this);
            finish();
        }
    }
}
