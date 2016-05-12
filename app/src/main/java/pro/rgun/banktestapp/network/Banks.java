package pro.rgun.banktestapp.network;

import pro.rgun.banktestapp.model.CbrBankModel;
import pro.rgun.banktestapp.model.HtmlwebruBankModel;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by rgun on 09.05.16.
 *
 * <p>
 *     Интерфейс запросов
 * </p>
 */
public interface Banks {

    /**
     * Запрос получения списка
     *
     * @return {@link CbrBankModel}
     */
    @GET("/scripts/XML_bic.asp")
    CbrBankModel banks();

    /**
     * Запрос получения детальной банка
     *
     * @param bic - БИК банка
     * @return {@link HtmlwebruBankModel}
     */
    @GET("/service/api.php?json")
    HtmlwebruBankModel bankDetail(@Query("bic") String bic);

}
