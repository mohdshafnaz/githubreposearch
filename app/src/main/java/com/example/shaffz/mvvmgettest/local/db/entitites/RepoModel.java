package com.example.shaffz.mvvmgettest.local.db.entitites;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import com.example.shaffz.mvvmgettest.local.db.AppDatabase;

@Entity(tableName = AppDatabase.TABLE_NAME_GIT_HUB)
public class RepoModel {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    public int _id;
    public String name;
    public String description;
    public String avatar_url;
    public String type;




}
