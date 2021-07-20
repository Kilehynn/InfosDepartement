package com.project.infosdepartement.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.project.infosdepartement.database.dao.DepartementsDao;
import com.project.infosdepartement.database.dao.DepartementsListDao;
import com.project.infosdepartement.database.entity.DepartementEntity;
import com.project.infosdepartement.database.entity.DepartementsListEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Create our database with 2 tables, one for each Entity
@Database(entities = {DepartementEntity.class, DepartementsListEntity.class}, version = 1, exportSchema = false)
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

    public abstract DepartementsDao departementsDao();

    public abstract DepartementsListDao departementsListDao();
}



