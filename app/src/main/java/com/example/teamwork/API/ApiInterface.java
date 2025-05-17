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
import com.example.teamwork.Database.Tables.User;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {

    // "api" est déjà inclu dans l'url dans le client de retrofit, voir ApiService.java
    /** fin URL pour l'api pour importer les projets de la base de donnée web */
    @GET("projects")
    Call<List<Project>> getProjects();

    /** fin URL pour l'api pour importer les équipes de la base de donnée web */
    @GET("teams")
    Call<List<Team>> getTeams();

    /** fin URL pour l'api pour importer les étudiant de la base de donnée web */
    @GET("students")
    Call<List<Student>> getStudents();

    /** fin URL pour l'api pour importer les relations étudiant-équipe de la base de donnée web*/
    @GET("student_team")
    Call<List<TeamStudent>> getTeamStudent();

    /** fin URL pour l'api pour importer les cours de la base de donnée web */
    @GET("cours")
    Call<List<Course>> getCourses();

    /** fin URL pour l'api pour importer les groupes de la base de donnée web*/
    @GET("groups")
    Call<List<Group>> getGroups();

    /** fin URL pour l'api pour importer les relations groupe-projet de la base de donnée web */
    @GET("group_project")
    Call<List<GroupProject>> getGroupProject();

    /** fin URL pour l'api pour insérer un nouveau projet dans la base de donnée web
     * @param id est int, id du groupe touché
     * @param project est un objet Projet */
    @POST("group/{id_group}/newproject")
    Call<Void> createProject(@Path("id_group") int id, @Body Project project);

    /** fin URL pour l'api pour supprimer un projet de la base de donnée web
     * @param id est int, id du projet touché */
    @DELETE("project-destroy/{id_project}")
    Call<Void> deleteProject(@Path("id_project")int id);

    /** fin URL pour l'api pour modifier un projet dans la base de donnée web
     * @param project est objet Projet avec les modifications */
    @PUT("projects/update")
    Call<Void> updateProject(@Body Project project);

    /** fin URL pour l'api pour supprimer un équipe de la base de donnée web
     * @param id est int, id de l'équipe touché */
    @DELETE("team/{team_id}/delete")
    Call<Void> deleteTeam (@Path("team_id") int id);

    /** fin URL pour l'api pour modifier un équipe dans la base de donnée web
     * @param team est objet Team avec les modifications */
    @PUT("teams/update")
    Call<Void> updateTeam(@Body Team team);

    /** fin URL pour l'api pour insérer un nouveau projet dans la base de donnée web
     * @param team est objet Team*/
    @POST("teams/store")
    Call<Void> createTeam(@Body Team team);

    /** fin URL pour l'api pour insérer une relation étudiant-équipe dans la base de donnée web
     * @param team_id est int, id de l'équipe touché
     * @param json est un objet JSON, contenant le id de l'étudiant*/
    @POST("team/{team_id}/add")
    Call<Void> createTeamStudent(@Path("team_id") int team_id, @Body JsonObject json);

    /** fin URL pour l'api pur supprimer une relation étudiant-équipe de la base de donnée web
     * @param project_id est int, id du projet touché
     * @param user_id est int, id de l'utilisateur touché */
    @DELETE("project/{project_id}/student/{user_id}")
    Call<Void> deleteTeamStudent(@Path("project_id") int project_id, @Path("user_id") int user_id);

    @POST("register")
    Call<Void> registerUser(@Body JsonObject json);

    @POST("login")
    Call<User> login(@Body JsonObject json);
}