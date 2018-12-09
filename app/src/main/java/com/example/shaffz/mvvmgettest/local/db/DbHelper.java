

package com.example.shaffz.mvvmgettest.local.db;

import com.example.shaffz.mvvmgettest.local.db.entitites.RepoModel;

import io.reactivex.Observable;

import java.util.List;


public interface DbHelper {

    Observable<List<RepoModel>> getAllRepos();

    Observable<Boolean> saveRepoList(List<RepoModel> reposList);
}
