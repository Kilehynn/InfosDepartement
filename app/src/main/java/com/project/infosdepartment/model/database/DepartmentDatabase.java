package com.project.infosdepartment.model.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.project.infosdepartment.model.database.dao.DepartmentsDao;
import com.project.infosdepartment.model.database.dao.DepartmentsListDao;
import com.project.infosdepartment.model.database.entity.DepartmentEntity;
import com.project.infosdepartment.model.database.entity.DepartmentsListEntity;
import com.project.infosdepartment.model.repositories.DepartmentRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

//Create our database with 2 tables, one for each Entity
@Database(entities = {DepartmentEntity.class, DepartmentsListEntity.class}, version = 1, exportSchema = false)
public abstract class DepartmentDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 4;
    private static Context ctx;
    private static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static volatile DepartmentDatabase instance = null;


    //TODO : Tried to do it with a callback, but couldn't make it work, to investigate
 /*   private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            RequestQueue requestQueue = Volley.newRequestQueue(ctx);
            String url = DepartmentRepository.getUrlDepartmentEndpoint();
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject current = response.getJSONObject(i);

                        String departmentName = current.getString("nom");
                        String departmentCode = current.getString("code");

                        DepartmentsListDao departmentsListDao = instance.departmentsListDao();
                        databaseWriteExecutor.execute(() -> {
                            DepartmentsListEntity departmentEntity = new DepartmentsListEntity(departmentCode, departmentName);
                            departmentsListDao.insert(departmentEntity);
                        });

                    } catch (JSONException e) {
                        Log.e("[ERROR][DepartmentDatabase]", "onResponse: An error occurred when populating the database.");
                        // TODO: Handle error
                    }
                }

            }, error -> {
                // TODO: Handle error
            });
            requestQueue.add(jsonArrayRequest);
        }
    };*/

    public static DepartmentDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (DepartmentDatabase.class) {
                if (instance == null) {
                    ctx = context;
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            DepartmentDatabase.class, "departement_database")
                            //  .addCallback(sRoomDatabaseCallback)
                            .build();
                    instance.fillDB();

                }
            }
        }
        return instance;
    }

    private void fillDB() {

        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        String url = DepartmentRepository.getUrlDepartmentEndpoint();
        DepartmentsListDao departmentsListDao = instance.departmentsListDao();
        Integer anyDepartment = 0;
        Future<Integer> future = databaseWriteExecutor.submit(departmentsListDao::getAnyDepartment);
        try {
            anyDepartment = future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        if (anyDepartment < 101) {
            Log.i("[DEBUG][DepartmentDatabase]", "FillDB: Populating database.");
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
                ArrayList<DepartmentsListEntity> departmentsListEntities = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject current = response.getJSONObject(i);
                        String departmentName = current.getString("nom");
                        String departmentCode = current.getString("code");
                        Log.i("[DEBUG][DepartmentDatabase]", "onResponse: Create Department Entity.");
                        Log.i("[DEBUG][DepartmentDatabase]", "onResponse: Department " + (i + 1) + " out of " + response.length() + ".");
                        DepartmentsListEntity departmentEntity = new DepartmentsListEntity(departmentCode, departmentName);
                        departmentsListEntities.add(departmentEntity);
                    } catch (JSONException e) {
                        Log.e("[ERROR][DepartmentDatabase]", "onResponse: An error occurred when populating the database.");
                    }

                }
                Future<?> insertFuture = databaseWriteExecutor.submit(() -> departmentsListDao.insert(departmentsListEntities.toArray(new DepartmentsListEntity[response.length()])));
                try {
                    insertFuture.get();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }, error -> {
                // TODO: Handle error
            });
            requestQueue.add(jsonArrayRequest);
        } else {
            Log.i("[DEBUG][DepartmentDatabase]", "FillDB: Database is full.");
        }/*
        databaseWriteExecutor.execute(() -> {
      List<DepartmentsListEntity> anyDepartment = departmentsListDao.getAnyDepartment();
        });*/

    }

    public abstract DepartmentsDao departmentsDao();

    public abstract DepartmentsListDao departmentsListDao();

    public static ExecutorService getDatabaseWriteExecutor() {
        return databaseWriteExecutor;
    }
}



