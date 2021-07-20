package com.project.infosdepartement.dao;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.project.infosdepartement.entity.DepartementsListEntity;

import java.util.List;

public interface DepartementsListDao {

    @Query("SELECT * FROM departementsList")
    List<DepartementsListEntity> getDepartements();

    @Query("SELECT * FROM departementsList WHERE code == :departementCode")
    DepartementsListEntity getDepartement(String departementCode);

    @Update
    void updateEntities(DepartementsListEntity... departementsListEntities);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(DepartementsListEntity... departementsListEntities);

    @Query("UPDATE departementsList SET areDatasFetched = 'TRUE' WHERE code == :departementCode")
    DepartementsListEntity updateBoolDepartement(String departementCode);

}
