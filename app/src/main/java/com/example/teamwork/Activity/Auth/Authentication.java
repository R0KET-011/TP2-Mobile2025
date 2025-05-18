package com.example.teamwork.Activity.Auth;

public class Authentication {

    private static int id, code;
    private static boolean isStudent;
    private static String email, name;

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

    public static int getCode() {
        return code;
    }

    public static void setCode(int code) {
        Authentication.code = code;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        Authentication.email = email;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Authentication.name = name;
    }
}