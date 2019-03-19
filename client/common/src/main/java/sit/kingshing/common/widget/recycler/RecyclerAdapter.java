package sit.kingshing.common.widget.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import sit.kingshing.common.R;


public class RecyclerAdapter<Data>
        extends Adapter<RecyclerAdapter.ViewHolder<Data>>
            implements View.OnClickListener, View.OnLongClickListener {


    private final List<Data> mDataList = new ArrayList<>();


    /**
     * @param parent   RecyclerView
     * @param viewType 界面的类型,约定为xml布局的id
     * @return viewHolder
     */
    @NonNull
    @Override
    public ViewHolder<Data> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(viewType, parent, false);
        ViewHolder<Data> holder = onCreateViewHolder((ViewGroup) root, viewType);

        //设置点击事件
        root.setOnClickListener(this);
        root.setOnLongClickListener(this);

        //设置View的tag为ViewHolder，进行双向绑定
        root.setTag(R.id.tag_recycler_holder, holder);

        //进行界面注解绑定
        holder.mUnbinder = ButterKnife.bind(holder, root);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder<Data> dataViewHolder, int i) {
        //
        Data data = mDataList.get(i);
        //
        dataViewHolder.bind(data);

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }


    public abstract static class ViewHolder<Data> extends RecyclerView.ViewHolder {
        protected Data mData;
        private Unbinder mUnbinder;
        private AdapterCallback callback;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(Data data) {
            this.mData = data;
            onBind(data);
        }

        protected abstract void onBind(Data data);

        public void update(Data data) {
            if (callback != null) {
                this.callback.update(data, this);
            }
        }

    }
}
