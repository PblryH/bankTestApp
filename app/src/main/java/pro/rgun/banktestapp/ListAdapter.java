package pro.rgun.banktestapp;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by rgun on 03.12.15.
 */
public abstract class ListAdapter<D, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    protected List<D> mDataList;

    public ListAdapter(List<D> dataList) {
        mDataList = dataList;
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void clear() {
        int size = mDataList.size();
        mDataList.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void addAll(List<D> data) {
        mDataList.addAll(data);
        notifyDataSetChanged();
    }

    protected D getItemAt(int position) {
        return mDataList.get(position);
    }

    protected D replaceItemAt(int position, D newItem) {
        D item = mDataList.set(position, newItem);
        notifyItemChanged(position);
        return item;
    }

    protected void addItem(int index, D newItem) {
        mDataList.add(index, newItem);
        notifyItemInserted(index);
    }

    protected void addItem(D newItem) {
        mDataList.add(newItem);
        notifyItemInserted(mDataList.size() - 1);
    }

    protected D removeItem(int index) {
        if (mDataList.size() > 0) {
            D item = mDataList.remove(index);
            notifyItemRemoved(index);

            return item;
        }

        return null;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        VH vh = createNormalViewHolder(parent, viewType);
        return vh;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        if (mDataList != null && !mDataList.isEmpty()) {
            bindNormalViewHolder(holder, position);
        }
    }

    protected abstract VH createNormalViewHolder(ViewGroup parent, int viewType);

    protected abstract void bindNormalViewHolder(VH holder, int position);
}