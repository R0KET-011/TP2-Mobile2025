package com.example.teamwork.Database.Dao;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.teamwork.Database.Tables.Student;
import com.example.teamwork.Database.Tables.Team;
import com.example.teamwork.Database.Tables.TeamStudent;

import java.util.List;

@Dao
public interface TeamStudentDao {

    /**
     * Permet d'inserer une jonction de teamId et studentId
     * @param teamStudent la jonction de teamId, studentId et un commentaire
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(TeamStudent teamStudent);

    /**
     * Permet d'inserer plusieurs jonction de teamId et studentId
     * @param teamStudents plusieurs jonction de teamId, studentId et un commentaire
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(TeamStudent... teamStudents);

    /**
     * Permet d'inserer plusieurs jonction de teamId et studentId sous forme de liste
     * @param teamStudents plusieurs jonction de teamId, studentId et un commentaire sous forme de liste
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTeamStudent(List<TeamStudent> teamStudents);

    /**
     * supprime la jonction de teamId, studentId et un commentaire
     * @param teamStudent l'instance d'une jonction de teamId, studentId et un commentaire
     */
    @Delete
    void delete(TeamStudent teamStudent);

    /**
     * Retourne tout les team_student de la base de donnés
     * @return un LiveData qui contien une liste de jonction de teamId, studentId et un commentaire
     */
    @Query("SELECT * FROM team_students")
    LiveData<List<TeamStudent>> getTeamStudent();

    /**
     * Retourne tout les étudiants d'un équipe
     * @param teamId l'ID de l'équipe
     * @return les étudiants d'un équipe dans une liste dans un LiveData
     */
    @Query("SELECT s.* FROM students s " +
            "INNER JOIN team_students ts ON s.id = ts.student_id " +
            "WHERE ts.team_id = :teamId")
    LiveData<List<Student>> getStudentsForTeam(int teamId);

    /**
     * Retourne tout les équipes d'un étudiant
     * @param studentId l'ID de l'étudiant
     * @return les équipes d'un étudiant dans une liste dans un LiveData
     */
    @Query("SELECT t.* FROM teams t " +
            "INNER JOIN team_students ts ON t.id = ts.team_id " +
            "WHERE ts.student_id = :studentId")
    LiveData<List<Team>> getTeamsForStudent(int studentId);

    /**
     * Supprime tout les étudiants d'un équipe
     * @param teamId l'ID de l'équipe
     */
    @Query("DELETE FROM team_students WHERE team_id = :teamId")
    void deleteAllStudentsFromTeam(int teamId);

    /**
     * Donne le compte d'étudiants pour un équipe
     * @param teamId l'ID de l'équipe
     * @return un int qui est le compte d'étudiants pour un équipe
     */
    @Query("SELECT COUNT(*) FROM team_students WHERE team_id = :teamId")
    LiveData<Integer> getStudentCountForTeam(int teamId);

    /**
     * Donne le compte d'étudiants pour un équipe sans compter l'étudiant X
     * @param teamId l'ID de l'équipe
     * @param StudentId l'ID de l'étudiant a exclure
     * @return un int qui est le compte d'étudiants pour un équipe exculant l'étudiant X qui contenu dans un LiveData
     */
    @Query("SELECT COUNT(*) FROM team_students WHERE team_id = :teamId AND student_id != :StudentId")
    LiveData<Integer> getStudentCountForTeamExcluding(int teamId, int StudentId);

    /**
     * Retourne le commentaire relier a l'étudiant et l'équipe donnée
     * @param studentId l'ID de l'étudiant
     * @param teamId l'ID de l'équipe
     * @return un string qui est contenu dans un LiveData
     */
    @Query("SELECT comment FROM team_students WHERE team_id = :teamId AND student_id = :studentId")
    LiveData<String> getCommentFromStudentTeam(int studentId, int teamId);

    /**
     * Retourne l'instance d'un TeamStudent pour un étudiant donnée dans un équipe donnée
     * @param teamId l'ID de l'équipe
     * @param studentId l'ID de l'étudiant
     * @return Un instancde d'un TeamStudent contenu dans un LiveData
     */
    @Query("SELECT * FROM team_students WHERE team_id = :teamId AND student_id = :studentId")
    LiveData<TeamStudent> getStudentInTeam(int teamId, int studentId);

    /**
     * Supprime l'étudiant de tout les équipes d'un projet donnée
     * @param studentId l'ID de l'étudiant
     * @param projectId l'ID du projet
     */
    @Query("DELETE FROM team_students WHERE student_id = :studentId AND team_id IN (SELECT id FROM teams WHERE project_id = :projectId)")
    void deleteStudentFromProject(int studentId, int projectId);


    /**
     * Ajoute un commentaire pour un étudiant donnée dans un équipe donnée
     * @param comment Un string qui est le commentaire
     * @param studentId l'ID de l'étudiant
     * @param teamId l'ID de l'équipe
     */
    @Query("UPDATE team_students SET comment = :comment WHERE student_id = :studentId AND team_id = :teamId")
    void setCommentWhereStudentTeam(@Nullable String comment, int studentId, int teamId);

    /**
     * Supprime la jonction de teamId et studentId
     * @param teamId l'ID de l'équipe
     * @param studentId l'ID de l'étudiant
     */
    @Query("DELETE FROM team_students WHERE team_id = :teamId AND student_id = :studentId")
    void deleteStudentFromTeam(int teamId, int studentId);
}