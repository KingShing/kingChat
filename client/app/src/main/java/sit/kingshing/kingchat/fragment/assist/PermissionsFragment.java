package sit.kingshing.kingchat.fragment.assist;


import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.qiujuer.genius.ui.widget.Button;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import sit.kingshing.common.app.Application;
import sit.kingshing.factory.persistence.Account;
import sit.kingshing.kingchat.MainActivity;
import sit.kingshing.kingchat.R;
import sit.kingshing.kingchat.activities.AccountActivity;
import sit.kingshing.kingchat.activities.UserActivity;
import sit.kingshing.kingchat.fragment.media.GalleryFragment;


public class PermissionsFragment extends BottomSheetDialogFragment implements EasyPermissions.PermissionCallbacks {

    // 权限回调的标示
    private final static int requestCode = 0x110;

    public PermissionsFragment() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new GalleryFragment.TransStatusBottomSheetDialog(getContext());
    }

    private static void show(FragmentManager manager) {
        new PermissionsFragment().show(manager, PermissionsFragment.class.getSimpleName());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_permissions, container, false);

        Button btn = (Button) root.findViewById(R.id.btn_submit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //请求权限
                requestPermissions();
            }
        });

        return root;
    }


    public static boolean haveAllPermissions(Context context){
        String[] perms = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
        };

        return EasyPermissions.hasPermissions(context, perms);
    }


    public static boolean  checkAndRequestAllPermissions(FragmentManager manager, Context context){
        boolean flag = haveAllPermissions(context);

        if (!flag) {
            show(manager);
        }
        return flag;
    }



    @Override
    public void onResume() {
        super.onResume();
        refreshFragment(getView());
    }

    @AfterPermissionGranted(requestCode)
    private void requestPermissions() {

        String[] perms = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
        };
        //先检查是否已经有了所有权限
        if (EasyPermissions.hasPermissions(this.getContext(), perms)) {
            Application.showToast(R.string.label_permission_ok);
            refreshFragment(getView());
            // 检查跳转到主页还是登录
            if (Account.isLogin()) {

                if(Account.isComplete()){
                    MainActivity.show(getContext());
                }else {
                    UserActivity.show(getContext());
                }
            } else {
                AccountActivity.show(getContext());
            }
            getActivity().finish();

        } else {
            //请求权限
            EasyPermissions.requestPermissions(this, getString(R.string.title_assist_permissions), requestCode, perms);
        }


    }

    private void refreshFragment(View root) {
        //检查各个权限
        if (root == null) {
            return;
        }
        root.findViewById(R.id.im_state_permission_record_audio)
                .setVisibility(checkOnePermission(Manifest.permission.RECORD_AUDIO) ? View.VISIBLE : View.GONE);

        root.findViewById(R.id.im_state_permission_network)
                .setVisibility(checkOnePermission(Manifest.permission.INTERNET) ? View.VISIBLE : View.GONE);

        root.findViewById(R.id.im_state_permission_read)
                .setVisibility(checkOnePermission(Manifest.permission.READ_EXTERNAL_STORAGE) ? View.VISIBLE : View.GONE);

        root.findViewById(R.id.im_state_permission_write)
                .setVisibility(checkOnePermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ? View.VISIBLE : View.GONE);




    }

    private boolean checkOnePermission(String permission) {
        return EasyPermissions.hasPermissions(getContext(), permission);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        // 如果权限有没有申请成功的权限存在，则弹出弹出框，用户点击后去到设置界面自己打开权限
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog
                    .Builder(this)
                    .build()
                    .show();
        }
    }


    /**
     * 权限申请的时候回调的方法，在这个方法中把对应的权限申请状态交给EasyPermissions框架
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults,this);
    }
}
