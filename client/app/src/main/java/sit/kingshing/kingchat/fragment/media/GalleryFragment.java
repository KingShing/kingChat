package sit.kingshing.kingchat.fragment.media;


import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        galleryView.setUp(getLoaderManager(), this);
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
}
