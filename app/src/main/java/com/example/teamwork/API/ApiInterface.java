package com.example.teamwork.API;

import java.util.List;
import com.example.teamwork.Database.Tables.Project;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    // "api" est déjà inclu dans l'url dans le client de retrofit, voir ApiService.java
    @GET("group/{id}/projects")
    Call<List<Project>> getProjects(@Path("id") int userId);

}