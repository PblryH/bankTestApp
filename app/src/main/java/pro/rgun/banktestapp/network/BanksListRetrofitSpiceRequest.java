package pro.rgun.banktestapp.network;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import pro.rgun.banktestapp.model.CbrBankModel;
import retrofit.RestAdapter;
import retrofit.converter.SimpleXMLConverter;

/**
 * Created by rgun on 09.05.16.
 */
public class BanksListRetrofitSpiceRequest extends RetrofitSpiceRequest<CbrBankModel, Banks> {


    public BanksListRetrofitSpiceRequest() {
        super(CbrBankModel.class, Banks.class);
    }

    public RestAdapter getHostAdapter(String baseHost){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(baseHost)
//                .setRequestInterceptor(requestInterceptor)
                .setConverter(new SimpleXMLConverter())
                .build();

        return restAdapter;
    }

    @Override
    public CbrBankModel loadDataFromNetwork() {
        return getHostAdapter("http://www.cbr.ru").create(Banks.class).banks();
    }
}
