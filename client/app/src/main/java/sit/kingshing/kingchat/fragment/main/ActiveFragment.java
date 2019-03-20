package sit.kingshing.kingchat.fragment.main;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.util.Log;


import butterknife.BindView;
import sit.kingshing.common.app.Fragment;
import sit.kingshing.common.widget.GalleryView;
import sit.kingshing.kingchat.R;

import static android.support.constraint.Constraints.TAG;


public class ActiveFragment extends Fragment {


    @BindView(R.id.galleyView)
    GalleryView mGalleyView;

    public ActiveFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    protected void initData() {
        super.initData();

        Log.d(TAG, "initData: adadadADadadaaaaaaaaaaaaa");
        mGalleyView.setUp(LoaderManager.getInstance(this), new GalleryView.SelectedChangeListener() {

            @Override
            public void onSelectedCountChanged(int count) {

            }
        });
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_active;
    }

}
