package com.project.infosdepartement.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.project.infosdepartement.entity.DepartementEntity;

import java.util.List;

@Dao
public interface DepartementsDao {

    @Query("SELECT * FROM departements")
    List<DepartementEntity> getDepartements();

    @Query("SELECT * FROM departements WHERE code == :departementCode")
    DepartementEntity getDepartement(int departementCode);
}