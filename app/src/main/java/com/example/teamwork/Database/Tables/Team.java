package com.example.teamwork.Database.Tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "teams",
        foreignKeys = @ForeignKey(
                entity = Project.class,
                parentColumns = "id",
                childColumns = "project_id",
                onDelete = ForeignKey.CASCADE
        )
)
public class Team {
    @PrimaryKey
    private int id;
    private String name;
    private String state;
    private String description;

    @ColumnInfo(name = "project_id")
    private int projectId;

    public Team(int id, String name, String state, String description, int projectId) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.description = description;
        this.projectId = projectId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}
