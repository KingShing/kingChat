package sit.kingshing.common.widget.nineGrideView;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class DefaultImageCreator implements LGNineGrideView.ImageCreator {

    private static DefaultImageCreator defaultImageCreator;

    private DefaultImageCreator() {
    }

    public static DefaultImageCreator getInstance() {
        if (defaultImageCreator == null) {
            synchronized (DefaultImageCreator.class) {
                if (defaultImageCreator == null)
                    defaultImageCreator = new DefaultImageCreator();
            }
        }
        return defaultImageCreator;
    }

    @Override
    public ImageView createImageView(Context context) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }

    @Override
    public void loadImage(Context context, String url, final ImageView imageView) {
        if (url == null) {
            imageView.setVisibility(View.GONE);
        }
        //imageView.setAdjustViewBounds(true);
        Glide.with(context)
                .load(url)
                .asBitmap()
                .fitCenter()
                .dontTransform()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(imageView);
    }
}
