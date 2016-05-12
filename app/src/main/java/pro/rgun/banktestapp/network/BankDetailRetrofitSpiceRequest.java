package pro.rgun.banktestapp.network;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import pro.rgun.banktestapp.model.HtmlwebruBankModel;

/**
 * Created by rgun on 09.05.16.
 *
 * <p>Запрос на получение детальной информации о банке по БИК</p>
 */
public class BankDetailRetrofitSpiceRequest extends RetrofitSpiceRequest<HtmlwebruBankModel, Banks> {


    private String bic;

    /**
     * Конструктор
     * @param bic БИК банка
     */
    public BankDetailRetrofitSpiceRequest(String bic) {
        super(HtmlwebruBankModel.class, Banks.class);
        this.bic = bic;
    }

    @Override
    public HtmlwebruBankModel loadDataFromNetwork() {
        return getService().bankDetail(bic);
    }
}
