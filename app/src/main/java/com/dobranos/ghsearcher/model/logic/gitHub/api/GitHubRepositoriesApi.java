package com.dobranos.ghsearcher.model.logic.gitHub.api;

import com.dobranos.ghsearcher.model.data.gitHub.GitHubRepository;
import com.dobranos.ghsearcher.model.data.gitHub.GitHubUser;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.ArrayList;
import java.util.List;

// see https://developer.github.com/v3/repos/
public interface GitHubRepositoriesApi
{
    String TYPE_ALL     = "all";
    String TYPE_OWNER   = "owner";
    String TYPE_MEMBER  = "member";
    String TYPE_DEFAULT = "owner";

    String SORT_CREATED     = "created";
    String SORT_UPDATED     = "updated";
    String SORT_PUSHED      = "pushed";
    String SORT_FULL_NAME   = "full_name";
    String SORT_DEFAULT     = "full_name";

    String DIRECTION_ASC        = "asc";
    String DIRECTION_DESC       = "desc";
    String DIRECTION_DEFAULT    = "asc";

    long PAGE_SIZE_MAX          = 100;
    long PAGE_SIZE_DEFAULT      = 30;

    //Lists public repositories for the specified user.
    //
    //GET /users/:username/repos
    //Parameters
    //Name	Type	Description
    //type	string	Can be one of all, owner, member. Default: owner
    //sort	string	Can be one of created, updated, pushed, full_name. Default: full_name
    //direction	string	Can be one of asc or desc. Default: asc when using full_name, otherwise desc
    @GET("/users/{username}/repos")
    Observable<List<GitHubRepository>> getUserRepositories(@Path("username") final String userName,
                                                           @Query("type") final String type,
                                                           @Query("sort") final String sort,
                                                           @Query("direction") final String direction,
                                                           @Query("page") final long page,
                                                           @Query("per_page") final long pageSize);

    @GET("/users/{username}/repos")
    Call<ArrayList<GitHubRepository>> getUserRepositories(@Path("username") final String userName, @Query("page") final long page, @Query("per_page") final long pageSize);

    @GET("/users/{username}/repos")
    Call<ArrayList<GitHubRepository>> getUserRepositories(@Path("username") final String userName, @Query("page") final long page);

    @GET("/users/{username}/repos")
    Call<ArrayList<GitHubRepository>> getUserRepositories(@Path("username") final String userName);
}