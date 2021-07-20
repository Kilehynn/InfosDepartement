package com.project.infosdepartment.repositories;

import android.app.Application;

import com.project.infosdepartment.database.DepartmentDatabase;
import com.project.infosdepartment.database.dao.DepartmentsDao;
import com.project.infosdepartment.database.dao.DepartmentsListDao;

public class DepartmentRepository {

    private static final String url = "https://geo.api.gouv.fr";
    private static final String format = "&format=json";
    private static final String fields = "fields=";
    private static final String departmentEndpoint = "/departements/";
    private final String townEndpoint = "/communes";
    private DepartmentsDao departmentsDao;
    private DepartmentsListDao departmentsListDao;

    public DepartmentRepository(Application application) {
        DepartmentDatabase db = DepartmentDatabase.getDatabase(application);
        this.departmentsDao = db.departmentsDao();
        this.departmentsListDao = db.departmentsListDao();
    }

    public static String getUrl() {
        return url;
    }

    public static String getFormat() {
        return format;
    }

    public static String getFields() {
        return fields;
    }

    public static String getDepartmentEndpoint() {
        return departmentEndpoint;
    }

    public static String getUrlDepartmentEndpoint() {
        return url + departmentEndpoint;
    }
}
