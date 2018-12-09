package com.example.shaffz.mvvmgettest.local.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.example.shaffz.mvvmgettest.local.db.dao.ProjectListDao;


@Database(entities = {ProjectListDao.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public static final String TABLE_NAME_GIT_HUB = "github_proj_table";
    private static final String DATABASE_NAME = "git_hub_projects_db";
    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(final Context mContext) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                  /*  INSTANCE = Room.databaseBuilder(mContext.getApplicationContext(), AppDatabase.class, DATABASE_NAME).
                            allowMainThreadQueries().build();*/
                }
            }
        }
        return INSTANCE;
    }
    public abstract ProjectListDao projectListDao();


}
