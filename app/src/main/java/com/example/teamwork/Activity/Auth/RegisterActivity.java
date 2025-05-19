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
    EditText input;
    LinearLayout layout;
    Pattern mailPattern = Pattern.compile("^[0-9]{9}@cegepsherbrooke\\.qc\\.ca$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.buttonSendMail).setOnClickListener(this);
        layout = findViewById(R.id.layout);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            finish();
        }
        if (v.getId() == R.id.buttonSendMail) {
            input = findViewById(R.id.editTextNDA);
            String inputText = input.getText().toString();

            Matcher m = mailPattern.matcher(inputText);
            boolean bFormatCorrect = m.matches();

            if (inputText.isEmpty()) {
                input.setError("Veuillez entrer votre courriel");
                input.requestFocus();
            } else if (!bFormatCorrect) {
                input.setError("Le courriel doit être dans le format suivant : 000000000@cegepsherbrooke.qc.ca");
                input.requestFocus();
            } else {
                ApiInterface api = ApiClient.getClient("").create(ApiInterface.class);
                UserRepository repository = new UserRepository(this);
                JsonObject json = new JsonObject();
                json.addProperty("email", inputText);
                LinearLayout layout = findViewById(R.id.layout);
                repository.sendMail(api, json, layout);
            }
        }
    }
}