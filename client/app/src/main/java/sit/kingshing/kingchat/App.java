package sit.kingshing.kingchat;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.igexin.sdk.PushManager;

import sit.kingshing.common.app.Application;
import sit.kingshing.factory.Factory;
import sit.kingshing.kingchat.fragment.assist.PermissionsFragment;

public class App extends Application {

    private static int ActivityCount = 0;

    @Override
    public void onCreate() {
        super.onCreate();

        // 调用Factory进行初始化
        Factory.setup();
        // 推送进行初始化
        // 推送进行初始化
        PushManager.getInstance().initialize(this, AppPushService.class);
        // 推送注册消息接收服务
        PushManager.getInstance().registerPushIntentService(this, AppMessageReceiverService.class);
        //为了解决，进入app后，在设置里手动取消权限所带来的问题
        initPermissionChecker();
    }

    private void initPermissionChecker() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            private void restartApplication(Context context) {
                final Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
            }


            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                ActivityCount++;
                //应用进入前台，并且没有权限，而且不是启动的LaunchActivity
                if (!(activity instanceof LaunchActivity) && ActivityCount == 1 && !PermissionsFragment.haveAllPermissions(activity)) {
                    restartApplication(activity);//重启app，重新获取权限
                }
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                ActivityCount--;
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }
}
