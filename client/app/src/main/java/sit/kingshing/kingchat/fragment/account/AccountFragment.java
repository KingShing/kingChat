package sit.kingshing.kingchat.fragment.account;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import sit.kingshing.common.app.Application;
import sit.kingshing.common.app.Fragment;
import sit.kingshing.common.widget.PortraitView;
import sit.kingshing.factory.Factory;
import sit.kingshing.factory.net.UploadHelper;
import sit.kingshing.kingchat.R;
import sit.kingshing.kingchat.fragment.media.GalleryFragment;

import static android.app.Activity.RESULT_OK;


public class AccountFragment extends Fragment {

public static final String TAG = Fragment.class.getSimpleName();

    @BindView(R.id.im_portrait)
    PortraitView mPortraitView;


    public AccountFragment() {
    }


    @OnClick(R.id.im_portrait)
    public void click() {
        new GalleryFragment().setListener(new GalleryFragment.onSelectedImageListener() {
            @Override
            public void selectedImage(String path) {

                UCrop.Options options = new UCrop.Options();
                // 设置图片处理的格式JPEG
                options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                // 设置压缩后的图片精度
                options.setCompressionQuality(96);

                // 得到头像的缓存地址
                File dPath = Application.getPortraitTmpFile();

                // 发起剪切
                UCrop.of(Uri.fromFile(new File(path)), Uri.fromFile(dPath))
                        .withAspectRatio(1, 1) // 1比1比例
                        .withMaxResultSize(520, 520) // 返回最大的尺寸
                        .withOptions(options) // 相关参数
                        .start(getActivity());
            }

        }).show(getChildFragmentManager(), GalleryFragment.class.getSimpleName());


    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_account;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            if (resultUri != null) {
                loadPortrait(resultUri);
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }


    private void loadPortrait(Uri uri) {

        //加载图片到控件
        Glide.with(this)
                .load(uri)
                .asBitmap()
                .centerCrop()
                .into(mPortraitView);

        //保存到服务器
        //TODO
        final String localPath = uri.getPath();

        Factory.runOnAsync(new Runnable() {
            @Override
            public void run() {
              String url =   UploadHelper.uploadPortrait(localPath);
                Log.d(TAG, "run: "+url);
            }
        });
    }
}
