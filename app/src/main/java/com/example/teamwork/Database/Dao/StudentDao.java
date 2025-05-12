package com.example.teamwork.Database.Dao;

import android.service.autofill.OnClickAction;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.teamwork.Database.Tables.Student;

import java.util.List;

@Dao
public interface StudentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Student student);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Student... students);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertStudents(List<Student> students);

    @Update
    void update(Student student);

    @Delete
    void delete(Student student);

    @Query("SELECT * FROM students")
    LiveData<List<Student>> getAllStudents();

    @Query("SELECT * FROM students WHERE id = :id")
    LiveData<Student> getStudentById(int id);

    @Query("SELECT * FROM students WHERE code = :code")
    Student getStudentByCode(int code);
}