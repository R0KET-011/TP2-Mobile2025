///****************************************
// Fichier : GroupRepository.java
// Auteur : Kevin Larochelle
// Fonctionnalité :
// Manager entre Api et Database pour projets.
//
// Date : 05/12/2025
//
// Vérification :
// Date Nom Approuvé
//
// =========================================================
// Historique de modifications :
// Date Nom Description
//
// =========================================================
// ****************************************/
//
//package com.example.teamwork.API.Repository;
//
//import com.example.teamwork.API.ApiInterface;
//import com.example.teamwork.Database.AppDatabase;
//
//public class GroupRepository {
//
//    private GroupDao groupDao;
//
//    private GroupRepository(Context context) {
//        AppDatabase db = AppDatabase.getDatabase(context);
//        groupDao = db.groupDao();
//    }
//
//    public void fetchInsertGroups(ApiInterface api) {
//        Call<List<Group>> call = api.getGroups();
//        Log.v("Group API Call", "Call done.");
//
//        call.enqueue(new Callback(<List<Group>>() {
//            @Override
//            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    Log.v("Group Response Body", "Contains something");
//                    try {
//                        new Thread(() -> {
//                            groupDao.insertGroups(response.body());
//                            Log.v("Insertion", "Group Insertion successful");
//                        }).start();
//                    }
//                    catch(Exception e) {
//                        Log.e("ERROR ROM FETCH", "" +e);
//                    }
//                }
//                else {
//                    Log.v("Group Response Body", "Issues detected");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Group>> call, Throwable t) {
//                t.printStackTrace();
//                Log.v("Group Call Fail", "Failure", t);
//            }
//        }
//    }
//}
