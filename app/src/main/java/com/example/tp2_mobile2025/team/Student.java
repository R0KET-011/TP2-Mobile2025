package com.example.tp2_mobile2025.team;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable {
    private String name;

    public Student(String name) {
        this.name = name;
    }

    protected Student(Parcel in) {
        name = in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
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
}

