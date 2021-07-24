package com.project.infosdepartment.model.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.project.infosdepartment.model.database.entity.DepartmentEntity;

import java.util.List;

@Dao
public interface DepartmentsDao {

    @Query("SELECT * FROM departments")
    List<DepartmentEntity> getDepartments();

    @Query("SELECT * FROM departments WHERE code == :departmentCode")
    DepartmentEntity getDepartmentFromCode(String departmentCode);


    @Query("DELETE FROM departments")
    void deleteAll();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insert(DepartmentEntity... departementEntity);
}
