/****************************************
 Fichier : LoginActivity.java
 Auteur : Samy Larochelle
 Fonctionnalité : Fonctionalité 33.2, Connexion
 Date : 05/05/2025
 Vérification :
 Date           Nom                 Approuvé
 =========================================================
 Historique de modifications :
 Date           Nom                 Description
 05/05/2025     Samy Larochelle     Création
 05/07/2025     Samy Larochelle     Recréation de la vue
 05/16/2025     Samy Larochelle     Apporte vers ProjetActivity si déja login
 05/18/2025     Samy Larochelle     Déplace la logique au dessus dans MainActivity
 =========================================================
 ****************************************/

package com.example.teamwork.Activity.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teamwork.API.ApiClient;
import com.example.teamwork.API.ApiInterface;
import com.example.teamwork.API.Repository.UserRepository;
import com.example.teamwork.Activity.MainActivity;
import com.example.teamwork.Activity.Project.ProjectActivity;
import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Dao.UserDao;
import com.example.teamwork.Database.Tables.User;
import com.example.teamwork.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.teamwork.Activity.Auth.Authentication;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * Les champs pour la connexion
     */
    EditText editTextCourriel, editTextPassword;
    /**
     * Le layout de l'activité
     */
    LinearLayout layout;
    /**
     * Le pattern regex pour l'adresse mail
     */
    Pattern mailPattern = Pattern.compile("^[0-9]{9}@cegepsherbrooke\\.qc\\.ca$");

    /**
     * Le constructeur qui initialise les variables de base et charge le UI.
     *
     * @param savedInstanceState Si l'activité est recréée après une fermeture,
     * ce Bundle contient les données précédemment enregistrées.
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.buttonRegister).setOnClickListener(this);
        findViewById(R.id.buttonLogin).setOnClickListener(this);
        layout = findViewById(R.id.layout);
    }

    /**
     * Gère les clics sur les différents boutons de l'interface.
     *
     * @param v La vue qui a été cliquée
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonRegister) {
            Intent intent = new Intent(this, RegisterActivity.class);
            registerLauncher.launch(intent);
        }
        if (v.getId() == R.id.buttonLogin) {
            editTextCourriel = findViewById(R.id.editTextCourriel);
            editTextPassword = findViewById(R.id.editTextPassword);
            String courriel = editTextCourriel.getText().toString();
            String password = editTextPassword.getText().toString();

            Matcher m = mailPattern.matcher(courriel);
            boolean bFormatCorrect = m.matches();

            if (courriel.isEmpty()) {
                editTextCourriel.setError("Veuillez entrer votre courriel.");
                editTextCourriel.requestFocus();
            } else if (!bFormatCorrect) {
                editTextCourriel.setError("Le courriel doit être dans le format suivant : 000000000@cegepsherbrooke.qc.ca");
                editTextCourriel.requestFocus();
            }
            else if (password.isEmpty()) {
                editTextPassword.setError("Veuillez entrer votre mot de passe");
                editTextPassword.requestFocus();
            }
            else {
                ApiInterface api = ApiClient.getClient("").create(ApiInterface.class);
                UserRepository repository = new UserRepository(this);
                JsonObject json = new JsonObject();
                json.addProperty("email", courriel);
                json.addProperty("password", password);
                repository.login(api, json, this, layout);
            }
        }
    }
    /**
     * Lanceur d'activité pour l'inscription
     * Affiche un message comme quoi l'email a bien été envoyé
     */
    private final ActivityResultLauncher<Intent> registerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Snackbar.make(layout, "Courriel envoyé!", Snackbar.LENGTH_SHORT).show();
                }
            });
}