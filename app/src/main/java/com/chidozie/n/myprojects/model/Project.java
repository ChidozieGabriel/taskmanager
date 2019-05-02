package com.chidozie.n.myprojects.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by Chidozie on 9/20/2018.
 */
@Entity
public class Project implements android.os.Parcelable {
    public static final Creator<Project> CREATOR = new Creator<Project>() {
        @Override
        public Project createFromParcel(Parcel source) {
            return new Project(source);
        }

        @Override
        public Project[] newArray(int size) {
            return new Project[size];
        }
    };

//
//    public static final String SORT_ORDER() {
//        return "urgency ASC," +
//            "isImportant ASC," +
//            "trivial ASC";
//    }

    @PrimaryKey
    @NonNull
    private String id;
    private String title;
    private boolean completed;
    private float urgency;
    private boolean isImportant;
    private Date dateFinished;
    private Date dateCreated;
    private Date deadlineDate;

    public Project() {
        this("");
    }

    @Ignore
    public Project(String title) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        dateCreated = new Date();
        dateFinished = new Date();
        deadlineDate = new Date();
//        Calendar cal = GregorianCalendar.getInstance();
//        cal.setTime(deadlineDate);
//        int day = cal.get(Calendar.DAY_OF_MONTH);
//        cal.set(Calendar.DAY_OF_MONTH, day + 2);
//        deadlineDate = cal.getTimeRemaining();
    }

    protected Project(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.completed = in.readByte() != 0;
        this.urgency = in.readFloat();
        this.isImportant = in.readByte() != 0;
        long tmpDateFinished = in.readLong();
        this.dateFinished = tmpDateFinished == -1 ? null : new Date(tmpDateFinished);
        long tmpDateCreated = in.readLong();
        this.dateCreated = tmpDateCreated == -1 ? null : new Date(tmpDateCreated);
        long tmpDeadlineDate = in.readLong();
        this.deadlineDate = tmpDeadlineDate == -1 ? null : new Date(tmpDeadlineDate);
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public float getUrgency() {
        return urgency;
    }

    public void setUrgency(float urgency) {
        this.urgency = urgency;
    }

    public void setUrgency() {
        final long timeCreated = dateCreated.getTime();
        final long deadlineTime = deadlineDate.getTime();
        final long currentTime = new Date().getTime();

        final long totalTime = deadlineTime - timeCreated;
        final long timeTaken = currentTime - timeCreated;

        urgency = (float) 100 * timeTaken / totalTime;

        Log.d("kkk", "urgency: " + title + ": " + urgency);
    }

    public boolean isUrgent() {
        return urgency > 50;
    }

    public boolean isDeadline() {
        return urgency >= 100;
    }

    private Duration duration(long startTime, long endTime) {
        int period;
        long value;
        long timeDiff = endTime - startTime;
        boolean isPast = false;
        if (timeDiff < 0) {
            timeDiff = -timeDiff;
            isPast = true;
        }

        long secs = timeDiff / 1000;
        long mins = secs / 60;
        long hrs = mins / 60;
        long days = hrs / 24;
        long wks = days / 7;
        long mths = wks / 4;
        long yrs = mths / 12;

        if (yrs > 0) {
            period = Duration.YEARS;
            value = yrs;
        } else if (mths > 0) {
            period = Duration.MONTHS;
            value = mths;
        } else if (wks > 0) {
            period = Duration.WEEKS;
            value = wks;
        } else if (days > 0) {
            period = Duration.DAYS;
            value = days;
        } else if (hrs > 0) {
            period = Duration.HOURS;
            value = hrs;
        } else if (mins > 0) {
            period = Duration.MINUTES;
            value = mins;
        } else {
            period = Duration.SECONDS;
            value = secs;
        }

        return new Duration((int) value, period, isPast);
    }

    public Duration getDeadlineDuration() {
        return duration(new Date().getTime(), deadlineDate.getTime());
    }

    public Duration getCompletedDuration() {
        return duration(dateFinished.getTime(), new Date().getTime());
    }


    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateFinished() {
        return dateFinished;
    }

    public void setDateFinished(Date dateFinished) {
        this.dateFinished = dateFinished;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, title, completed, urgency, isImportant, dateFinished, dateCreated, deadlineDate);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return completed == project.completed &&
            Float.compare(project.urgency, urgency) == 0 &&
            isImportant == project.isImportant &&
            Objects.equals(id, project.id) &&
            Objects.equals(title, project.title) &&
            Objects.equals(dateFinished, project.dateFinished) &&
            Objects.equals(dateCreated, project.dateCreated) &&
            Objects.equals(deadlineDate, project.deadlineDate);
    }

    public boolean isImportant() {
        return isImportant;
    }

    public void setImportant(boolean important) {
        isImportant = important;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeByte(this.completed ? (byte) 1 : (byte) 0);
        dest.writeFloat(this.urgency);
        dest.writeByte(this.isImportant ? (byte) 1 : (byte) 0);
        dest.writeLong(this.dateFinished != null ? this.dateFinished.getTime() : -1);
        dest.writeLong(this.dateCreated != null ? this.dateCreated.getTime() : -1);
        dest.writeLong(this.deadlineDate != null ? this.deadlineDate.getTime() : -1);
    }
}
