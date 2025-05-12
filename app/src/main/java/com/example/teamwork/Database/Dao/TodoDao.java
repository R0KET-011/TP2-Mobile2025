/****************************************
 Fichier : TodoDao.java
 Auteur : Samy Larochelle
 Fonctionnalité : Fonctionalités 36, Gestion des tâches
 Date : 05/05/2025
 Vérification :
 Date           Nom                 Approuvé
 =========================================================
 Historique de modifications :
 Date           Nom                 Description
 05/09/2025     Samy Larochelle     Création
 05/10/2025     Samy Larochelle     Correction
 =========================================================
 ****************************************/
package com.example.teamwork.Database.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.teamwork.Database.Tables.Todo;

import java.util.List;

@Dao
public interface TodoDao {

    /**
     * @param todo todo to add to the db
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Todo todo);

    /**
     * @param todos todos to add to the db
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Todo... todos);

    /**
     * @param todo todo to update in the db
     */
    @Update
    void update(Todo todo);

    /**
     * @param todo todo to delete in the db
     */
    @Delete
    void delete(Todo todo);

    /**
     * @return todos in the db
     */
    @Query("SELECT * FROM todos")
    LiveData<List<Todo>> getAllTodos();

    /**
     * @return todos in the db matching the given id
     */
    @Query("SELECT * FROM todos WHERE id = :id")
    LiveData<Todo> getTodoById(int id);

    /**
     * @return todos in the db matching the given project id
     */
    @Query("SELECT * FROM todos WHERE project_id = :projectId ORDER BY completed")
    LiveData<List<Todo>> getTodosByProjectId(int projectId);
}
