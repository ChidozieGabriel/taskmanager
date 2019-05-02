package com.chidozie.n.myprojects;

import android.app.Application;
import android.content.res.Resources;

/**
 * Created by Chidozie on 9/25/2018.
 */

public class App extends Application {
    private static App instance;
    private static Resources res;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        res = getResources();
    }

    public static App getInstance() {
        return instance;
    }

    public static Resources getRes() {
        return res;
    }
}
