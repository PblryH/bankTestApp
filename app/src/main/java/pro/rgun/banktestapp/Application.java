package pro.rgun.banktestapp;

import android.content.Context;

/**
 * Created by rgun on 01.05.16.
 */
public class Application extends android.app.Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Application.context = getApplicationContext();
    }
}
