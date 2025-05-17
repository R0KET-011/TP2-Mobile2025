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

    /** Insert un cours dans la base de donnée locale
     * @param courses est collection de json de cours*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourses(List<Course> courses);

    /** return une liste de cours */
    @Query("SELECT * FROM courses")
    List<Course> getAllCourses();

    /** return le nom du cours d'un groupe */
    @Query("SELECT name FROM courses WHERE id = (SELECT id_group FROM groups WHERE code = :groupCode)")
    String getCourseName(int groupCode);
}
