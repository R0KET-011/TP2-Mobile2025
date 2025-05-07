package com.example.teamwork.Database.Dao;

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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(TeamStudent teamStudent);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(TeamStudent... teamStudents);

    @Delete
    void delete(TeamStudent teamStudent);

    @Query("SELECT s.* FROM students s " +
            "INNER JOIN team_students ts ON s.id = ts.student_id " +
            "WHERE ts.team_id = :teamId")
    List<Student> getStudentsForTeam(int teamId);

    @Query("SELECT t.* FROM teams t " +
            "INNER JOIN team_students ts ON t.id = ts.team_id " +
            "WHERE ts.student_id = :studentId")
    List<Team> getTeamsForStudent(int studentId);

    @Query("DELETE FROM team_students WHERE team_id = :teamId")
    void deleteAllStudentsFromTeam(int teamId);

    @Query("SELECT COUNT(*) FROM team_students WHERE team_id = :teamId")
    int getStudentCountForTeam(int teamId);
}