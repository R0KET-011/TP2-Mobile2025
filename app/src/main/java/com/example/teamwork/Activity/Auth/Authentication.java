package com.example.teamwork.Activity.Auth;

public class Authentication {

    private static int id;
    private static boolean isStudent;

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Authentication.id = id;
    }

    public static boolean isStudent() {
        return isStudent;
    }

    public static void setIsStudent(boolean isStudent) {
        Authentication.isStudent = isStudent;
    }
}