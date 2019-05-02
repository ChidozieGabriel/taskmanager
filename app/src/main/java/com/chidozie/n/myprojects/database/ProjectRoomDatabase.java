package com.chidozie.n.myprojects.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.chidozie.n.myprojects.model.Project;

/**
 * Created by Chidozie on 10/31/2018.
 */
@Database(entities = {Project.class}, version = 2)
@TypeConverters(Converters.class)
public abstract class ProjectRoomDatabase extends RoomDatabase {
    private static volatile ProjectRoomDatabase instance;
    private static RoomDatabase.Callback roomDatabaseCallback =
        new RoomDatabase.Callback() {
            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
                new PopulateDbAsync(instance).execute();
            }
        };

    static ProjectRoomDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (ProjectRoomDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        ProjectRoomDatabase.class,
                        "project_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build();
                }
            }
        }

        return instance;
    }

    public abstract ProjectDao projectDao();

    private static class PopulateDbAsync
        extends AsyncTask<Void, Void, Void> {
        private final ProjectDao dao;

        private PopulateDbAsync(ProjectRoomDatabase db) {
            this.dao = db.projectDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            Project p = new Project("Rate this app '5' stars");
            Project p1 = new Project("Download this AWESOME app");
            p1.setCompleted(true);
            dao.insert(p1);
            dao.insert(p);
            return null;
        }
    }

}
