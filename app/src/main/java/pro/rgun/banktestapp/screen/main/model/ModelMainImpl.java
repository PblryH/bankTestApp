package pro.rgun.banktestapp.screen.main.model;

import com.octo.android.robospice.persistence.DurationInMillis;

import pro.rgun.banktestapp.network.BankDetailRetrofitSpiceRequest;
import pro.rgun.banktestapp.network.BanksListRetrofitSpiceRequest;
import pro.rgun.banktestapp.screen.BaseSpiceActivity;
import pro.rgun.banktestapp.screen.main.ActivityMain;

/**
 * Created by rgun on 12.05.16.
 */
public class ModelMainImpl implements ModelMain{

    public static final String BANKS_REQUEST_CACHE_KEY = "banksListRetrofitSpiceRequest";

    private BanksListRetrofitSpiceRequest banksListRetrofitSpiceRequest;
    private BaseSpiceActivity mActivity;

    public ModelMainImpl(BaseSpiceActivity activity) {
        mActivity = activity;
        banksListRetrofitSpiceRequest = new BanksListRetrofitSpiceRequest();
    }

    @Override
    public void loadBanksFromNetwork(ActivityMain.BanksRequestListener requestListener) {
        mActivity.getSpiceManager().execute(
                banksListRetrofitSpiceRequest,
                BANKS_REQUEST_CACHE_KEY,
                DurationInMillis.ALWAYS_EXPIRED,
                requestListener);
    }

    @Override
    public void loadBanksFromCache(ActivityMain.BanksRequestListener requestListener) {
        mActivity.getSpiceManager().execute(
                banksListRetrofitSpiceRequest,
                BANKS_REQUEST_CACHE_KEY,
                DurationInMillis.ALWAYS_RETURNED,
                requestListener);
    }

    @Override
    public void loadBankDetail(String bic, ActivityMain.BankDetailRequestListener requestListener) {
        mActivity.getSpiceManager().execute(
                new BankDetailRetrofitSpiceRequest(bic),
                "BankDetail" + bic,
                DurationInMillis.ALWAYS_RETURNED,
                requestListener);
    }

}
