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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Team team);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Team... teams);

    @Update
    void update(Team team);

    @Delete
    void delete(Team team);

    @Query("SELECT * FROM teams")
    LiveData<List<Team>> getAllTeams();

    @Query("SELECT * FROM teams WHERE id = :id")
    LiveData<Team> getTeamById(int id);

    @Query("SELECT * FROM teams WHERE project_id = :projectId")
    LiveData<List<Team>> getTeamsByProjectId(int projectId);

    @Query("SELECT COUNT(*) FROM teams WHERE LOWER(name) = LOWER(:name) AND project_id = :projectId")
    int isNameTaken(String name, int projectId);

    @Query("SELECT COUNT(*) FROM teams WHERE LOWER(name) = LOWER(:name) AND project_id = :projectId AND id != :teamId")
    int isNameTakenEdit(String name, int projectId, int teamId);
}