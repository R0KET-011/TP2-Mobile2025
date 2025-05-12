package com.example.teamwork.Database.Tables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey
    private int id;
    private String email;
    private String token;
    private String lien_image;

    /**
     * @param id         the db id of the user
     * @param email      the email of the user
     * @param token      the api token of the user
     * @param lien_image the image file path of the user's profile picture
     */
    public User(int id, String email, String token, String lien_image) {
        this.id = id;
        this.email = email;
        this.token = token;
        this.lien_image = lien_image;
    }

    /**
     * @return the db id of the user
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the db id of the user
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email email of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the api token of the user
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the api token of the user
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return the image file path of the user's profile picture
     */
    public String getLien_image() {
        return lien_image;
    }

    /**
     * @param lien_image the image file path of the user's profile picture
     */
    public void setLien_image(String lien_image) {
        this.lien_image = lien_image;
    }
}
