package com.example.teamwork.Activity.Auth;

public class Authentication {

    private static int id, code;
    private static boolean isStudent;
    private static String email, name, token;

    /**
     *
     * @return the db id of the current user
     */
    public static int getId() {
        return id;
    }
    /**
     *
     * @return  the api token of the current user
     */
    public static String getToken() {
        return token;
    }

    /**
     * @param token api token of the current user
     */
    public static void setToken(String token) {
        Authentication.token = token;
    }

    /**
     *
     * @param id of the current user
     */
    public static void setId(int id) {
        Authentication.id = id;
    }

    /**
     *
     * @return if the current user is a student
     */
    public static boolean isStudent() {
        return isStudent;
    }

    /**
     *
     * @param isStudent if the user is a student
     */
    public static void setIsStudent(boolean isStudent) {
        Authentication.isStudent = isStudent;
    }

    /**
     *
     * @return the code of the current user
     */
    public static int getCode() {
        return code;
    }

    /**
     *
     * @param code of the current user
     */
    public static void setCode(int code) {
        Authentication.code = code;
    }

    /**
     *
     * @return the email of the current user
     */
    public static String getEmail() {
        return email;
    }

    /**
     *
     * @param email of the current user
     */
    public static void setEmail(String email) {
        Authentication.email = email;
    }

    /**
     *
     * @return the full name of the current user
     */
    public static String getName() {
        return name;
    }

    /**
     *
     * @param name full name of the current user
     */
    public static void setName(String name) {
        Authentication.name = name;
    }
}