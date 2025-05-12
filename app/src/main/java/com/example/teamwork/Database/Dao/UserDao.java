package com.example.teamwork.Database.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.teamwork.Database.Tables.User;

import java.util.List;

@Dao
public interface UserDao {

    /**
     * @param user user to add to the db
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    /**
     * @param users users to add to the db
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(User... users);

    /**
     * @param user user to update in the db
     */
    @Update
    void update(User user);

    /**
     * @param user user to delete in the db
     */
    @Delete
    void delete(User user);

    /**
     * @return users in the db
     */
    @Query("SELECT * FROM users")
    LiveData<List<User>> getAllUsers();

    /**
     * @return users in the db matching the given id
     */
    @Query("SELECT * FROM users WHERE id = :id")
    LiveData<User> getUserById(int id);
}
