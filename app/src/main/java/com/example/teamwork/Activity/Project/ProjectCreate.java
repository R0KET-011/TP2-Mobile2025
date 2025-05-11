/****************************************
 Fichier : ProjectCreate.java
 Auteur : Kevin Larochelle
 Fonctionnalité :
 Fait afficher un formulaire pour créer un projet.

 Date : 05/11/2025

 Vérification :
 Date Nom Approuvé

 =========================================================
 Historique de modifications :
 Date Nom Description

 =========================================================
 ****************************************/

package com.example.teamwork.Activity.Project;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teamwork.R;

public class ProjectCreate extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_project_create);

        findViewById(R.id.back).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            finish();
        }
        else if(v.getId() == R.id.create){
            // logic to add to database
        }
    }
}