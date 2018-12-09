
package com.example.shaffz.mvvmgettest.di;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.shaffz.mvvmgettest.local.db.AppDatabase;
import dagger.Module;
import dagger.Provides;


import javax.inject.Singleton;


@Module
public class AppModule {


    @Provides
    @Singleton
    AppDatabase provideAppDatabase(@DatabaseInfo String dbName, Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, dbName).fallbackToDestructiveMigration()
                .build();
    }



    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }


}
