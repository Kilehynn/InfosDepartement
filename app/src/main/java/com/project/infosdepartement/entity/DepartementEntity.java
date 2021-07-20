package com.project.infosdepartement.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//Creation of an entity that stocks all the infos about a departement
@Entity(tableName = "departements")
public class DepartementEntity {

    //PrimaryKey of our DB
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Integer id;

    //The code of each departement
    @NonNull
    @ColumnInfo(name = "code")
    private final String departementCode;

    //The name of each Departement
    @NonNull
    @ColumnInfo(name = "name")
    private final String departementName;

    @ColumnInfo(name = "inhabitants")
    private Integer inhabitants;

    @ColumnInfo(name = "number_towns")
    private Integer nbTowns;

    public DepartementEntity(@NonNull String departementCode, @NonNull String nomDepartement, Integer population, Integer nbTowns) {
        this.departementCode = departementCode;
        this.departementName = nomDepartement;
        this.inhabitants = population;
        this.nbTowns = nbTowns;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getDepartementCode() {
        return departementCode;
    }

    @NonNull
    public String getDepartementName() {
        return departementName;
    }

    public int getInhabitants() {
        return inhabitants;
    }

    public int getNbTowns() {
        return nbTowns;
    }
}
