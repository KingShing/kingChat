package sit.kingshing.kingchat.fragment.media;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.LoaderManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import sit.kingshing.common.tools.UiTool;
import sit.kingshing.common.widget.GalleryView;
import sit.kingshing.kingchat.R;


public class GalleryFragment extends BottomSheetDialogFragment implements GalleryView.SelectedChangeListener {


    GalleryView galleryView;

    private onSelectedImageListener mListener;

    public GalleryFragment setListener(onSelectedImageListener listener) {
        this.mListener = listener;
        return this;
    }

    public GalleryFragment() {
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new BottomSheetDialog(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        galleryView = (GalleryView) root.findViewById(R.id.galleryView);
        return root;
    }


    @Override
    public void onStart() {
        super.onStart();
        galleryView.setUp(LoaderManager.getInstance(this), this);
    }

    @Override
    public void onSelectedCountChanged(int count) {
        if (count>0) {
            if (mListener!=null) {
                String[]  paths = galleryView.getSelectedPath();
                mListener.selectedImage(paths[0]);
            }
        }
    }


    public interface onSelectedImageListener {
        void selectedImage(String path);
    }


    /**
     * 为了解决顶部状态栏变黑而写的TransStatusBottomSheetDialog
     */
    public static class TransStatusBottomSheetDialog  extends BottomSheetDialog{

        public TransStatusBottomSheetDialog(@NonNull Context context) {
            super(context);
        }

        public TransStatusBottomSheetDialog(@NonNull Context context, int theme) {
            super(context, theme);
        }

        protected TransStatusBottomSheetDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
            super(context, cancelable, cancelListener);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            final Window window = getWindow();
            if (window == null)
                return;


            // 得到屏幕高度
            int screenHeight = UiTool.getScreenHeight(getOwnerActivity());
            // 得到状态栏的高度
            int statusHeight = UiTool.getStatusBarHeight(getOwnerActivity());

            // 计算dialog的高度并设置
            int dialogHeight = screenHeight - statusHeight;
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    dialogHeight <= 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight);

        }
    }
}
