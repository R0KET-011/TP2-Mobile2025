package com.example.tp2_mobile2025.team;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.tp2_mobile2025.R;

import java.util.ArrayList;
import java.util.List;

public class Team implements Parcelable {
    private String name, state, description;
    private List<Student> students;

    public Team(String name, String state, String description, List<Student> students){
        this.name = name;
        this.state = state;
        this.description = description;
        this.students = students;
    }

    protected Team(Parcel in) {
        name = in.readString();
        state = in.readString();
        description = in.readString();
        students = new ArrayList<>();
        in.readTypedList(students, Student.CREATOR);
    }

    public static final Creator<Team> CREATOR = new Creator<Team>() {
        @Override
        public Team createFromParcel(Parcel in) {
            return new Team(in);
        }

        @Override
        public Team[] newArray(int size) {
            return new Team[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(state);
        dest.writeString(description);
        dest.writeTypedList(students);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public int getStudentsCount(){
        return this.students.size();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int  getStateColor(Context context){
        switch (state) {
            case "Totalement conforme":
                return context.getColor(R.color.color_green);
            case "Partiellement conforme":
                return context.getColor(R.color.color_yellow);
            default:
                return context.getColor(R.color.color_red);
        }
    }
}
