package com.project.infosdepartment.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.project.infosdepartment.database.dao.DepartmentsDao;
import com.project.infosdepartment.database.dao.DepartmentsListDao;
import com.project.infosdepartment.database.entity.DepartmentEntity;
import com.project.infosdepartment.database.entity.DepartmentsListEntity;
import com.project.infosdepartment.repositories.DepartmentRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Create our database with 2 tables, one for each Entity
@Database(entities = {DepartmentEntity.class, DepartmentsListEntity.class}, version = 1, exportSchema = false)
public abstract class DepartmentDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 4;
    private static Context ctx;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static volatile DepartmentDatabase instance = null;
    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
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
                        DepartmentsListEntity departmentEntity = new DepartmentsListEntity(departmentCode, departmentName);
                        departmentsListDao.insert(departmentEntity);

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
    };

    public static DepartmentDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (DepartmentDatabase.class) {
                if (instance == null) {
                    ctx = context;
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            DepartmentDatabase.class, "departement_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return instance;
    }

    public abstract DepartmentsDao departmentsDao();

    public abstract DepartmentsListDao departmentsListDao();
}



