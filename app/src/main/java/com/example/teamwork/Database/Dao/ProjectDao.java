package com.example.teamwork.Database.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.teamwork.Database.Tables.Project;

import java.util.List;

@Dao
public interface ProjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Project project);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Project> projects);

    @Update
    void update(Project project);

    @Delete
    void delete(Project project);

    @Query("SELECT * FROM projects")
    List<Project> getAllProjects();

    @Query("SELECT * FROM projects WHERE id = :id")
    Project getProjectById(int id);
}