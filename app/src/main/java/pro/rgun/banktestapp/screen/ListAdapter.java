package pro.rgun.banktestapp.screen;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by rgun on 03.12.15.
 *
 * <p>Абстрактный адаптер списка</p>
 */
public abstract class ListAdapter<D, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    /**
     * Список элементов
     */
    protected List<D> mDataList;

    /**
     * Конструктор
     * @param dataList Список данных
     */
    public ListAdapter(List<D> dataList) {
        mDataList = dataList;
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /**
     * Очистить список
     */
    public void clear() {
        int size = mDataList.size();
        mDataList.clear();
        notifyItemRangeRemoved(0, size);
    }

    /**
     * Добавить список данных к сущеструющим
     */
    public void addAll(List<D> data) {
        mDataList.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * Получить элемент по позиции
     * @param position позиция в списке
     * @return элемент
     */
    protected D getItemAt(int position) {
        return mDataList.get(position);
    }

    /**
     * Заменить элемент по позиции
     * @param position позиция в списке
     * @param newItem новый элемент
     * @return элемент
     */
    protected D replaceItemAt(int position, D newItem) {
        D item = mDataList.set(position, newItem);
        notifyItemChanged(position);
        return item;
    }

    /**
     * Добавить элемент на позицию
     * @param index позиция
     * @param newItem элемент
     */
    protected void addItem(int index, D newItem) {
        mDataList.add(index, newItem);
        notifyItemInserted(index);
    }

    /**
     * Добавить элемент в конец списка
     * @param newItem элемент
     */
    protected void addItem(D newItem) {
        mDataList.add(newItem);
        notifyItemInserted(mDataList.size() - 1);
    }

    /**
     * Удалить элемент на позиции
     * @param index позиция
     * @return удаленный элемент
     */
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

    /**
     * Создание вью холдера
     * @param parent
     * @param viewType
     * @return вью холдер
     */
    protected abstract VH createNormalViewHolder(ViewGroup parent, int viewType);

    /**
     * Привязка данных модели к вью холдеру по позиции
     * @param holder
     * @param position
     */
    protected abstract void bindNormalViewHolder(VH holder, int position);
}