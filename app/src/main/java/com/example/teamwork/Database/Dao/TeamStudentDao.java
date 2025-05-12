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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(TeamStudent teamStudent);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(TeamStudent... teamStudents);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTeamStudent(List<TeamStudent> teamStudent);

    @Delete
    void delete(TeamStudent teamStudent);

    @Query("SELECT * FROM team_students")
    LiveData<List<TeamStudent>> getTeamStudent();

    @Query("SELECT s.* FROM students s " +
            "INNER JOIN team_students ts ON s.id = ts.student_id " +
            "WHERE ts.team_id = :teamId")
    LiveData<List<Student>> getStudentsForTeam(int teamId);

    @Query("SELECT t.* FROM teams t " +
            "INNER JOIN team_students ts ON t.id = ts.team_id " +
            "WHERE ts.student_id = :studentId")
    LiveData<List<Team>> getTeamsForStudent(int studentId);

    @Query("DELETE FROM team_students WHERE team_id = :teamId")
    void deleteAllStudentsFromTeam(int teamId);

    @Query("SELECT COUNT(*) FROM team_students WHERE team_id = :teamId")
    LiveData<Integer> getStudentCountForTeam(int teamId);

    @Query("SELECT COUNT(*) FROM team_students WHERE team_id = :teamId AND student_id != :StudentId")
    LiveData<Integer> getStudentCountForTeamExcluding(int teamId, int StudentId);

    @Query("SELECT comment FROM team_students WHERE team_id = :teamId AND student_id = :studentId")
    LiveData<String> getCommentFromStudentTeam(int studentId, int teamId);

    @Query("SELECT * FROM team_students WHERE team_id = :teamId AND student_id = :studentId")
    LiveData<TeamStudent> getStudentInTeam(int teamId, int studentId);

    @Query("DELETE FROM team_students WHERE student_id = :studentId AND team_id IN (SELECT id FROM teams WHERE project_id = :projectId)")
    void deleteStudentFromProject(int studentId, int projectId);


    @Query("UPDATE team_students SET comment = :comment WHERE student_id = :studentId AND team_id = :teamId")
    void setCommentWhereStudentTeam(@Nullable String comment, int studentId, int teamId);
}