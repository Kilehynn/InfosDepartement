package com.project.infosdepartment.model.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//Creation of an entity that stocks all the infos about a departement
@Entity(tableName = "departments")
public class DepartmentEntity {


    //The code of each department
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "code")
    private final String departmentCode;

    //The name of each department
    @NonNull
    @ColumnInfo(name = "name")
    private final String departmentName;

    @ColumnInfo(name = "inhabitants")
    private final Integer inhabitants;

    @ColumnInfo(name = "number_towns")
    private final Integer nbTowns;

    public DepartmentEntity(@NonNull String departmentCode, @NonNull String departmentName, Integer inhabitants, Integer nbTowns) {
        if (departmentCode.equals("") || departmentName.equals("") || inhabitants == 0 || nbTowns == 0) {
            throw new IllegalArgumentException("Illegal argument when creating a DepartmentEntity");
        }
        this.departmentCode = departmentCode;
        this.departmentName = departmentName;
        this.inhabitants = inhabitants;
        this.nbTowns = nbTowns;
    }


    @NonNull
    public String getDepartmentCode() {
        return departmentCode;
    }

    @NonNull
    public String getDepartmentName() {
        return departmentName;
    }

    public Integer getInhabitants() {
        return inhabitants;
    }

    public Integer getNbTowns() {
        return nbTowns;
    }

}
