package com.project.infosdepartment.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.project.infosdepartment.model.database.entity.DepartmentEntity;
import com.project.infosdepartment.model.database.entity.DepartmentsListEntity;
import com.project.infosdepartment.model.repositories.DepartmentRepository;

import java.util.List;

public class DepartmentViewModel extends AndroidViewModel {

    private final DepartmentRepository departmentRepository;
    private final LiveData<List<DepartmentsListEntity>> departmentsListEntity;

    public DepartmentViewModel(@NonNull Application application) {
        super(application);
        departmentRepository = new DepartmentRepository(application);
        departmentsListEntity = departmentRepository.getDepartmentsList();
    }

    public void updateBoolean(String departmentCode) {
        departmentRepository.setTrueBoolDepartment(departmentCode);
    }

    public DepartmentEntity getDepartmentInfo(String departmentCode) {
        return departmentRepository.getDepartmentInfo(departmentCode);
    }

    public void resetCache() {
        departmentRepository.resetCache();
    }

    public LiveData<List<DepartmentsListEntity>> getDepartmentsListEntity() {
        return departmentsListEntity;
    }
}
