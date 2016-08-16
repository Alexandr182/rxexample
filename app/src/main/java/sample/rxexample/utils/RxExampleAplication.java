package sample.rxexample.utils;

import android.app.Application;

/**
 * Created by profiralexandr on 17/08/16.
 */

public class RxExampleAplication extends Application {

    private static RxExampleAplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static RxExampleAplication getInstance() {
        return sInstance;
    }
}
