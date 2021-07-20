package com.project.infosdepartment.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//Creation of an entity that stocks all the infos about a departement
@Entity(tableName = "departments")
public class DepartmentEntity {

    //PrimaryKey of our DB
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Integer id;

    //The code of each department
    @NonNull
    @ColumnInfo(name = "code")
    private final String departmentCode;

    //The name of each department
    @NonNull
    @ColumnInfo(name = "name")
    private final String departmentName;

    @ColumnInfo(name = "inhabitants")
    private Integer inhabitants;

    @ColumnInfo(name = "number_towns")
    private Integer nbTowns;

    public DepartmentEntity(@NonNull String departmentCode, @NonNull String departmentName, Integer inhabitants, Integer nbTowns) {
        this.departmentCode = departmentCode;
        this.departmentName = departmentName;
        this.inhabitants = inhabitants;
        this.nbTowns = nbTowns;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getDepartmentCode() {
        return departmentCode;
    }

    @NonNull
    public String getDepartmentName() {
        return departmentName;
    }

    public int getInhabitants() {
        return inhabitants;
    }

    public int getNbTowns() {
        return nbTowns;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

}
