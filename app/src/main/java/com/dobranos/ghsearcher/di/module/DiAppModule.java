package com.dobranos.ghsearcher.di.module;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Room;
import com.dobranos.ghsearcher.model.data.db.Db;
import com.dobranos.ghsearcher.model.logic.db.DbBookmarkProvider;
import com.dobranos.ghsearcher.model.logic.db.DbServiceProvider;
import com.dobranos.ghsearcher.model.logic.gitHub.GitHubService;
import com.dobranos.ghsearcher.model.logic.gitHub.GitHubServiceProvider;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class DiAppModule
{
    private Context appContext;

    public DiAppModule(@NonNull Context cx)
    {
        appContext = cx;
    }

    @Singleton
    @Provides
    Context provideContext()
    {
        return appContext;
    }

    @Singleton
    @Provides
    Db provideDatabase()
    {
        return Room.databaseBuilder(appContext, Db.class, "database").build();
    }

    @Singleton
    @Provides
    GitHubService provideGitHubApi()
    {
        return new GitHubService();
    }

    @Singleton
    @Provides
    DbBookmarkProvider provideBookmarkService() { return new DbBookmarkProvider(); }

    @Singleton
    @Provides
    GitHubServiceProvider provideGitHubService()
    {
        return new GitHubServiceProvider();
    }

    @Singleton
    @Provides
    DbServiceProvider provideDbService()
    {
        return new DbServiceProvider();
    }
}