/****************************************
 Fichier : Group.java
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

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "groups",
        foreignKeys =  @ForeignKey(
                entity = Group.class,
                parentColumns = "id_group",
                childColumns = "id_course"
        )
)

public class Group {
    @PrimaryKey
    int id_group = 0;
    int code = 0;
    int id_course = 0;
    String semester = null;

    public Group(){}
    /** Constructeur pour les groupes
     * @param newId est int
     * @param newCode est int de 5 chiffres
     * @param newCourse est int
     * @param newSemester est int*/
    public Group(int newId, int newCode, int newCourse, String newSemester) {
        this.id_group = newId;
        this.code = newCode;
        this.id_course = newCourse;
        this.semester = newSemester;
    }

    /** Setterpour le id du groupe
     * @param id_group est int */
    public void setId_group(int id_group) {
        this.id_group = id_group;
    }

    /** Getter pour le id du groupe
     * @return id_group, int*/
    public int getId_group() {
        return id_group;
    }

    /** Setter pour le code du groupe
     * @param code est int à 5 chiffres*/
    public void setCode(int code) {
        this.code = code;
    }

    /** Getter pour le code du groupe
     * @return code est int*/
    public int getCode() {
        return this.code;
    }

    /** Setter pour le id du cours
     * @id_course est int*/
    public void setId_course(int id_course) {
        this.id_course = id_course;
    }

    /** Geter pour le id du cours
     * @return id_course, int*/
    public int getId_course() {
        return id_course;
    }

    /** Setter pour le id de la session
     * @param semester est string*/
    public void setSemester(String semester) {
        this.semester = semester;
    }

    /** Getter pour le id de la session
     * @return id_semester, int*/
    public String getSemester() {
        return semester;
    }
}
