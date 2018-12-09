package com.example.shaffz.mvvmgettest.local.db.dao;

import android.arch.persistence.room.*;
import com.example.shaffz.mvvmgettest.local.db.AppDatabase;
import com.example.shaffz.mvvmgettest.local.db.entitites.RepoModel;


import java.util.List;

@Dao
public interface ProjectListDao {


    @Insert
    void insertRepos(List<RepoModel> projectList);

    @Query("SELECT * FROM " + AppDatabase.TABLE_NAME_GIT_HUB)
    List<RepoModel> fetchAllRepos();



    @Query("SELECT * FROM " + AppDatabase.TABLE_NAME_GIT_HUB + " WHERE _id = :project_id")
    List<RepoModel> fetchAllRepos(int project_id);


}
