package com.example.teamwork.Database.Dao;

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
    void insertAll(List<Student> students);

    @Update
    void update(Student student);

    @Delete
    void delete(Student student);

    @Query("SELECT * FROM students")
    List<Student> getAllStudents();

    @Query("SELECT * FROM students WHERE id = :id")
    Student getStudentById(int id);
}