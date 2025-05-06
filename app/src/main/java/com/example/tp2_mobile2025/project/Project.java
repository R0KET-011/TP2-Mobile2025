/****************************************
 Fichier : Project.java
 Auteur : Kevin Larochelle
 Fonctionnalité :
 Class pour les projets

 Date : 05/05/2025

 Vérification :
 Date Nom Approuvé

 =========================================================
 Historique de modifications :
 Date Nom Description

 =========================================================
 ****************************************/

package com.example.tp2_mobile2025.project;

import com.example.tp2_mobile2025.team.Team;

import java.util.ArrayList;

public class Project {
    int id = 0;
    String name = "";
    String description = "";
    int min_per_team = 0;
    int max_per_team = 0;
    boolean joinable = false;
    boolean creatable = false;
    boolean common_classes = false;
    ArrayList<Team> teams = new ArrayList<Team>();

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
                   boolean newJoinable, boolean newCreatable, boolean newCommon ) {
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
    public void setDescrption(String newDescription) {this.description = newDescription; }
    /** Getter pour la description du projet.
     * @return this.description, String */
    public String getDescription() {return this.description; }

    /** Setter pour le minimum de membres par équipe dans le projet.
     * @param newMin est int plus grand que 0 */
    public void setMinPetTeam(int newMin) {this.min_per_team = newMin; }
    /** Getter pour le minimum de membres par équipe dans le projet.
     * @return this.min_per_team, int plus grand que 0 */
    public int getMinPerTeam() {return this.min_per_team; }

    /** Setter pour le maximum de membres par équipe dans le projet.
     * @param newMax est int plus petit que 99 */
    public void setMax_per_team(int newMax) {this.max_per_team = newMax; }
    /** Getter pour le maximum de membres par équipe dans le projet.
     * @return max_per_team, int plus petit que 99 */
    public int getMaxPerTeam() {return this.max_per_team; }

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
    public void setCommonClasses(boolean newCommon) {this.common_classes = newCommon; }
    /** Getter pour le statut de cours commun requis dans les équipes du projet.
     * @return this.common_classes, boolean*/
    public boolean getCommonClasses() {return this.common_classes; }

}
