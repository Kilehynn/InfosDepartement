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
    List<DepartmentEntity> getDepartements();

    @Query("SELECT * FROM departments WHERE code == :departmentCode")
    DepartmentEntity getDepartement(String departmentCode);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(DepartmentEntity... departementEntities);
}
