/****************************************
 Fichier : ProjectIndexActivity.java
 Auteur : Kevin Larochelle
 Fonctionnalité :
 31.2 Lister / consulter des projets

 Date : 05/05/2025

 Vérification :
 Date Nom Approuvé

 =========================================================
 Historique de modifications :
 Date Nom Description

 =========================================================
 ****************************************/
package com.example.tp2_mobile2025.project;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp2_mobile2025.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ProjectIndexActivity extends AppCompatActivity {

    TextView testName;
    String domain = "http://10.0.2.2:8000/api";
    String tokenA = "11|fTsBgrMwGIkGTgZQMaygNoHdmr7R7G5q99xdqpl0378b7300";
    StringBuilder response = new StringBuilder();
    ArrayList<Project> projectArrayList = new ArrayList<Project>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_project_index);

        Thread thread = new Thread(()-> {
            try {
                URL url = new URL (domain + "/group/1/projects");

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                connection.setRequestProperty("Authorization", "Bearer " + tokenA);
                connection.setRequestProperty("Accept", "application/json");

                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                connection.disconnect();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Convert StringBuilder string into an ArrayList of Project objects
        Gson gson = new Gson();
        String json = response.toString();
        Type receivedType = new TypeToken<ArrayList<Project>>(){}.getType();
        projectArrayList = gson.fromJson(json, receivedType);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        ProjectAdapter projectAdapter = new ProjectAdapter(this, projectArrayList);
        recyclerView.setAdapter(projectAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}