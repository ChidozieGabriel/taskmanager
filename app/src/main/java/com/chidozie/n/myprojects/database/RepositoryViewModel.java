package com.chidozie.n.myprojects.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.chidozie.n.myprojects.model.Project;

import java.util.List;

/**
 * Created by Chidozie on 10/31/2018.
 */
public class RepositoryViewModel extends AndroidViewModel {
    private final ProjectRepository repository;
    private final LiveData<List<Project>> allProjects;
    private final MutableLiveData<Project> selected;
    private final LiveData<List<Project>> completedProjects;
    private final LiveData<List<Project>> unCompletedProjects;
    private final LiveData<Integer> countUncompletedProjects;
    private final LiveData<Integer> countCompletedProjects;

    public RepositoryViewModel(@NonNull Application application) {
        super(application);
        repository = ProjectRepository.getInstance(application);
        allProjects = repository.getAllProjects();
        completedProjects = repository.getCompletedProjects();
        unCompletedProjects = repository.getUnCompletedProjects();
        selected = new MutableLiveData<>();
        countUncompletedProjects = repository.getCountUncompletedProjects();
        countCompletedProjects = repository.getCountCompletedProjects();
    }

    public LiveData<Integer> getCountUncompletedProjects() {
        return countUncompletedProjects;
    }

    public LiveData<Integer> getCountCompletedProjects() {
        return countCompletedProjects;
    }

    public LiveData<List<Project>> getAllProjects() {
        return allProjects;

    }

    public LiveData<List<Project>> getCompletedProjects() {
        return completedProjects;
    }

    public LiveData<List<Project>> getUnCompletedProjects() {
        return unCompletedProjects;
    }

    public void insert(Project project) {
        repository.insert(project);
    }

    public void update(Project project) {
        repository.update(project);
    }

    public void delete(Project project) {
        repository.delete(project);
    }

    public void select(Project project) {
        selected.setValue(project);
    }

    public LiveData<Project> getSelected() {
        return selected;
    }
}
