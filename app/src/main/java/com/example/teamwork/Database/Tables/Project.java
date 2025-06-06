/****************************************
 Fichier : Project.java
 Auteur : Kevin Larochelle
 Fonctionnalité :
 Class pour objet.

 Date : 05/09/2025

 Vérification :
 Date Nom Approuvé

 =========================================================
 Historique de modifications :
 Date Nom Description

 =========================================================
 ****************************************/

package com.example.teamwork.Database.Tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "projects")
public class Project {
    @PrimaryKey
    int id = 0;
    @ColumnInfo(name = "name")
    String name = "";
    @ColumnInfo(name = "description")
    String description = "";
    @ColumnInfo(name = "min_per_team")
    int min_per_team = 0;
    @ColumnInfo(name = "max_per_team")
    int max_per_team = 0;
    @ColumnInfo(name = "is_joinable")
    boolean joinable = false;
    @ColumnInfo(name = "is_creatable")
    boolean creatable = false;
    @ColumnInfo(name = "is_common_classes")
    boolean common_classes = false;
//    int group = 0;
//    String course = "";

    public Project() {}
    /** Constructeur pour objet Projet.
     * @param newId est int
     * @param newName est String
     * @param newDescription est String
     * @param newMin est int plus grand que 0
     * @param newMax est int plus petit que 99
     * @param newJoinable est boolean
     * @param newCreatable est boolean
     * @param newCommon est boolean */
    public Project(int newId, String newName, String newDescription, int newMin, int newMax,
                   boolean newJoinable, boolean newCreatable, boolean newCommon
//            , int newGroup, String newCourse
    ) {
        this.id = newId;
        this.name = newName;
        this.description = newDescription;
        this.min_per_team = newMin;
        this.max_per_team = newMax;
        this.joinable = newJoinable;
        this.creatable = newCreatable;
        this.common_classes = newCommon;
    }

    /** Setter pour l'Id du projet.
     * @param newId est int */
    public void setId(int newId) {this.id = newId; }
    /** Getter pour l'Id du projet.
     * @return this.id, int */
    public int getId() {return this.id; }

    /** Setter pour le nom du projet.
     * @param newName est String */
    public void setName(String newName) {this.name =newName; }
    /** Getter pour le nom du projet.
     * @return this.name, String */
    public String getName() {return this.name; }

    /** Setter pour la description du projet.
     * @param newDescription est String */
    public void setDescription(String newDescription) {this.description = newDescription; }
    /** Getter pour la description du projet.
     * @return this.description, String */
    public String getDescription() {return this.description; }

    /** Setter pour le minimum de membres par équipe dans le projet.
     * @param newMin est int plus grand que 0 */
    public void setMin_per_team(int newMin) {this.min_per_team = newMin; }
    /** Getter pour le minimum de membres par équipe dans le projet.
     * @return this.min_per_team, int plus grand que 0 */
    public int getMin_per_team() {return this.min_per_team; }

    /** Setter pour le maximum de membres par équipe dans le projet.
     * @param newMax est int plus petit que 99 */
    public void setMax_per_team(int newMax) {this.max_per_team = newMax; }
    /** Getter pour le maximum de membres par équipe dans le projet.
     * @return max_per_team, int plus petit que 99 */
    public int getMax_per_team() {return this.max_per_team; }

    /** Setter pour le statut rejoignable des équipes du projet.
     * @param  newJoinable est boolean */
    public void setJoinable(boolean newJoinable) {this.joinable = newJoinable; }
    /** Getter pour le statut rejoignabl des équipes du projet.
     * @return this.joinable, boolean */
    public boolean getJoinable() {return this.joinable; }

    /** Setter pour le statut de création d'équipe dans le projet.
     * @param newCreatable est boolean */
    public void setCreatable(boolean newCreatable) {this.creatable = newCreatable; }
    /** Getter pour le statut de création d'équipe dan le projet.
     * @return this.creatable, boolean */
    public boolean getCreatable() {return this.creatable; }

    /** Setter pour le statut de cours commun requis pour les équipes du projet.
     * @param newCommon est boolean */
    public void setCommon_classes(boolean newCommon) {this.common_classes = newCommon; }
    /** Getter pour le statut de cours commun requis dans les équipes du projet.
     * @return this.common_classes, boolean*/
    public boolean getCommon_classes() {return this.common_classes; }

//    /** Setter pour le code du groupe du projet.
//     * @param newGroup est int */
//    public void setGroup(int newGroup) {this.group = newGroup; }
//    /** Getter pour le code du groupe du projet.
//     * @return this.group, int*/
//    public int getGroup() {return this.group; }
//
//    /** Setter pour le nom du cours du projet.
//     * @param newCourse est string */
//    public void setCourse(String newCourse) {this.course = newCourse; }
//    /** Getter pour lel nom du cours du projet.
//     * @return this.common_classes, boolean*/
//    public String getCourse() {return this.course; }

    @Override
    public String toString() {
        return ("Project: " + this.name + ", " + this.description + ", " + this.min_per_team +
                "-" + this.max_per_team + ", " + this.joinable + ", " + this.creatable + ", " + this.common_classes
//                + ", " + "Group: " + this.group + ", Course: " + this.course
        );
    }
}
