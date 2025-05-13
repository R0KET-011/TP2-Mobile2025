/****************************************
 Fichier : Course.java
 Auteur : Kevin Larochelle
 Fonctionnalité :
 Objet pour cours.

 Date : 05/13/2025

 Vérification :
 Date Nom Approuvé

 =========================================================
 Historique de modifications :
 Date Nom Description

 =========================================================
 ****************************************/
package com.example.teamwork.Database.Tables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "courses")
public class Course {

    @PrimaryKey
    int id = 0;
    String code = null;
    String name = null;

    public Course(){}
    /** Constructeur pour objet Course
     * @param newId est int
     * @param newCode est string de 3 chiffres, dash, 3 chiffres, dash et 2 lettres majuscules
     * @param newName est string*/
    public Course(int newId, String newCode, String newName) {
        this.id = newId;
        this.code = newCode;
        this.name = newName;
    }

    /** Setter pour l'id du cours.
     * @param id est int */
    public void setId(int id) {
        this.id = id;
    }

    /** Getter pour l'id du cours
     * @return this.id, int*/
    public int getId() {
        return id;
    }

    /** Setter pour le code du cours
     * @param code est String de 3 chiffres, dash, 3 chiffres, dash et 2 lettres majuscules.*/
    public void setCode(String code) {
        this.code = code;
    }

    /** Getter pour le code du cours
     * @return this.code*/
    public String getCode() {
        return code;
    }

    /** Setter pour le nom du cours
     * @param name est String*/
    public void setName(String name) {
        this.name = name;
    }

    /** Getter pour le nom du cours
     * @return this.name, String*/
    public String getName() {
        return name;
    }
}
