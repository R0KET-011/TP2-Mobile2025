package com.example.teamwork.Activity.Project;

import com.example.teamwork.R;
import android.os.Bundle;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import com.example.teamwork.Database.Tables.Project;

public class ProjectActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    TextView testName;
    String domain = "http://10.0.2.2:8000/api";
    String tokenA = "12|PSQGLDkL7Th23vE8BR1AJL1RBYNG4rAD6BHqltot7e06c219";
    static StringBuilder response = new StringBuilder();

    ArrayList<Project> projectArrayList = new ArrayList<Project>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_project);


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
                //runOnUiThread(() -> testName.setText("Response Serveur: " + response.toString()));

            } catch (Exception e) {
                e.printStackTrace();
                //runOnUiThread(() -> testName.setText("Erreur: " + e.getMessage()));
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        String json = response.toString();
        Type receivedType = new TypeToken<ArrayList<Project>>(){}.getType();
        projectArrayList = gson.fromJson(json, receivedType);

//        JSONArray jsonConvert = null;
//        try {
//            jsonConvert = new JSONArray(response.toString());
//
//            for (int i = 0; i < jsonConvert.length(); i++) {
//                JSONObject obj = jsonConvert.getJSONObject(i);
//                Project project = new Project(obj.getInt("id"), obj.getString("name"),
//                        obj.getString("description"), obj.getInt("min_per_team"), obj.getInt(
//                                "max_per_team"), obj.getBoolean("joinable"), obj.getBoolean(
//                                        "creatable"), obj.getBoolean("common_classes"),
//                        obj.getInt("group"), obj.getString("course"));
//                projectArrayList.add(project);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        StringBuilder test = new StringBuilder();
//        if (projectArrayList.size() != 0) {
//            for (int i = 0 ; i < projectArrayList.size() ; i++) {
//                test.append(projectArrayList.get(i).toString()).append(" |||| ");
//            }
//        }
//        StringBuilder test = new StringBuilder();
//        for (int i = 0 ; i < projectGson.size() ; i++) {
//            test.append(projectGson.get(i).toString()).append("|||||");
//        }
//        testName.setText(test.toString());

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        ProjectAdapter projectAdapter = new ProjectAdapter(this, projectArrayList);
        recyclerView.setAdapter(projectAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}