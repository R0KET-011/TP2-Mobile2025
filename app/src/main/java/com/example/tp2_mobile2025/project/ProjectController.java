/****************************************
 Fichier : ProjectController.java
 Auteur : Kevin Larochelle
 Fonctionnalité :
 31.1 Ajouter un projet
 31.2 Lister / consulter des projets
 31.4 Modifier un projet
 31.5 Supression d'un projet

 Date : 05/05/2025

 Vérification :
 Date Nom Approuvé

 =========================================================
 Historique de modifications :
 Date Nom Description

 =========================================================
 ****************************************/

package com.example.tp2_mobile2025.project;

import android.app.Application;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ProjectController {

    /** 31.2 Lister / consulter des projets
     * Retourne une liste d'objet Projet pour l'affichage de la liste des projets.
     * @return ArrayList of Project objects */
    public ArrayList<Project> index() throws JSONException {
        // ArrayList pour retour de liste de projets
        ArrayList<Project> projects = new ArrayList<Project>();

        // JSONArray qui reçois la retour API de la liste des projets en json.
        JSONArray projectArray = new JSONArray();

        // API CALL TO LOCAL
        new Thread(() -> {
            try {
                URL url = new URL("localhost:8000/api/group/1/projects");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null){
                    response.append(line);
                }
                reader.close();
                connection.disconnect();

            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }).start();

        // Convertit le JSON en objet Projets et les rajoutent dans l'Arraylist.
        for (int i = 0 ; i < projectArray.length() ; i++) {
            JSONObject project = projectArray.getJSONObject(i);
            Project newProject = new Project(project.getInt("id"), project.getString(
                    "name"), project.getString("description"), project.getInt("min_per_team"), project.getInt("max_per_team"), project.getBoolean("joinable"), project.getBoolean("creatable"), project.getBoolean("common_classes"),
                    project.getInt("group"), project.getString("course"));

            projects.add(newProject);
        }

        return projects;
    }

    /** Ammène vers le formulaire pour la création d'un projet */
    public void create()
    {
        //
    }

    /** 31.1 Ajouter un projet
     * Envoie les données à l'API pour ajouter un nouveau projets basé sur les champs remplis.
     * @param formdata qui est un bundle d'ExtraData de l'intent */
    public void store(Bundle formdata)
    {
        //
    }

    /** Affiche les informations sur un projet.
     * @param id est int*/
    public void show(int id)
    {
        //
    }

    /** Affiche le formulaire pour modifier un projet
     * @param id est int*/
    public void edit(int id)
    {
        //
    }

    /** Met à jour les informations sur un projet
     * @param formdata qui estun bundle d'Extradata de l'intent. */
    public void update(Bundle formdata)
    {
        //
    }

    /** Supprime un projet
     * @param id est int */
    public void destroy(int id)
    {
        //
    }
}
