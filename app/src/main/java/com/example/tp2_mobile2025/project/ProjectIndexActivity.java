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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tp2_mobile2025.R;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ProjectIndexActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_project_index);


//        ProjectController projectController = new ProjectController();
//        try {
//            ArrayList<Project> projectList = projectController.index();
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }


        StringBuilder response = new StringBuilder();

        new Thread(()-> {
            try {
                URL url = new URL ("http://10.0.2.2:8000/api/group/1/projects");
                String tokenA = "9|iv0z0veAUU4NFNjjcbmk0LLDS6DXDOsMdaGW81Te802afd4c";

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
                // runOnUiThread(() -> test_name.setText("Response Serveur: " + response.toString
                // ()));
            } catch (Exception e) {
                e.printStackTrace();
                // runOnUiThread(() -> test_name.setText("Erreur: " + e.getMessage()));
            }
        }).start();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}