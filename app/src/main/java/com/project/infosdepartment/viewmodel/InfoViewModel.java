package com.project.infosdepartment.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.project.infosdepartment.model.database.entity.DepartmentEntity;
import com.project.infosdepartment.model.repositories.DepartmentRepository;

public class InfoViewModel extends AndroidViewModel {
    private final DepartmentRepository departmentRepository;
    private String departmentName;
    private String departmentCode;
    private Integer departmentInhabitant;
    private Integer departmentTowns;


    public InfoViewModel(@NonNull Application application) {
        super(application);
        departmentRepository = new DepartmentRepository(application);
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public Integer getDepartmentInhabitant() {
        return departmentInhabitant;
    }

    public Integer getDepartmentTowns() {
        return departmentTowns;
    }

    public int getIfDataFetched(String departmentCode) {
        return departmentRepository.isDataFetched(departmentCode);
    }

    public DepartmentEntity getDepartmentInfo(String departmentCode) {
        return departmentRepository.getDepartmentEntity(departmentCode);
    }

}
