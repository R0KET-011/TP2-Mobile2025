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

import com.example.teamwork.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText input;
    LinearLayout layout;
    Pattern p = Pattern.compile("^[0-9]{9}$");

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

            Matcher m = p.matcher(inputText);
            boolean bFormatCorrect = m.matches();

            if (inputText.isEmpty()) {
                input.setError("Veuillez entrer votre numéro de dossier.");
                input.requestFocus();
            } else if (!bFormatCorrect) {
                input.setError("Le numéro de dossier doit contenir 9 chiffres");
                input.requestFocus();
            } else {
                //Todo : call api to send email
                finish();
            }
        }
    }
}