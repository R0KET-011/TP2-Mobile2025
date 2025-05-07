package com.example.teamwork.Database.Tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "projects")
public class Project {
    @PrimaryKey
    private int id;
    @ColumnInfo(name = "min_per_team")
    int minPerTeam;
    @ColumnInfo(name = "max_per_team")
    int maxPerTeam;
    String name;
    String description;
    @ColumnInfo(name = "is_joinable")
    boolean isJoinable;
    @ColumnInfo(name = "is_creatable")
    boolean isCreatable;
    @ColumnInfo(name = "is_common_classes")
    boolean isCommonClasses;

    public Project(int id, int minPerTeam, int maxPerTeam, String name, String description, boolean isJoinable, boolean isCreatable, boolean isCommonClasses) {
        this.id = id;
        this.minPerTeam = minPerTeam;
        this.maxPerTeam = maxPerTeam;
        this.name = name;
        this.description = description;
        this.isJoinable = isJoinable;
        this.isCreatable = isCreatable;
        this.isCommonClasses = isCommonClasses;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMinPerTeam() {
        return minPerTeam;
    }

    public void setMinPerTeam(int minPerTeam) {
        this.minPerTeam = minPerTeam;
    }

    public int getMaxPerTeam() {
        return maxPerTeam;
    }

    public void setMaxPerTeam(int maxPerTeam) {
        this.maxPerTeam = maxPerTeam;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isJoinable() {
        return isJoinable;
    }

    public void setJoinable(boolean joinable) {
        isJoinable = joinable;
    }

    public boolean isCreatable() {
        return isCreatable;
    }

    public void setCreatable(boolean creatable) {
        isCreatable = creatable;
    }

    public boolean isCommonClasses() {
        return isCommonClasses;
    }

    public void setCommonClasses(boolean commonClasses) {
        isCommonClasses = commonClasses;
    }
}
