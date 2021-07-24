package com.project.infosdepartment.model.database.entity;

import android.util.Log;

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
    @ColumnInfo(name = "areDataFetched")
    private Integer areDataFetched = 0;
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer id;

    public DepartmentsListEntity(@NonNull String departmentCode, @NonNull String departmentName) {
        this.departmentCode = departmentCode;
        this.departmentName = departmentName;
        Log.i("[DEBUG][DepartmentsListEntity]", "Ctr : department number " + departmentCode + " , " + departmentName);
    }

    @NonNull
    public String getDepartmentCode() {
        return departmentCode;
    }

    @NonNull
    public String getDepartmentName() {
        return departmentName;
    }

    @NonNull
    public Integer getAreDataFetched() {
        return areDataFetched;
    }

    public void setAreDataFetched(@NonNull Integer areDataFetched) {
        this.areDataFetched = areDataFetched;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "(" + departmentCode + ") - " + departmentName;
    }
}
