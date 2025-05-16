package com.example.teamwork.Database.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.teamwork.Database.Tables.Team;

import java.util.List;

@Dao
public interface TeamDao {

    /**
     * Insérer une nouvelle équipe par rapport à une instance donnée
     * @param team une instance d'équipe
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Team team);

    /**
     * Insérer plusieurs nouvelles équipes par rapport à des instances données.
     * @param teams des instances d'équipe
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Team... teams);

    /**
     * Insérer une liste d'équipes.
     * @param teams une liste d'instances d'équipe
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTeams(List<Team> teams);

    /**
     * Met à jour une équipe existante dans la base de données.
     * @param team l'équipe à mettre à jour
     */
    @Update
    void update(Team team);

    /**
     * Supprime une équipe de la base de données.
     * @param team l'équipe à supprimer
     */
    @Delete
    void delete(Team team);

    /**
     * Récupère toutes les équipes.
     * @return une liste observable de toutes les équipes
     */
    @Query("SELECT * FROM teams")
    LiveData<List<Team>> getAllTeams();

    /**
     * Récupère une équipe par son identifiant sous forme observable.
     * @param id l'identifiant de l'équipe
     * @return une instance observable de l'équipe correspondante
     */
    @Query("SELECT * FROM teams WHERE id = :id")
    LiveData<Team> getTeamById(int id);

    /**
     * Récupère une équipe par son identifiant
     * @param id l'identifiant de l'équipe
     * @return l'équipe correspondante
     */
    @Query("SELECT * FROM teams WHERE id = :id")
    Team getTeamByIdClass(int id);

    /**
     * Récupère les équipes associées à un projet donné.
     * @param projectId l'identifiant du projet
     * @return une liste observable des équipes associées au projet
     */
    @Query("SELECT * FROM teams WHERE project_id = :projectId")
    LiveData<List<Team>> getTeamsByProjectId(int projectId);

    /**
     * Vérifie si un nom d'équipe est déjà utilisé dans un projet donné.
     * @param name le nom de l'équipe
     * @param projectId l'identifiant du projet
     * @return le nombre d'équipes avec ce nom dans le projet (0 si disponible, >0 si pris)
     */
    @Query("SELECT COUNT(*) FROM teams WHERE LOWER(name) = LOWER(:name) AND project_id = :projectId")
    int isNameTaken(String name, int projectId);

    /**
     * Vérifie si un nom d'équipe est déjà utilisé dans un projet donné en excluant une équipe spécifique.
     * @param name le nom de l'équipe
     * @param projectId l'identifiant du projet
     * @param teamId l'identifiant de l'équipe à exclure de la vérification
     * @return le nombre d'équipes avec ce nom dans le projet (hors l'équipe exclue)
     */
    @Query("SELECT COUNT(*) FROM teams WHERE LOWER(name) = LOWER(:name) AND project_id = :projectId AND id != :teamId")
    int isNameTakenEdit(String name, int projectId, int teamId);

    @Query("SELECT COUNT(*) FROM teams")
    int getTeamTableSize();
}