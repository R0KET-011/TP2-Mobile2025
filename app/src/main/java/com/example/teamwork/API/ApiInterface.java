/****************************************
 Fichier : ApiInterface.java
 Auteur : Kevin Larochelle
 Fonctionnalité :
 Pour les liens vers l'Api.

 Date : 05/09/2025

 Vérification :
 Date Nom Approuvé

 =========================================================
 Historique de modifications :
 Date Nom Description

 =========================================================
 ****************************************/

package com.example.teamwork.API;

import java.util.List;

import com.example.teamwork.Database.Tables.Course;
import com.example.teamwork.Database.Tables.Group;
import com.example.teamwork.Database.Tables.GroupProject;
import com.example.teamwork.Database.Tables.Project;
import com.example.teamwork.Database.Tables.Student;
import com.example.teamwork.Database.Tables.Team;
import com.example.teamwork.Database.Tables.TeamStudent;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    // "api" est déjà inclu dans l'url dans le client de retrofit, voir ApiService.java
    @GET("projects")
    Call<List<Project>> getProjects();

    @GET("teams")
    Call<List<Team>> getTeams();

    @GET("students")
    Call<List<Student>> getStudents();

    @GET("student_team")
    Call<List<TeamStudent>> getTeamStudent();

    @GET("cours")
    Call<List<Course>> getCourses();

    @GET("groups")
    Call<List<Group>> getGroups();

    @GET("group_project")
    Call<List<GroupProject>> getGroupProject();

    @POST("group/{id_group}/newproject")
    Call<Void> createProject(@Path("id_group") int id, @Body Project project);

    @DELETE("project-destroy/{id_project}")
    Call<Void> deleteProject(@Path("id_project")int id);

}