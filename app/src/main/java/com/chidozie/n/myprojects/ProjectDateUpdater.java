package com.chidozie.n.myprojects;

import android.os.HandlerThread;

/**
 * Created by Chidozie on 12/22/2018.
 */
public class ProjectDateUpdater<T> extends HandlerThread {
    private static final String TAG = "ProjectDateUpdater";
    private boolean hasQuit;

    public ProjectDateUpdater() {
        super(TAG);
    }

    @Override
    public boolean quit() {
        hasQuit = true;
        return super.quit();
    }
}
