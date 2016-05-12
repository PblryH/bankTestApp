package pro.rgun.banktestapp.screen.main.view;

import android.support.v4.widget.SwipeRefreshLayout;

import java.util.List;

import pro.rgun.banktestapp.model.Record;

/**
 * Created by rgun on 12.05.16.
 */
public interface ViewMain {

    /**
     * Добавление данных в RecyclerView
     * @param data {@link List<ListItemBankModel>}
     */
    void addDataToRecyclerView(List<ListItemBankModel> data);


    /**
     * Получить список элементов
     * @return
     */
    List<ListItemBankModel> getListItemBankModels();
    /**
     * Показать прогресс
     */
    void showProgress();

    /**
     * Скрыть прогресс
     */
    void hideProgress();

    /**
     * Установить листнер PullToRefresh
     * @param listener
     */
    void setPullToRefreshListener(SwipeRefreshLayout.OnRefreshListener listener);

    /**
     * Установить листнер {@link Listener}
     * @param listener
     */
    void setListener(Listener listener);

    /**
     * Уведомить элемент об изменении на позиции
     * @param position
     */
    void notifyItemChanged(int position);

    /**
     * Заполнение списка
     * @param recordList {@link List<Record>}
     */
    void fillList(List<Record> recordList);

    /**
     * Скрыть прогресс PullToRefresh
     */
    void hidePullToRefreshProgress();

    /**
     * Очистить список
     */
    void clearList();

    /**
     * Проскролить список на позицию
     * @param position
     */
    void scrollToPosition(int position);


    interface Listener{

        /**
         * При нажатии на элемент списка
         * @param object
         * @param position
         */
        void onItemClick(ListItemBankModel object, int position);

        /**
         * При нажатии на кнопку retry
         * @param object
         * @param position
         */
        void onRetryClick(ListItemBankModel object, int position);
    }
}
