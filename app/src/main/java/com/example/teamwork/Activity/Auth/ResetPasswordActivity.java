/****************************************
 Fichier : ResetPassword.java
 Auteur : Samy Larochelle
 Fonctionnalité : Fonctionalité 33.1, Récupération de mot de passe
 Date : 05/05/2025
 Vérification :
 Date           Nom                 Approuvé
 =========================================================
 Historique de modifications :
 Date           Nom                 Description
 05/05/2025     Samy Larochelle     Création
 05/07/2025     Samy Larochelle     Recréation de la vue
 =========================================================
 ****************************************/
package com.example.teamwork.Activity.Auth;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamwork.R;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        findViewById(R.id.back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            finish();
        }
    }
}