package com.dobranos.ghsearcher.model.logic.gitHub.api;

import com.dobranos.ghsearcher.model.data.gitHub.GitHubUser;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

// see https://developer.github.com/v3/users/
public interface GitHubUsersApi
{
    @GET("/users/{username}")
    Observable<GitHubUser> getUser(@Path("username") final String userName);
}