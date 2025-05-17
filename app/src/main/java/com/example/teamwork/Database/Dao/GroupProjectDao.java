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

    /** Insert une relation groupe-projet dans la base de donnée locale
     * @param groupProject est une collection de json de relation groupe-projet*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGroupProject(List<GroupProject> groupProject);

    /** Insert une relation groupe-projet
     * @param groupProject est un objet */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEntry(GroupProject groupProject);

}
