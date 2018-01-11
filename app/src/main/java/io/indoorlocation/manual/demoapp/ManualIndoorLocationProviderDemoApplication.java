package io.indoorlocation.manual.demoapp;

import android.app.Application;

import io.mapwize.mapwizeformapbox.AccountManager;

public class ManualIndoorLocationProviderDemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AccountManager.start(this, "1f04d780dc30b774c0c10f53e3c7d4ea");
    }

}
