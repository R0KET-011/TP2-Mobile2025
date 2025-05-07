package com.example.teamwork.Database.Tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(
        tableName = "team_students",
        primaryKeys = {"team_id", "student_id"},
        foreignKeys = {
                @ForeignKey(
                        entity = Team.class,
                        parentColumns = "id",
                        childColumns = "team_id",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Student.class,
                        parentColumns = "id",
                        childColumns = "student_id",
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class TeamStudent {
    @ColumnInfo(name = "team_id")
    private int teamId;

    @ColumnInfo(name = "student_id")
    private int studentId;

    public TeamStudent(int teamId, int studentId) {
        this.teamId = teamId;
        this.studentId = studentId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}