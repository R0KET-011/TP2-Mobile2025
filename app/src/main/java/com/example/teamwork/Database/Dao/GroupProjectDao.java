/****************************************
 Fichier GroupProjectDao.java
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

import com.example.teamwork.API.Repository.GroupProjectRepository;
import com.example.teamwork.Database.Tables.GroupProject;

import java.util.List;

@Dao
public interface GroupProjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGroupProject(List<GroupProject> groupProject);
}
