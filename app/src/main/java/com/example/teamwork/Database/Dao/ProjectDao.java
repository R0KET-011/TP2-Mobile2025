/****************************************
 Fichier ProjectDao.java
 Auteur : Kevin Larochelle
 Fonctionnalité :
 SQL pour base de donnée mobile.

 Date : 05/09/2025

 Vérification :
 Date Nom Approuvé

 =========================================================
 Historique de modifications :
 Date Nom Description

 =========================================================
 ****************************************/
package com.example.teamwork.Database.Dao;

import androidx.lifecycle.LiveData;
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
    void insertAll(Project... projects);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProjects(List<Project> projects);

    @Update
    void update(Project project);

    @Delete
    void delete(Project project);

    @Query("SELECT * FROM projects")
    LiveData<List<Project>> getAllProjects();

    @Query("SELECT * FROM projects WHERE id = :id")
    LiveData<Project> getProjectById(int id);

    @Query("SELECT p.* FROM projects p WHERE p.id IN " +
            "(SELECT t.project_id FROM teams t INNER JOIN team_students ts ON ts.team_id = t.id WHERE ts.student_id = :userId)")
    LiveData<List<Project>> getProjectByUser(int userId);

    @Query("SELECT COUNT(id) FROM projects")
    int getProjectTableSize();
}