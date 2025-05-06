package com.example.tp2_mobile2025.students;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;

/**
 * La class student peux contenir une instance d'un student. sera utiliser pour
 * lister les différents élèves d'une équipe ou pour n'importe quelle autre tache
 * fait avec des élèves.
 * @author Émeric Leclerc
 * @version 1
 */
public class Student implements Parcelable {
    /**
     * Variable Id du students
     */
    public int id;
    /**
     *Variable String qui contien le first_name et last_name concatoné d'un élève.
     */
    public String name;
    /**
     *Variable String qui contien un commentaire laisser sur un élève. peux etre null.
     */
    @Nullable
    public String comment;

    /**
     * Variable int qui réfère à l'id d'une équipe dans la bd.
     */
    public int team;

    protected Student(Parcel in) {
        id = in.readInt();
        name = in.readString();
        comment = in.readString();
        team = in.readInt();
    }

    public Student(String name) {

    }


    //create from db
    static Student createFromLocalDB() {
        return new Student("patate");
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(comment);
        dest.writeInt(team);
    };


    //setters et getters
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setComment(@Nullable String comment) {
        this.comment = comment;
    }

    @Nullable
    public String getComment() {
        return comment;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public int getTeam() {
        return team;
    }
}
