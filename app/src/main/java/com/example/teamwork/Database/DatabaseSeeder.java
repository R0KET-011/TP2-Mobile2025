package com.example.teamwork.Database;

import com.example.teamwork.Database.Tables.Project;
import com.example.teamwork.Database.Tables.Student;
import com.example.teamwork.Database.Tables.Team;
import com.example.teamwork.Database.Tables.TeamStudent;

public class DatabaseSeeder {
    public static void seed(AppDatabase appDatabase) {
        Project project1 = new Project(
                1,
                "Projet intégrateur - Web",
                "Ce projet permet d'appliquer les compétences acquises dans les cours 420-404-SH (Méthodologie du développement logiciel) 420-446-SH (Développement d'applications mobiles), ainsi que le cours Web actuel. Chaque volet du projet est consacré à l’un de ces cours, sur une période d’environ une semaine, afin de créer une solution intégrée complète.",
                2,
                3,
                true,
                true,
                true,
                4118,
                "Développement Web avancé"
        );

        Project project2 = new Project(
                2,
                "Conception d'un thermostat connecté",
                "Ce projet vous permettra de développer un thermostat connecté complet en CircuitPython. Vous allez acquérir des données de température, les traiter, les afficher, les stocker localement et les partager sur le cloud. Ce projet intègre plusieurs concepts importants en IoT : acquisition de données, traitement, stockage, affichage et communication.",
                2,
                4,
                true,
                true,
                false,
                4118,
                "Introduction à l'utilisation d'objets connectés"
        );
        appDatabase.projectDao().insertAll(project1, project2);

        Team team1 = new Team(1, "TeamWork", "Totalement conforme", "Ce logiciel aura pour objectif d'orchestrer la formation des groupe.", project1.getId());
        Team team2 = new Team(2, "CafeGamer", "Partiellement conforme", "Description", project1.getId());
        Team team3 = new Team(3, "Walmart", "Non conforme", "Description", project1.getId());
        appDatabase.teamDao().insertAll(team1, team2, team3);

        Student student1 = new Student(1, "Antoine", "Blouin", "Essayer d'aider, marche comme ça peut");
        Student student2 = new Student(2, "Samy", "Larochelle", "Bonne compréhension du projet");
        Student student3 = new Student(3, "Kevin", "Larochelle", "Plonger dans son API");
        Student student4 = new Student(4, "Emeric", "Leclerc", "Dédié à la tâche");
        appDatabase.studentDao().insertAll(student1, student2, student3, student4);

        TeamStudent teamStudent2 = new TeamStudent(team1.getId(), student2.getId(), null);
        TeamStudent teamStudent3 = new TeamStudent(team1.getId(), student3.getId(), null);
        TeamStudent teamStudent4 = new TeamStudent(team1.getId(), student4.getId(), null);
        appDatabase.teamStudentDao().insertAll(teamStudent2, teamStudent3, teamStudent4);
    }
}
