package sit.kingshing.kingchat;

import com.igexin.sdk.PushManager;

import sit.kingshing.common.app.Application;
import sit.kingshing.factory.Factory;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // 调用Factory进行初始化
        Factory.setup();
        // 推送进行初始化
        PushManager.getInstance().initialize(this);
    }
}
