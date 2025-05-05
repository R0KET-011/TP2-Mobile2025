package com.example.tp2_mobile2025.project;

public class Project {
    int id = 0;
    String name = "";
    String description = "";
    int min_per_team = 0;
    int max_per_team = 0;
    boolean joinable = false;
    boolean creatable = false;
    boolean common_classes = false;

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

    // Setter et Getter pour l'Id du projet.
    /** @param newId est int */
    public void setId(int newId) {this.id = newId; }
    /** @return this.id, int */
    public int getId() {return this.id; }

    // Setter et Getter pour le nom du projet.
    /** @param newName est int */
    public void setName(String newName) {this.name =newName; }
    /** @return this.name, int */
    public String getName() {return this.name; }

    // Setter et Getter pour la description d'un projet.
    /** @param newDescription est String */
    public void setDescrption(String newDescription) {this.description = newDescription; }
    /** @return this.description, String */
    public String getDescription() {return this.description; }

    // Setter et Getter pour le minimum de membres dans une équipe dans le projet.
    /** @param newMin est int plus grand que 0 */
    public void setMinPetTeam(int newMin) {this.min_per_team = newMin; }
    /** @return this.min_per_team, int plus grand que 0 */
    public int getMinPerTeam() {return this.min_per_team; }

    // Setter et Getter pour le maximum de membres dans une équipe dans le projet
    /** @param newMax est int plus petit que 99 */
    public void setMax_per_team(int newMax) {this.max_per_team = newMax; }
    /** @return max_per_team, int plus petit que 99 */
    public int getMaxPerTeam() {return this.max_per_team; }

    // Setter et Getter pour le statut rejoignable des équipes du projet.
    /** @param  newJoinable est boolean */
    public void setJoinable(boolean newJoinable) {this.joinable = newJoinable; }
    /** @return this.joinable, boolean */
    public boolean getJoinable() {return this.joinable; }

    // Setter et Getter pour le statut de création d'équipe du projet.
    /** @param newCreatable est boolean */
    public void setCreatable(boolean newCreatable) {this.creatable = newCreatable; }
    /** @return this.creatable, boolean */
    public boolean getCreatable() {return this.creatable; }

    // Setter et Getter pour le statut des cours commun requis pour les équipes du projet.
    /** @param newCommon est boolean */
    public void setCommonClasses(boolean newCommon) {this.common_classes = newCommon; }
    /** @return this.common_classes, boolean*/
    public boolean getCommonClasses() {return this.common_classes; }

}
