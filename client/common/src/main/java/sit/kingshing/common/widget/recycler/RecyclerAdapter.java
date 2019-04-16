package sit.kingshing.common.widget.recycler;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import sit.kingshing.common.R;


public abstract class RecyclerAdapter<Data>
        extends Adapter<RecyclerAdapter.ViewHolder<Data>>
        implements View.OnClickListener, View.OnLongClickListener, AdapterCallback<Data> {


    private final List<Data> mDataList;
    private AdapterListener<Data> mListener;

    public void setListener(AdapterListener<Data> mListener) {
        this.mListener = mListener;
    }

    /**
     * 构造函数
     */
    public RecyclerAdapter() {
        this(null);
    }

    public RecyclerAdapter(AdapterListener<Data> listener) {
        this(new ArrayList<Data>(), listener);
    }

    public RecyclerAdapter(List<Data> dataList, AdapterListener<Data> listener) {
        this.mDataList = dataList;
        this.mListener = listener;
    }


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
        ViewHolder<Data> holder = onCreateViewHolder(root, viewType);
        //设置View的tag为ViewHolder，进行双向绑定
        root.setTag(R.id.tag_recycler_holder, holder);

        //设置点击事件
        root.setOnClickListener(this);
        root.setOnLongClickListener(this);

        //进行界面注解绑定
        holder.mUnbinder = ButterKnife.bind(holder, root);

        //绑定callback
        holder.callback = this;
        return holder;
    }

    /**
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position, mDataList.get(position));
    }

    /**
     * @param position
     * @param data
     * @return
     */
    @LayoutRes
    protected abstract int getItemViewType(int position, Data data);


    /**
     * @param root
     * @param viewType
     * @return
     */
    protected abstract ViewHolder<Data> onCreateViewHolder(View root, int viewType);

    /**
     * @param dataViewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder<Data> dataViewHolder, int i) {
        //
        Data data = mDataList.get(i);
        //
        dataViewHolder.bind(data);

    }


    /**
     * @return
     */
    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void add(Data data) {
        mDataList.add(data);
        notifyItemInserted(mDataList.size() - 1);
    }

    public void add(Data... dataList) {
        if (dataList != null && dataList.length > 0) {
            int startPos = mDataList.size();
            Collections.addAll(mDataList, dataList);
            notifyItemRangeInserted(startPos, dataList.length);
        }
    }

    public void add(Collection<Data> dataList) {
        if (dataList != null && dataList.size() > 0) {
            int startPos = mDataList.size();
            mDataList.addAll(dataList);
            notifyItemRangeInserted(startPos, dataList.size());
        }
    }

    /**
     * 删除操作
     */
    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    /**
     * 替换一个新的集合
     */
    public void replace(Collection<Data> dataList) {
        mDataList.clear();
        if (dataList != null && dataList.size() > 0) {
            mDataList.addAll(dataList);
        }
        notifyDataSetChanged();
    }

    @Override
    public void update(Data data, ViewHolder<Data> holder) {
        int pos = holder.getAdapterPosition();
        if (pos >= 0) {/*从0开始*/
            mDataList.remove(pos);
            mDataList.add(pos, data);
            //通知这个坐标下的数据要更新
            notifyItemChanged(pos);
        }
    }

    /**
     * @param v
     */
    @Override
    public void onClick(View v) {
        ViewHolder viewHolder = (ViewHolder) v.getTag(R.id.tag_recycler_holder);
        if (this.mListener != null) {
            int pos = viewHolder.getAdapterPosition();
            this.mListener.onItemClick(viewHolder, mDataList.get(pos));
        }
    }

    /**
     * @param v
     * @return
     */
    @Override
    public boolean onLongClick(View v) {
        ViewHolder viewHolder = (ViewHolder) v.getTag(R.id.tag_recycler_holder);
        if (this.mListener != null) {
            int pos = viewHolder.getAdapterPosition();
            this.mListener.onItemClick(viewHolder, mDataList.get(pos));
            return true;
        }
        return false;
    }

    public List<Data> getItems() {
        return mDataList;
    }


    /**
     * 自定义的监听器
     *
     * @param <Data>
     */
    public interface AdapterListener<Data> {
        //当cell点击时触发
        void onItemClick(RecyclerAdapter.ViewHolder viewHolder, Data data);

        //当cell长按时触发
        void onItemLongClick(RecyclerAdapter.ViewHolder viewHolder, Data data);
    }


    /**
     * @param <Data>
     */
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

    public static abstract class AdapterListenerImpl<Data> implements RecyclerAdapter.AdapterListener<Data> {

        @Override
        public void onItemClick(RecyclerAdapter.ViewHolder viewHolder, Data data) {

        }

        @Override
        public void onItemLongClick(RecyclerAdapter.ViewHolder viewHolder, Data data) {

        }
    }

}
