package pro.rgun.banktestapp.network;

import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;

/**
 * Created by rgun on 09.05.16.
 */
public class RetrofitSpiceService extends RetrofitGsonSpiceService {

    private final static String BASE_URL = "http://htmlweb.ru";

    @Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(Banks.class);
    }

    @Override
    protected String getServerUrl() {
        return BASE_URL;
    }

}
