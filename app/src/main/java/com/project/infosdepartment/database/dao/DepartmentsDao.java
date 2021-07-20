package com.project.infosdepartment.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.project.infosdepartment.database.entity.DepartmentEntity;

import java.util.List;

@Dao
public interface DepartmentsDao {

    @Query("SELECT * FROM departments")
    List<DepartmentEntity> getDepartments();

    @Query("SELECT * FROM departments WHERE code == :departmentCode")
    DepartmentEntity getDepartmentFromCode(String departmentCode);


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(DepartmentEntity... departementEntities);
}
