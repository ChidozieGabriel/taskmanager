package com.chidozie.n.myprojects.database;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import com.chidozie.n.myprojects.model.Project;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Chidozie on 10/11/2018.
 */

public class UpdateService extends IntentService {
    private static final String TAG = "UpdateService";
    private static final long INTERVAL_MS = TimeUnit.SECONDS.toMillis(60);

    public static Intent newIntent(Context context) {
        return new Intent(context, UpdateService.class);
    }

    public static void setServiceAlarm(Context context, boolean isOn) {
        Intent i = UpdateService.newIntent(context);
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);
        AlarmManager alarmManager = (AlarmManager)
            context.getSystemService(Context.ALARM_SERVICE);
        if (isOn) {
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime(), INTERVAL_MS, pi);
        } else {
            alarmManager.cancel(pi);
            pi.cancel();
        }
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public UpdateService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG, "Received an intent: " + intent);

        final ProjectRepository repo = ProjectRepository.getInstance(getApplication());
        final List<Project> unCompletedProjects = repo.getUnCompletedProjects().getValue();
        if (unCompletedProjects == null) {
            return;
        }

        for (Project project : unCompletedProjects) {
            if (!project.isCompleted()) {
                project.setUrgency();
                repo.insert(project);
            }
        }

    }
}

