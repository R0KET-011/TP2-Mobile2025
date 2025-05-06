package com.example.tp2_mobile2025.students;

import androidx.annotation.Nullable;

/**
 * La class student peux contenir une instance d'un student. sera utiliser pour
 * lister les différents élèves d'une équipe ou pour n'importe quelle autre tache
 * fait avec des élèves.
 * @author Émeric Leclerc
 * @version 1
 */
public class Student {
    /**
     * Variable Id du students
     */
    public int id;
    /**
     *Variable String qui contien le first_name et last_name concatoné d'un élève.
     */
    public String name;
    /**
     *Variable String qui contien un commentaire laisser sur un élève. peux etre null.
     */
    @Nullable
    public String comment;

    /**
     * Variable int qui réfère à l'id d'une équipe dans la bd.
     */
    public int team;

}
