/****************************************
 Fichier : RegisterActivity.java
 Auteur : Samy Larochelle
 Fonctionnalité : Fonctionalités 33.3, Inscription
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
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamwork.API.ApiClient;
import com.example.teamwork.API.ApiInterface;
import com.example.teamwork.API.Repository.UserRepository;
import com.example.teamwork.R;
import com.google.gson.JsonObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * Le champs pour l'envoi de mail
     */
    EditText editTextCourriel;
    /**
     * Le pattern regex pour l'adresse mail
     */
    Pattern mailPattern = Pattern.compile("^[0-9]{9}@cegepsherbrooke\\.qc\\.ca$");
    /**
     * Le layout de l'activité
     */
    LinearLayout layout;

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
        setContentView(R.layout.activity_register);

        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.buttonSendMail).setOnClickListener(this);
        layout = findViewById(R.id.layout);
    }

    /**
     * Gère les clics sur les différents boutons de l'interface.
     *
     * @param v La vue qui a été cliquée
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            finish();
        }
        if (v.getId() == R.id.buttonSendMail) {
            editTextCourriel = findViewById(R.id.editTextNDA);
            String courriel = editTextCourriel.getText().toString();

            Matcher m = mailPattern.matcher(courriel);
            boolean bFormatCorrect = m.matches();

            if (courriel.isEmpty()) {
                editTextCourriel.setError("Veuillez entrer votre courriel");
                editTextCourriel.requestFocus();
            } else if (!bFormatCorrect) {
                editTextCourriel.setError("Le courriel doit être dans le format suivant : 000000000@cegepsherbrooke.qc.ca");
                editTextCourriel.requestFocus();
            } else {
                ApiInterface api = ApiClient.getClient("").create(ApiInterface.class);
                UserRepository repository = new UserRepository(this);
                JsonObject json = new JsonObject();
                json.addProperty("email", courriel);
                repository.sendMail(api, json,this, layout);
            }
        }
    }
}