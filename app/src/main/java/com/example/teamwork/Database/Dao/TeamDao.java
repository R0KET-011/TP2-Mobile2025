package com.example.teamwork.Database.Dao;

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
    void insertAll(List<Team> teams);

    @Update
    void update(Team team);

    @Delete
    void delete(Team team);

    @Query("SELECT * FROM teams")
    List<Team> getAllTeams();

    @Query("SELECT * FROM teams WHERE id = :id")
    Team getTeamById(int id);

    @Query("SELECT * FROM teams WHERE project_id = :projectId")
    List<Team> getTeamsByProjectId(int projectId);
}