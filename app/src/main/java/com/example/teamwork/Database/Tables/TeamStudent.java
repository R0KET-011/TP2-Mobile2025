package com.example.teamwork.Database.Tables;

import androidx.annotation.Nullable;
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

    @ColumnInfo(name = "comment")
    @Nullable
    private String comment;

    public TeamStudent(int teamId, int studentId, @Nullable String comment) {
        this.teamId = teamId;
        this.studentId = studentId;
        this.comment = comment;
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

    @Nullable
    public String getComment() {return comment;}

    public void setComment(@Nullable String comment) {this.comment = comment;}
}