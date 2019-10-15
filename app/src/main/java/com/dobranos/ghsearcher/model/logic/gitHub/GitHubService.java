package com.dobranos.ghsearcher.model.logic.gitHub;

import com.dobranos.ghsearcher.model.logic.gitHub.api.GitHubRepositoriesApi;
import com.dobranos.ghsearcher.model.logic.gitHub.api.GitHubSearchApi;
import com.dobranos.ghsearcher.model.logic.gitHub.api.GitHubUsersApi;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GitHubService
{
    private static final String BASE_URL = "https://api.github.com";

    private Retrofit provider;

    private static GitHubService instance;
    public static GitHubService getInstance()
    {
        if(instance == null)
            instance = new GitHubService();
        return instance;
    }

    private GitHubService()
    {
        provider = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
    }

    public GitHubSearchApi getSearchApi()               { return provider.create(GitHubSearchApi.class); }
    public GitHubUsersApi getUsersApi()                 { return provider.create(GitHubUsersApi.class); }
    public GitHubRepositoriesApi getRepositoriesApi()   { return provider.create(GitHubRepositoriesApi.class); }
}
