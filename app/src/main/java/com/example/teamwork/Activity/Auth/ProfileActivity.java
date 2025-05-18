package com.example.teamwork.Activity.Auth;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.teamwork.Activity.Team.TeamAdapter;
import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Tables.Student;
import com.example.teamwork.R;

import java.io.File;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{
    /**
     * Les View pour le UI
     */
    private TextView name, code, email;

    /**
     * La View pour l'image
     */
    private ImageView imageView;

    /**
     * L'ID unique de la photo
     */
    private Uri photoURI;

    /**
     * Le chemin vers la photo
     */
    private File imageFile;

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
        setContentView(R.layout.activity_profile);

        findViews();
        setInfos();
        loadImage();
    }

    /**
     * Permet de définir les vues en les associant aux variables via les IDs du fichier XML.
     * Ajoute également les listeners.
     */
    private void findViews(){
        name = findViewById(R.id.name);
        code = findViewById(R.id.code);
        email = findViewById(R.id.email);
        imageView = findViewById(R.id.profile);

        findViewById(R.id.take_picture).setOnClickListener(this);
        findViewById(R.id.password_reset).setOnClickListener(this);
        findViewById(R.id.disconnect).setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
    }

    /**
     * Affiche les informations du profil.
     */
    private void setInfos(){
        name.setText(Authentication.getName());
        code.setText(String.valueOf(Authentication.getCode()));
        email.setText(Authentication.getEmail());
    }

    /**
     * Essaye de charger l'image de profil (si elle existe).
     */
    private void loadImage() {
        imageFile = imageFile();
        if (imageFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            imageView.setImageBitmap(bitmap);
        }
    }

    /**
     * Crée ou retourne le fichier de l'image avec un nom basé sur l'ID de l'utilisateur.
     *
     * @return File vers l'image du profil
     */
    private File imageFile() {
        String fileName = Authentication.getId() + ".jpg";
        return new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName);
    }

    /**
     * Vérifie la permission de la caméra et déclenche la prise de photo si elle est accordée.
     */
    private void takePicture() {
        if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            launchCameraIntent();
        } else {
            requestPermissions(new String[]{android.Manifest.permission.CAMERA}, 100);
        }
    }

    /**
     * Callback après la demande de permission. Lance la caméra si accordée.
     *
     * @param requestCode Le code de la requête
     * @param permissions Les permissions demandées
     * @param grantResults Les résultats (accordé ou refusé)
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchCameraIntent();
            } else {
                Toast.makeText(this, "Camera permission is required to take pictures", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Lance l'activité de caméra avec les informations nécessaires
     */
    private void launchCameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageFile = imageFile();
        photoURI = FileProvider.getUriForFile(
                this,
                "com.example.teamwork.fileprovider",
                imageFile
        );
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        takePictureLauncher.launch(intent);
    }


    /**
     * Gère les clics sur les différents boutons de l'interface.
     *
     * @param v La vue qui a été cliquée
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.take_picture) {
            takePicture();
        } else if (v.getId() == R.id.back){
            finish();
        } else if (v.getId() == R.id.password_reset) {
            // ToDo Implementer le reste de password
        } else if (v.getId() == R.id.disconnect){
            // ToDo Implementer la deconnection
        }
    }

    /**
     * Lanceur d'activité pour la prise de photo.
     * Recharge l'image dans l'UI si la photo a bien été prise.
     */
    private final ActivityResultLauncher<Intent> takePictureLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    loadImage();
                }
            });

}