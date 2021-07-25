package com.project.infosdepartment.model.repositories;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.project.infosdepartment.model.database.DepartmentDatabase;
import com.project.infosdepartment.model.database.dao.DepartmentsDao;
import com.project.infosdepartment.model.database.dao.DepartmentsListDao;
import com.project.infosdepartment.model.database.entity.DepartmentEntity;
import com.project.infosdepartment.model.database.entity.DepartmentsListEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class DepartmentRepository {

    private static final String urlPrefix = "https://geo.api.gouv.fr";
    private static final String format = "&format=json";
    private static final String fields = "?fields=";
    private static final String departmentEndpoint = "/departements/";
    private static final String townEndpoint = "/communes";
    private final DepartmentsDao departmentsDao;
    private final DepartmentsListDao departmentsListDao;
    private final Context ctx;

    public DepartmentRepository(Application application) {
        ctx = application;
        DepartmentDatabase db = DepartmentDatabase.getDatabase(ctx);
        this.departmentsDao = db.departmentsDao();
        this.departmentsListDao = db.departmentsListDao();
    }

    public static String getTownEndpoint() {
        return townEndpoint;
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

    public void fetchInfo(String code) {
        Log.d("[DEBUG][DepartmentRepository]", "fetchInfo : Department number " + code);
        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        String url = getUrlDepartmentEndpoint() + code + townEndpoint + fields + "departement,population" + format;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            String departmentName = "";
            int nbTowns = response.length();
            int inhabitants = 0;
            for (int i = 0; i < nbTowns; i++) {
                try {
                    JSONObject currentObject = response.getJSONObject(i);
                    if (i == 0) {
                        JSONObject departmentInfo = currentObject.getJSONObject("departement");
                        departmentName = departmentInfo.getString("nom");
                    }
                    inhabitants += currentObject.getInt("population");
                } catch (JSONException e) {

                    Log.e("[ERROR][FetchInfoCallback]", "onSuccess: An error occurred when converting an element from a JsonArray to a JsonObject.");
                    throw new RuntimeException("onSuccess: An error occurred when converting an element from a JsonArray to a JsonObject.");
                }

            }
            Log.d("[DEBUG][fetchInfoCallback]", "onSuccess : Insert new DepartementEntity in Department Database\n" +
                    "Department Code : " + code + "\n" +
                    "Department Name : " + departmentName + "\n" +
                    "Number of Towns : " + nbTowns + "\n" +
                    "Number of inhabitants : " + inhabitants + "\n");
            DepartmentEntity entity = new DepartmentEntity(code, departmentName, inhabitants, nbTowns);
            insert(entity);
            setTrueBoolDepartment(code);
        }, error ->
        {
            throw new RuntimeException("Error while fetching the info about " + code + " from the API.");
        });
        requestQueue.add(jsonArrayRequest);
    }


    public DepartmentEntity getDepartmentInfo(String code) {
        DepartmentEntity res = null;
        Future<DepartmentEntity> future;

        Log.d("[DEBUG][DepartmentRepository]", "getDepartmentInfo : Department number " + code);
        if (getIfDataFetched(code) == 1) {
            Log.d("[DEBUG][DepartmentRepository]", "getDepartmentInfo : Data already in cache");

        } else {
            fetchInfo(code);
        }
        future = DepartmentDatabase.getDatabaseWriteExecutor().submit(() -> departmentsDao.getDepartmentFromCode(code));
        try {
            res = future.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException("Error while getting the Department Entity from its code");
        }
        return res;
    }

    public List<DepartmentsListEntity> getDepartmentsList() {
        //   AtomicReference<List<DepartmentsListEntity>> res = new AtomicReference<>();
        List<DepartmentsListEntity> res = null;
        Future<List<DepartmentsListEntity>> future = DepartmentDatabase.getDatabaseWriteExecutor().submit(departmentsListDao::getDepartmentsList);
        try {
            res = future.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException("Error while getting all the DepartmentListEntity from the DB");
        }
        return res;
    }

    public void insert(DepartmentsListEntity departmentsListEntity) {
        DepartmentDatabase.getDatabaseWriteExecutor().execute(() -> departmentsListDao.insert(departmentsListEntity));
    }

    public DepartmentsListEntity getDepartment(String departmentCode) {
        DepartmentsListEntity res = null;
        Future<DepartmentsListEntity> future = DepartmentDatabase.getDatabaseWriteExecutor().submit(() -> {
            return departmentsListDao.getDepartment(departmentCode);
        });
        try {
            res = future.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException("Error while getting a DepartmentListEntity from its code");
        }
        return res;
    }

    public void insert(DepartmentEntity entity) {
        Future<Boolean> future = DepartmentDatabase.getDatabaseWriteExecutor().submit(() -> {
            departmentsDao.insert(entity);
            return true;
        });
        try {
            future.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException("Error while inserting a DepartmentEntity");
        }
    }

    public int getIfDataFetched(String departmentCode) {
        int res = 0;
        Future<Integer> future = DepartmentDatabase.getDatabaseWriteExecutor().submit(() -> departmentsListDao.getIfDataFetched(departmentCode));
        try {
            res = future.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException("Error while getting the result of the insertion of a DepartmentListEntity");
        }
        return res;

    }

    public void updateEntities(DepartmentsListEntity departmentsListEntity) {
        DepartmentDatabase.getDatabaseWriteExecutor().execute(() -> departmentsListDao.updateEntities(departmentsListEntity));
    }

    public void setTrueBoolDepartment(String departmentCode) {
        DepartmentDatabase.getDatabaseWriteExecutor().execute(() -> departmentsListDao.setTrueBoolDepartment(departmentCode));
    }

    public void resetBoolDepartment() {
        DepartmentDatabase.getDatabaseWriteExecutor().execute(departmentsListDao::resetBoolDepartment);
    }

    public void deleteAll() {
        DepartmentDatabase.getDatabaseWriteExecutor().execute(departmentsDao::deleteAll);
    }

    public void cleanDepartmentList() {
        DepartmentDatabase.getDatabaseWriteExecutor().execute(departmentsListDao::deleteAll);
    }

    public void resetCache() {
        resetBoolDepartment();
        deleteAll();
    }
}
