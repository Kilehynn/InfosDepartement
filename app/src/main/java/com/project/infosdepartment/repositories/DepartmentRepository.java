package com.project.infosdepartment.repositories;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.project.infosdepartment.database.DepartmentDatabase;
import com.project.infosdepartment.database.dao.DepartmentsDao;
import com.project.infosdepartment.database.dao.DepartmentsListDao;
import com.project.infosdepartment.database.entity.DepartmentEntity;
import com.project.infosdepartment.utils.FetchInfoCallback;

public class DepartmentRepository {

    private static final String urlPrefix = "https://geo.api.gouv.fr";
    private static final String format = "&format=json";
    private static final String fields = "?fields=";
    private static final String departmentEndpoint = "/departements/";
    private final String townEndpoint = "/communes";
    private final DepartmentsDao departmentsDao;
    private final DepartmentsListDao departmentsListDao;
    private final Context ctx;

    public DepartmentRepository(Application application) {
        ctx = application;
        DepartmentDatabase db = DepartmentDatabase.getDatabase(ctx);
        this.departmentsDao = db.departmentsDao();
        this.departmentsListDao = db.departmentsListDao();
    }

    public static String getUrlPrefix() {
        return urlPrefix;
    }

    public static String getUrlDepartmentEndpoint() {
        return urlPrefix + departmentEndpoint;
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

    public void fetchInfo(String code, FetchInfoCallback infoCallback) {
        Log.i("[INFO][DepartmentRepository]", "fetchInfo : Department number " + code);
        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        String url = getUrlDepartmentEndpoint() + code + townEndpoint + fields + "departement,population" + format;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> infoCallback.onSuccess(response, departmentsDao, code), error ->
        {
            // TODO: Handle error
        });
        requestQueue.add(jsonArrayRequest);
    }

    public DepartmentEntity getDepartmentInfo(String code) {
        Log.i("[INFO][DepartmentRepository]", "getDepartmentInfo : Department number " + code);
        if (departmentsListDao.getIfDataFetched(code)) {
            Log.i("[INFO][DepartmentRepository]", "getDepartmentInfo : Data already in cache");
            return departmentsDao.getDepartmentFromCode(code);
        } else {
            fetchInfo(code, new FetchInfoCallback());
            return departmentsDao.getDepartmentFromCode(code);
        }
    }

}
