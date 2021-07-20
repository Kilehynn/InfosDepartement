package com.project.infosdepartment.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "departmentsList")
public class DepartmentsListEntity {

    //The code of each department
    @NonNull
    @ColumnInfo(name = "code")
    private final String departmentCode;
    //The name of each Department
    @NonNull
    @ColumnInfo(name = "name")
    private final String departmentName;
    //Boolean saying if the data of a departement has already been fetched
    @NonNull
    @ColumnInfo(name = "areDatasFetched")
    private final Boolean areDatasFetched;
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer id;

    public DepartmentsListEntity(@NonNull String departmentCode, @NonNull String departmentName) {
        this.departmentCode = departmentCode;
        this.departmentName = departmentName;
        areDatasFetched = false;
    }
}
