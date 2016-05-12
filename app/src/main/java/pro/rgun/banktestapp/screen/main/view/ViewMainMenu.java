package pro.rgun.banktestapp.screen.main.view;

import java.util.List;

/**
 * Created by rgun on 12.05.16.
 */
public interface ViewMainMenu {

    /**
     * Установка текущего списка для поиска
     * @param list
     */
    void setListItemBankModels(List<ListItemBankModel> list);

    /**
     * Установка слушателя
     * @param listener
     */
    void setListener(Listener listener);

    interface Listener {

        /**
         * Нужно очистить список
         */
        void onNeedClearList();

        /**
         * Нужно добавить данные в список
         * @param list
         */
        void onNeedAddNewDataToList(List<ListItemBankModel> list);

    }
}
