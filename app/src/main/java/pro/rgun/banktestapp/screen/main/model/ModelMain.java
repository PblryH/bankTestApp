package pro.rgun.banktestapp.screen.main.model;

import pro.rgun.banktestapp.screen.main.ActivityMain;

/**
 * Created by rgun on 12.05.16.
 */
public interface ModelMain {

    /**
     * Загрузка списка банков игнорируя кэш
     * @param requestListener
     */
    void loadBanksFromNetwork(ActivityMain.BanksRequestListener requestListener);

    /**
     * Загрузка списка банков используя кэш
     * @param requestListener
     */
    void loadBanksFromCache(ActivityMain.BanksRequestListener requestListener);

    /**
     * Загрузка детальной банка по его БИК
     * @param bic
     * @param requestListener
     */
    void loadBankDetail(String bic, ActivityMain.BankDetailRequestListener requestListener);
}
