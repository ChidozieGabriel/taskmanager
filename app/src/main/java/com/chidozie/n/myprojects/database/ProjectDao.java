package com.chidozie.n.myprojects.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.chidozie.n.myprojects.model.Project;

import java.util.List;

/**
 * Created by Chidozie on 10/31/2018.
 */
@Dao
public interface ProjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Project project);

    @Update
    void update(Project Project);

    @Delete
    void delete(Project Project);

    @Query("SELECT * FROM Project WHERE id = :id")
    Project getProject(String id);

    @Query("DELETE FROM Project")
    void deleteAll();

    @Query("SELECT * FROM Project ORDER BY dateCreated ASC")
    LiveData<List<Project>> getAllProjects();

    @Query("SELECT * FROM Project WHERE completed = :isCompleted " +
        "ORDER BY urgency DESC, isImportant  DESC, deadlineDate")
    LiveData<List<Project>> getProjects(boolean isCompleted);

    @Query("SELECT * FROM Project WHERE completed = :isCompleted " +
        "ORDER BY :sortOrder")
    LiveData<List<Project>> getProjects(boolean isCompleted, String sortOrder);

    @Query("SELECT COUNT(completed) FROM Project WHERE completed = :isCompleted")
    LiveData<Integer> countProjects(boolean isCompleted);
}
