package pro.rgun.banktestapp.network;

import pro.rgun.banktestapp.model.CbrBankModel;
import pro.rgun.banktestapp.model.HtmlwebruBankModel;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by rgun on 09.05.16.
 */
public interface Banks {

    @GET("/scripts/XML_bic.asp")
    CbrBankModel banks();

    @GET("/service/api.php?json")
    HtmlwebruBankModel bankDetail(@Query("bic") Integer bic);

}
