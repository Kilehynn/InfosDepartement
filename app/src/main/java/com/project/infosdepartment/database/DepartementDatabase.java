package com.project.infosdepartment.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.project.infosdepartment.database.dao.DepartmentsDao;
import com.project.infosdepartment.database.dao.DepartmentsListDao;
import com.project.infosdepartment.database.entity.DepartmentEntity;
import com.project.infosdepartment.database.entity.DepartmentsListEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Create our database with 2 tables, one for each Entity
@Database(entities = {DepartmentEntity.class, DepartmentsListEntity.class}, version = 1, exportSchema = false)
public abstract class DepartementDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
    private static volatile DepartementDatabase instance = null;

    public static DepartementDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (DepartementDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            DepartementDatabase.class, "departement_database")
                            .build();
                }
            }
        }
        return instance;
    }

    public abstract DepartmentsDao departementsDao();

    public abstract DepartmentsListDao departementsListDao();
}



