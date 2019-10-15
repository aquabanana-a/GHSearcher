package com.dobranos.ghsearcher.model.logic.gitHub.api;

import com.dobranos.ghsearcher.model.data.gitHub.GitHubSearchResponse;
import com.dobranos.ghsearcher.model.data.gitHub.GitHubUser;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

// see https://developer.github.com/v3/search/
public interface GitHubSearchApi
{
    String SORT_FOLLOWERS       = "followers";
    String SORT_REPOSITIORIES   = "repositories";
    String SORT_JOINED          = "joined";
    String SORT_DEFAULT         = "best match";

    String ORDER_ASC        = "asc";
    String ORDER_DESC       = "desc";
    String ORDER_DEFAULT    = "desc";

    long PAGE_SIZE_MAX      = 100;
    long PAGE_SIZE_DEFAULT  = 30;

    /// Find users via various criteria. This method returns up to 100 results per page.
    //
    //When searching for users, you can get text match metadata for the issue login, email, and name fields when you pass the text-match media type. For more details about highlighting search results, see Text match metadata. For more details about how to receive highlighted search results, see Text match metadata.
    //
    //GET /search/users
    //Parameters
    //Name	Type	Description
    //q	string	Required. The query contains one or more search keywords and qualifiers. Qualifiers allow you to limit your search to specific areas of GitHub. The REST API supports the same qualifiers as GitHub.com. To learn more about the format of the query, see Constructing a search query. See "Searching users" for a detailed list of qualifiers.
    //sort	string	Sorts the results of your query by number of followers or repositories, or when the person joined GitHub. Default: best match
    //order	string	Determines whether the first search result returned is the highest number of matches (desc) or lowest number of matches (asc). This parameter is ignored unless you provide sort. Default: desc
    @GET("/search/users")
    Observable<GitHubSearchResponse<GitHubUser>> searchUsers(@Query("q") final String query,
                                                             @Query("sort") final String sort,
                                                             @Query("order") final String order,
                                                             @Query("page") final long page,
                                                             @Query("per_page") final long pageSize);

    @GET("/search/users")
    Call<GitHubSearchResponse<GitHubUser>> searchUsers(@Query("q") final String query, @Query("page") final long page, @Query("per_page") final long pageSize);

    @GET("/search/users")
    Call<GitHubSearchResponse<GitHubUser>> searchUsers(@Query("q") final String query, @Query("page") final long page);

    @GET("/search/users")
    Call<GitHubSearchResponse<GitHubUser>> searchUsers(@Query("q") final String query);

    // searchCommits
    // Not implemented

    // searchCode
    // Not implemented

    // ...
}