package com.example.teamwork.Database.Tables;

import android.content.Context;

import androidx.room.ColumnInfo;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.teamwork.R;

@Entity(
        tableName = "teams",
        foreignKeys = @ForeignKey(
                entity = Project.class,
                parentColumns = "id",
                childColumns = "project_id",
                onDelete = ForeignKey.CASCADE
        )
)
public class Team {

    /**
     * ID unique qui représente l'équipe
     */
    @PrimaryKey
    private int id;

    /**
     * Le nom de l'équipe
     */
    private String name;

    /**
     * L'état de l'équipe (Totalement conforme, Partiellement conforme, Non conforme)
     */
    private String state;

    /**
     * La description de l'équipe
     */
    private String description;

    /**
     * L'ID unique du projet lié à l'équipe
     */
    @ColumnInfo(name = "project_id")
    private int projectId;

    /**
     * Constructeur vide
     */
    public Team() {}

    /**
     * Constructeur de l'équipe
     * @param id ID unique qui représente l'équipe
     * @param name Le nom de l'équipe
     * @param state L'état de l'équipe (Totalement conforme, Partiellement conforme, Non conforme)
     * @param description La description de l'équipe
     * @param projectId L'ID unique du projet lié à l'équipe
     */
    public Team(int id, String name, String state, String description, int projectId) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.description = description;
        this.projectId = projectId;
    }

    /**
     * Pour obtenir l'ID unique de l'équipe
     * @return un int représentant l'ID unique
     */
    public int getId() {
        return id;
    }

    /**
     * Définit l'ID de l'équipe
     * @param id un int représentant l'ID unique de l'équipe
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Pour obtenir le nom de l'équipe
     * @return une chaîne contenant le nom de l'équipe
     */
    public String getName() {
        return name;
    }

    /**
     * Définit le nom de l'équipe
     * @param name une chaîne représentant le nom de l'équipe
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Pour obtenir l'état de l'équipe (Totalement conforme, Partiellement conforme, Non conforme)
     * @return une chaîne contenant l'état de l'équipe
     */
    public String getState() {
        return state;
    }

    /**
     * Définit l'état de l'équipe (Totalement conforme, Partiellement conforme, Non conforme)
     * @param state une chaîne représentant l'état de l'équipe
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Pour obtenir la description de l'équipe
     * @return une chaîne contenant la description de l'équipe
     */
    public String getDescription() {
        return description;
    }

    /**
     * Définit la description de l'équipe
     * @param description la nouvelle description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Pour obtenir l'ID unique du projet lié à l'équipe
     * @return un int représentant l'ID du projet
     */
    public int getProjectId() {
        return projectId;
    }

    /**
     * Définit l'ID unique du projet lié à l'équipe
     * @param projectId un int représentant l'ID du projet
     */
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    /**
     * Permet d'obtenir la couleur selon l'état :
     * Totalement conforme = vert
     * Partiellement conforme = jaune
     * Non conforme = rouge
     *
     * @param context le contexte de l'activité dans laquelle on appelle cette méthode
     * @return l'ID de la couleur définie dans res/colors.xml
     */
    public int  getStateColor(Context context){
        switch (state) {
            case "Totalement conforme":
                return context.getColor(R.color.color_green);
            case "Partiellement conforme":
                return context.getColor(R.color.color_yellow);
            default:
                return context.getColor(R.color.color_red);
        }
    }

    /**
     * Retourne l'ID du projet sous forme de texte
     * @return une chaîne sous la forme "project_id = *ProjectId*"
     */
    public String toString() {
        return "project_id = " + this.getProjectId();
    }
}
