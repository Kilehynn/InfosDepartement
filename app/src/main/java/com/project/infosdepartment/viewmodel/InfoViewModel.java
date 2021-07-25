package com.project.infosdepartment.viewmodel;

import android.app.Application;
import android.util.Log;

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

    public void setInfo(DepartmentEntity entity) {
        Log.i("[DEBUG][INFO]", "setInfo: ");
        this.departmentCode = entity.getDepartmentCode();
        this.departmentName = entity.getDepartmentName();
        this.departmentInhabitant = entity.getInhabitants();
        this.departmentTowns = entity.getNbTowns();
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
        return departmentRepository.getIfDataFetched(departmentCode);
    }

    public DepartmentEntity getDepartmentInfo(String departmentCode) {
        return departmentRepository.getDepartmentInfo(departmentCode);
    }

}
