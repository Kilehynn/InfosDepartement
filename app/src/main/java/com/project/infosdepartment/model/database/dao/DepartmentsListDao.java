package com.project.infosdepartment.model.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.project.infosdepartment.model.database.entity.DepartmentsListEntity;

import java.util.List;

@Dao
public interface DepartmentsListDao {

    @Query("SELECT * FROM departmentsList")
    LiveData<List<DepartmentsListEntity>> getDepartmentsList();

    @Query("SELECT * FROM departmentsList WHERE code == :departmentCode")
    DepartmentsListEntity getDepartment(String departmentCode);

    @Query("SELECT areDataFetched FROM departmentsList WHERE code == :departmentCode")
    Boolean getIfDataFetched(String departmentCode);

    @Update
    void updateEntities(DepartmentsListEntity... departmentsListEntities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DepartmentsListEntity... departmentsListEntities);

    @Query("UPDATE departmentsList SET areDataFetched = 'TRUE' WHERE code == :departmentCode")
    void setTrueBoolDepartment(String departmentCode);

    @Query("UPDATE departmentsList SET areDataFetched = 'FALSE'")
    void resetBoolDepartment();
}
