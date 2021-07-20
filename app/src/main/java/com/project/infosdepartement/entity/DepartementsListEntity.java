package com.project.infosdepartement.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "departementsList")
public class DepartementsListEntity {

    //The code of each departement
    @NonNull
    @ColumnInfo(name = "code")
    private final String departementCode;
    //The name of each Departement
    @NonNull
    @ColumnInfo(name = "name")
    private final String departementName;
    //Boolean saying if the data of a departement has already been fetched
    @NonNull
    @ColumnInfo(name = "areDatasFetched")
    private final boolean areDatasFetched;
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer id;

    public DepartementsListEntity(@NonNull String departementCode, @NonNull String departementName) {
        this.departementCode = departementCode;
        this.departementName = departementName;
        areDatasFetched = false;
    }
}
