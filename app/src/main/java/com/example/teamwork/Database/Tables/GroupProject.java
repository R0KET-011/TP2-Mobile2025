/****************************************
 Fichier : GroupProject.java
 Auteur : Kevin Larochelle
 Fonctionnalité :
 Liens entre Projet et Groupe.

 Date : 05/09/2025

 Vérification :
 Date Nom Approuvé

 =========================================================
 Historique de modifications :
 Date Nom Description

 =========================================================
 ****************************************/

package com.example.teamwork.Database.Tables;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(
        tableName = "group_projects",
        primaryKeys = {"id_group", "id_project"},
        foreignKeys = {
                @ForeignKey(
                        entity = Group.class,
                        parentColumns = "id_group",
                        childColumns = "id_group"
                ),
                @ForeignKey(
                        entity = Project.class,
                        parentColumns = "id",
                        childColumns = "id_project",
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class GroupProject {
    int id_group = 0;
    int id_project = 0;

    public GroupProject(){}
    /** Constructeur de GroupProject
     * @param id_project est int
     * @param id_group est int*/
    public GroupProject(int id_group, int id_project) {
        this.id_group = id_group;
        this.id_project = id_project;
    }

    /** Setter du id de group du lien GroupProject
     * @param id_group est int*/
    public void setId_group(int id_group) {
        this.id_group = id_group;
    }

    /** Getter du id de group du lien GroupProject
     * @return this.id_group, int*/
    public int getId_group() {
        return id_group;
    }

    /** Setter du id de projet du lien GroupProject
     * @param id_project est int*/
    public void setId_project(int id_project) {
        this.id_project = id_project;
    }

    /** Getter du id de projet du lien GroupProject
     * @return this.id_project, int*/
    public int getId_project() {
        return id_project;
    }
}
