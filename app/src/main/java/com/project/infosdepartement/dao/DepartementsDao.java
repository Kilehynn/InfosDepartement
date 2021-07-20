package com.project.infosdepartement.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.project.infosdepartement.entity.DepartementEntity;

import java.util.List;

@Dao
public interface DepartementsDao {

    @Query("SELECT * FROM departements")
    List<DepartementEntity> getDepartements();

    @Query("SELECT * FROM departements WHERE code == :departementCode")
    DepartementEntity getDepartement(String departementCode);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(DepartementEntity... departementEntities);
}
