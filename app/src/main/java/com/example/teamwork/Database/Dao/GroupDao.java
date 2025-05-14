/****************************************
 Fichier GroupDao.java
 Auteur : Kevin Larochelle
 Fonctionnalité :
 SQL pour base de donnée mobile.

 Date : 05/13/2025

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
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.teamwork.Database.Tables.Group;

import java.util.List;

@Dao
public interface GroupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGroups(List<Group> groups);

    @Query("SELECT * FROM groups")
    List<Group> getAllGroups();

    @Query("SELECT * FROM groups WHERE id_group = (SELECT id_group FROM group_projects WHERE id_project = :id_project)")
    Group getGroupByIdProject(int id_project);
}
