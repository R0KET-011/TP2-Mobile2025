/****************************************
 Fichier : CourseDao.java
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

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.teamwork.Database.Tables.Course;

import java.util.List;

@Dao
public interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourses(List<Course> courses);

    @Query("SELECT * FROM courses")
    List<Course> getAllCourses();
}
