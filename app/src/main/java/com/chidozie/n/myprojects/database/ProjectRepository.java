package com.chidozie.n.myprojects.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.chidozie.n.myprojects.model.Project;

import java.util.List;

/**
 * Created by Chidozie on 10/31/2018.
 */
class ProjectRepository {
    private static ProjectRepository instance;
    private final LiveData<Integer> countUncompletedProjects;
    private final LiveData<Integer> countCompletedProjects;
    private ProjectDao projectDao;
    private LiveData<List<Project>> allProjects;
    private LiveData<List<Project>> completedProjects;
    private LiveData<List<Project>> unCompletedProjects;

    static ProjectRepository getInstance(Application application) {
        if (instance == null) {
            instance = new ProjectRepository(application);
        }
        return instance;
    }

    private ProjectRepository(Application application) {
        ProjectRoomDatabase db = ProjectRoomDatabase.getInstance(application);
        projectDao = db.projectDao();
        allProjects = projectDao.getAllProjects();
        completedProjects = projectDao.getProjects(true);
        unCompletedProjects = projectDao.getProjects(false);
        countCompletedProjects = projectDao.countProjects(true);
        countUncompletedProjects = projectDao.countProjects(false);
    }

    LiveData<List<Project>> getAllProjects() {
        return allProjects;
    }

    LiveData<List<Project>> getCompletedProjects() {
        return completedProjects;
    }

    LiveData<Integer> getCountUncompletedProjects() {
        return countUncompletedProjects;
    }

    LiveData<Integer> getCountCompletedProjects() {
        return countCompletedProjects;
    }

    LiveData<List<Project>> getUnCompletedProjects() {
        return unCompletedProjects;
    }

    void insert(Project Project) {
        new InsertAsyncTask(projectDao).execute(Project);
    }

    void update(Project Project) {
        new UpdateAsyncTask(projectDao).execute(Project);
    }

    void delete(Project Project) {
        new DeleteAsyncTask(projectDao).execute(Project);
    }

    private static class InsertAsyncTask extends AsyncTask<Project, Void, Void> {
        private ProjectDao asyncTaskDao;

        InsertAsyncTask(ProjectDao dao) {
            this.asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Project... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Project, Void, Void> {
        private ProjectDao asyncTaskDao;

        UpdateAsyncTask(ProjectDao dao) {
            this.asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Project... params) {
            asyncTaskDao.update(params[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Project, Void, Void> {
        private ProjectDao asyncTaskDao;

        DeleteAsyncTask(ProjectDao dao) {
            this.asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Project... params) {
            asyncTaskDao.delete(params[0]);
            return null;
        }
    }
}
