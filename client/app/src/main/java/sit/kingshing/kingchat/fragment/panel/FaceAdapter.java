package sit.kingshing.kingchat.fragment.panel;

import android.view.View;

import java.util.List;

import sit.kingshing.common.widget.recycler.RecyclerAdapter;
import sit.kingshing.face.Face;
import sit.kingshing.kingchat.R;


public class FaceAdapter extends RecyclerAdapter<Face.Bean> {

    public FaceAdapter(List<Face.Bean> beans, AdapterListener<Face.Bean> listener) {
        super(beans, listener);
    }

    @Override
    protected int getItemViewType(int position, Face.Bean bean) {
        return R.layout.cell_face;
    }

    @Override
    protected ViewHolder<Face.Bean> onCreateViewHolder(View root, int viewType) {
        return new FaceHolder(root);
    }
}
