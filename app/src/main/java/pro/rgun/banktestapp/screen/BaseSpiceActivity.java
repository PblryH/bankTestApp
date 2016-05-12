package pro.rgun.banktestapp.screen;

import android.support.v7.app.AppCompatActivity;

import com.octo.android.robospice.SpiceManager;

import pro.rgun.banktestapp.network.RetrofitSpiceService;

/**
 * Created by rgun on 09.05.16.
 */
public class BaseSpiceActivity extends AppCompatActivity {

    private SpiceManager spiceManager = new SpiceManager(RetrofitSpiceService.class);

    ///////////////////////////////////////////////////////////////////////////
    // Activity lifecycle
    ///////////////////////////////////////////////////////////////////////////
    @Override
    protected void onStart() {
        spiceManager.start(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }

    /**
     * Получение {@link SpiceManager}
     * @return {@link SpiceManager}
     */
    protected SpiceManager getSpiceManager() {
        return spiceManager;
    }
}
