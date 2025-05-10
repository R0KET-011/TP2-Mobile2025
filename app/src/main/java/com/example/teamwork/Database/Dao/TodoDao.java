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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Todo todo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Todo... todos);

    @Update
    void update(Todo todo);

    @Delete
    void delete(Todo todo);

    @Query("SELECT * FROM todos")
    LiveData<List<Todo>> getAllTodos();

    @Query("SELECT * FROM todos WHERE id = :id")
    LiveData<Todo> getTodoById(int id);

    @Query("SELECT * FROM todos WHERE project_id = :projectId")
    LiveData<List<Todo>> getTodosByProjectId(int projectId);
}
