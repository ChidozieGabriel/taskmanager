package com.chidozie.n.myprojects.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;
import android.support.annotation.NonNull;
import android.view.View;

import com.chidozie.n.myprojects.App;
import com.chidozie.n.myprojects.BR;
import com.chidozie.n.myprojects.R;
import com.chidozie.n.myprojects.util.ProjectUtils;

import java.util.Date;
import java.util.Objects;

/**
 * Created by Chidozie on 9/20/2018.
 */

public class ProjectViewModel extends AndroidViewModel implements Observable {
    //    private final RepositoryViewModel repo;
    private Project project;
    private PropertyChangeRegistry callbacks = new PropertyChangeRegistry();


    public ProjectViewModel(@NonNull Application application) {
        super(application);
    }

//    public ProjectViewModel(RepositoryViewModel repo) {
//        this.repo = repo;
//    }

    @Bindable
    public String getTitle() {
        return project.getTitle();
    }

    public void setTitle(String title) {
        if (!Objects.equals(project.getTitle(), title)) {
            project.setTitle(title);
            notifyPropertyChanged(BR.title);
        }
    }

    @Bindable
    public String getImportance() {
        int i = project.isImportant() ? 1 : 0;

        return App.getRes().getStringArray(R.array.category_array)[i];
    }


    public void setImportance(boolean importance) {
        if (project.isImportant() != importance) {
            project.setImportant(importance);
            notifyPropertyChanged(BR.importance);
        }
    }

    @Bindable
    public String getDate() {
        return ProjectUtils.dateFormat(project.getDeadlineDate());
    }

    public void setDate(Date date) {
        if (!Objects.equals(project.getDeadlineDate(), date)) {
            project.setDeadlineDate(date);
            notifyPropertyChanged(BR.date);
        }
    }

    public void setDurationValue() {
        notifyPropertyChanged(BR.durationValue);
    }

    public void setDuration() {
        setDurationPeriod();
        setDurationValue();
    }

    @Bindable
    public String getDurationValue() {
        final Duration duration = project.getDeadlineDuration();
        return String.valueOf(duration.getValue());
    }

    public void setDurationPeriod() {
        notifyPropertyChanged(BR.durationPeriod);
    }

    @Bindable
    public String getDurationPeriod() {
        final Duration duration = project.getDeadlineDuration();
        return duration.getPeriod();
    }

    @Bindable
    public String getTime() {
        return ProjectUtils.timeFormat(project.getDeadlineDate());
    }

    public void setTime(Date time) {
        if (!Objects.equals(project.getDeadlineDate(), time)) {
            project.setDeadlineDate(time);
            notifyPropertyChanged(BR.time);
        }
    }


    @Bindable
    public boolean isCompleted() {
        return project.isCompleted();
    }

    public void setCompleted(boolean completed) {
        if (completed != project.isCompleted()) {
            project.setCompleted(completed);
            notifyPropertyChanged(BR.completed);
            notifyPropertyChanged(BR.markerColor);
        }

    }

    @Bindable
    public String getTimeRemaining() {
        final Duration duration = project.getDeadlineDuration();
        return App.getRes().getString(R.string.time_remaining,
            duration.getPeriod(), duration.getValue());
    }

    @Bindable
    public String getTimePast() {
        final Duration dur;
        if (project.isCompleted()) {
            dur = project.getCompletedDuration();
        } else {
            dur = project.getDeadlineDuration();
        }
        return App.getRes().getString(R.string.time_past, dur.getPeriod(), dur.getValue());
    }

    @Bindable
    public int getTimePastVisibility() {
        return project.isDeadline() ? View.VISIBLE : View.GONE;

    }

    @Bindable
    public int getTimeRemainingVisibility() {
        if (project.isDeadline() || project.isCompleted()) {
            return View.GONE;
        } else {
            return View.VISIBLE;
        }

    }

    @Bindable
    public boolean isUrgent() {
        return project.isUrgent();
    }

    public void setProject(Project project) {
        if (!project.equals(this.project)) {
            this.project = project;
            this.project.setUrgency();
//            notifyChange();
        }
    }

    @Bindable
    public int getMarkerColor() {
        if (project.isCompleted()) {
            return App.getRes().getColor(R.color.markerCompleted);
        }

        if (project.isDeadline()) {
            return App.getRes().getColor(R.color.markerDeadline);
        }

        if (project.isUrgent()) {
            return App.getRes().getColor(R.color.markerUrgent);
        }

        if (project.isImportant()) {
            return App.getRes().getColor(R.color.markerImportant);
        } else {
            return App.getRes().getColor(R.color.markerNeutral);

        }
    }

    @Bindable
    public int getDividerColor() {
        if (project.isImportant()) {
            return App.getRes().getColor(R.color.dividerImportant);
        } else {
            return App.getRes().getColor(R.color.dividerNeutral);

        }
    }

//    @Override
//    public void notifyPropertyChanged(int fieldId) {
////        super.notifyPropertyChanged(fieldId);
////        repo.update(project);
//    }


    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        callbacks.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        callbacks.remove(callback);
    }


    private void notifyChange() {
        callbacks.notifyCallbacks(this, 0, null);

    }

    private void notifyPropertyChanged(int fieldId) {
        callbacks.notifyCallbacks(this, fieldId, null);
    }
}

