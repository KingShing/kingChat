package sit.kingshing.kingchat.activities.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.LinkedList;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import sit.kingshing.kingchat.R;

public abstract class BaseAdapter<Data> extends RecyclerView.Adapter<BaseAdapter.ViewHolder>
        implements View.OnLongClickListener, View.OnClickListener {

    private BaseAdapterListener<Data> mListener;

    private LinkedList<Data> mDataList = new LinkedList<>();

    public void setListener(BaseAdapterListener<Data> listener) {
        this.mListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position, mDataList.get(position));
    }

    @LayoutRes
    public abstract int getItemViewType(int position, Data data);

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater from = LayoutInflater.from(parent.getContext());
        View root = from.inflate(viewType, parent, false);
        root.setOnClickListener(this);
        root.setOnLongClickListener(this);
        ViewHolder<Data> vh = onCreateViewHolder(root, viewType);

        //进行界面注解绑定
        vh.mUnbinder = ButterKnife.bind(vh, root);
        root.setTag(R.id.tag_viewholder, vh);
        return vh;
    }

    public abstract ViewHolder<Data> onCreateViewHolder(View parent, int type);


    @Override
    public void onBindViewHolder(@NonNull BaseAdapter.ViewHolder viewHolder, int index) {
        viewHolder.onBind(mDataList.get(index));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public void onClick(View v) {
        ViewHolder vh = (ViewHolder) v.getTag(R.id.tag_viewholder);
        int adapterPosition = vh.getAdapterPosition();
        if (mListener != null) {
            mListener.onItemClick(vh, mDataList.get(adapterPosition));
        }
    }

    @Override
    public boolean onLongClick(View v) {
        ViewHolder<Data> vh = (ViewHolder) v.getTag(R.id.tag_viewholder);
        int adapterPosition = vh.getAdapterPosition();
        if (mListener != null) {
            mListener.onItemLongClick(vh, mDataList.get(adapterPosition));
            return true;
        }
        return false;
    }


    public static abstract class ViewHolder<Data> extends RecyclerView.ViewHolder {

        protected Data data;
        private Unbinder mUnbinder;
        protected BaseAdapter mAdapterCallback;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(Data data) {
            this.data = data;
            onBind(data);
        }

        abstract protected void onBind(Data data);
    }

    public LinkedList<Data> getItemList() {
        return mDataList;
    }

    public void insertItem(int index, Data data) {
        mDataList.add(index, data);
        notifyItemChanged(index);
    }

    public void addItem(Data data) {
        mDataList.add(data);
        notifyItemChanged(mDataList.size() - 1);
    }


    public void addItem(Data... dataList) {
        if (dataList != null && dataList.length > 0) {
            int startPos = mDataList.size();
            Collections.addAll(mDataList, dataList);
            notifyItemRangeInserted(startPos, dataList.length);
        }
    }

    public void remove(int index) {
        if (index >= 0 && index <= mDataList.size() - 1) {
            mDataList.remove(index);
            notifyItemRemoved(index);
        }
    }

    public int getAdapterItemSize() {
        return mDataList.size();
    }

    /**
     * 自定义的监听器
     *
     * @param <Data>
     */
    public interface BaseAdapterListener<Data> {
        //当cell点击时触发
        void onItemClick(BaseAdapter.ViewHolder viewHolder, Data data);

        //当cell长按时触发
        void onItemLongClick(BaseAdapter.ViewHolder viewHolder, Data data);
    }


    public static abstract class BaseAdapterListenerImpl<Data> implements BaseAdapterListener<Data> {

        @Override
        public void onItemClick(ViewHolder viewHolder, Data data) {

        }

        @Override
        public void onItemLongClick(ViewHolder viewHolder, Data data) {

        }
    }
}
