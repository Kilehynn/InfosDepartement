package com.project.infosdepartment.database.dao;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.project.infosdepartment.database.entity.DepartmentsListEntity;

import java.util.List;

public interface DepartmentsListDao {

    @Query("SELECT * FROM departmentsList")
    List<DepartmentsListEntity> getDepartements();

    @Query("SELECT * FROM departmentsList WHERE code == :departementCode")
    DepartmentsListEntity getDepartement(String departementCode);

    @Update
    void updateEntities(DepartmentsListEntity... departementsListEntities);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(DepartmentsListEntity... departementsListEntities);

    @Query("UPDATE departmentsList SET areDatasFetched = 'TRUE' WHERE code == :departementCode")
    DepartmentsListEntity updateBoolDepartement(String departementCode);

}
