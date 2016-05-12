package pro.rgun.banktestapp.screen.main.view;

import java.io.Serializable;

import pro.rgun.banktestapp.model.HtmlwebruBankModel;

/**
 * Created by rgun on 05.05.16.
 *
 * <p>Модель для {@link BanksListAdapter}</p>
 */
public class ListItemBankModel implements Serializable{

    private static final long serialVersionUID = -8006070426484949986L;

    public String shortName;
    public String bic;
    public String ks;
    public String city;
    public String address;
    public String tel;
    public boolean isExpanded = false;
    public State state = State.SHORT;

    /**
     * Заполнение параметров ks, city, address, phone из модели {@link HtmlwebruBankModel}
     *
     * @param htmlwebruBankModel {@link HtmlwebruBankModel}
     */
    public void fillFromHtmlwebruBankModel(HtmlwebruBankModel htmlwebruBankModel){
        ks = htmlwebruBankModel.ks;
        city = htmlwebruBankModel.city;
        address = htmlwebruBankModel.adress;
        tel = htmlwebruBankModel.tel;
    }

    /**
     * Состояния элемента списка
     */
    public enum State {
        SHORT,
        FULL,
        IN_PROGRESS,
        REPEAT
    }
}
