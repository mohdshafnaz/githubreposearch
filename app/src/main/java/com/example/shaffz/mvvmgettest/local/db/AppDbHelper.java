
package com.example.shaffz.mvvmgettest.local.db;

import com.example.shaffz.mvvmgettest.local.db.entitites.RepoModel;
import io.reactivex.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.concurrent.Callable;



@Singleton
public class AppDbHelper implements DbHelper {

    private final AppDatabase mAppDatabase;

    @Inject
    public AppDbHelper(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }

    @Override
    public Observable<List<RepoModel>> getAllRepos() {
        return Observable.fromCallable(new Callable<List<RepoModel>>() {
            @Override
            public List<RepoModel> call() throws Exception {
                return mAppDatabase.projectListDao().fetchAllRepos();
            }
        });
    }

    @Override
    public Observable<Boolean> saveRepoList(final List<RepoModel> reposList) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.projectListDao().insertRepos(reposList);
                return true;
            }
        });
    }

/*    @Override
    public Observable<List<RepoModel>> getAllRepos() {
        return Observable.fromCallable(new Callable<List<RepoModel>>() {
            @Override
            public List<RepoModel> call() throws Exception {
                return mAppDatabase.projectListDao().fetchAllRepos();
            }
        });
    }





    @Override
    public Observable<Boolean> saveQuestionList(final List<RepoModel> questionList) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.questionDao().insertAll(questionList);
                return true;
            }
        });
    }*/
}
