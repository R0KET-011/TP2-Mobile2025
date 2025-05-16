package com.example.teamwork.Database.Tables;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(
        tableName = "team_students",
        primaryKeys = {"team_id", "student_id"},
        foreignKeys = {
                @ForeignKey(
                        entity = Team.class,
                        parentColumns = "id",
                        childColumns = "team_id",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Student.class,
                        parentColumns = "id",
                        childColumns = "student_id",
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class TeamStudent {
    /**
     * L'ID unique de l'équipe
     */
    @ColumnInfo(name = "team_id")
    private int teamId;

    /**
     * L'ID unique de l'étudiant
     */
    @ColumnInfo(name = "student_id")
    private int studentId;

    /**
     * Le commentaire sur l'étudiant
     */
    @ColumnInfo(name = "comment")
    @Nullable
    private String comment;

    /**
     *
     * @param teamId L'ID unique de l'équipe
     * @param studentId L'ID unique de l'étudiant
     * @param comment Le commentaire sur l'étudiant
     */
    public TeamStudent(int teamId, int studentId, @Nullable String comment) {
        this.teamId = teamId;
        this.studentId = studentId;
        this.comment = comment;
    }

    /**
     * Pour obtenir l'ID unique de l'équipe
     * @return un int représentant l'ID unique
     */
    public int getTeamId() {
        return teamId;
    }

    /**
     * Définit l'ID de l'équipe
     * @param teamId un int représentant l'ID unique de l'équipe
     */
    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    /**
     * Pour obtenir l'ID unique de l'étudiant
     * @return un int représentant l'ID unique
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     * Définit l'ID de l'étudiant
     * @param studentId un int représentant l'ID unique de l'étudiant
     */
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    /**
     * Pour obtenir le commentaire de l'étudiant
     * @return un string représentant le commentaire
     */
    @Nullable
    public String getComment() {return comment;}

    /**
     * Définit le commentaire de l'étudiant
     * @param comment un string représentant le commentaire
     */
    public void setComment(@Nullable String comment) {this.comment = comment;}

    /**
     * Retourne la jonction de teamId et studentId sous forme de texte
     * @return un string qui est la jonction de teamId et studentId
     */
    @Override
    public String toString() {
        return ("TeamStudent: " + this.getTeamId() + ", " + this.getStudentId());
    }
}