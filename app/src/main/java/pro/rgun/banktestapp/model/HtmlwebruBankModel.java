package pro.rgun.banktestapp.model;

import java.io.Serializable;

/**
 * Created by rgun on 30.04.16.
 *
 * <p>Модель для сохранения детальной информации по банку</p>
 *
 * @see <a href="https://htmlweb.ru/service/bank.php">https://htmlweb.ru/service/bank.php</a>
 */
public class HtmlwebruBankModel implements Serializable {

    private static final long serialVersionUID = -5284018040643792339L;

    public String name;
    public String city;
    public String adress;
    public String bic;
    public String ks;
    public String tel;
    public String upd;
    public Integer limit;
}
