package com.example.tp2_mobile2025.project;

import com.example.tp2_mobile2025.Comment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProjectController {

    /** Retourne une liste d'objet Projet pour l'affichage de la liste des projets.
     * @return ArrayList of Project objects */
    public ArrayList<Project> index() throws JSONException {
        // ArrayList pour retour de liste de projets
        ArrayList<Project> projects = new ArrayList<Project>();

        // JSONArray qui reçois la retour API de la liste des projets en json.
        JSONArray projectArray = new JSONArray();

        // API CALL TO LOCAL
        new Thread(() -> {
            try {

            }
            catch(Exception e) {

            }
        });

        // Convertit le JSON en objet Projets et les rajoutent dans l'Arraylist.
        for (int i = 0 ; i < projectArray.length() ; i++) {
            JSONObject project = projectArray.getJSONObject(i);
            Project newProject = new Project(project.getInt("id"), project.getString(
                    "name"), project.getString("description"), project.getInt("min_per_team"), project.getInt("max_per_team"), project.getBoolean("joinable"), project.getBoolean("creatable"), project.getBoolean("common_classes"));

            projects.add(newProject);
        }

        return projects;
    }

    /**
     * Show the form for creating a new resource.
     */
    public void create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     */
    public void store(Comment comment)
    {
        //
    }

    /**
     * Display the specified resource.
     */
    public void show(Comment comment)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     */
    public void edit(Comment comment)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     */
    public void update(Comment comment)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     */
    public void destroy(int id)
    {
        //
    }
}
