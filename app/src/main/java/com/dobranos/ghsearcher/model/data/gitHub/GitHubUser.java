package com.dobranos.ghsearcher.model.data.gitHub;

import com.dobranos.ghsearcher.model.data.common.IUser;
import com.dobranos.ghsearcher.model.data.common.IUserNote;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class GitHubUser implements IUser
{
    @SerializedName("login")                private String login;
    @SerializedName("id")                   private long id;
    @SerializedName("node_id")              private String node_id;
    @SerializedName("avatar_url")           private String avatarUrl;
    @SerializedName("gravatar_id")          private String gravatarId;
    @SerializedName("url")                  private String url;
    @SerializedName("html_url")             private String htmlUrl;
    @SerializedName("followers_url")        private String followersUrl;
    @SerializedName("following_url")        private String followingUrl;        // +
    @SerializedName("gists_url")            private String gistsUrl;            // +
    @SerializedName("starred_url")          private String starredUrl;          // +
    @SerializedName("subscriptions_url")    private String subscriptionsUrl;
    @SerializedName("organizations_url")    private String organizationsUrl;
    @SerializedName("repos_url")            private String reposUrl;
    @SerializedName("events_url")           private String events_url;          // +
    @SerializedName("received_events_url")  private String receivedEventsUrl;
    @SerializedName("type")                 private String type;
    @SerializedName("score")                private Double score;               // -
    @SerializedName("site_admin")           private Boolean siteAdmin;          // +-
    @SerializedName("name")                 private String name;                // +
    @SerializedName("company")              private String company;             // +
    @SerializedName("blog")                 private String blog;                // +
    @SerializedName("location")             private String location;            // +
    @SerializedName("email")                private String email;               // +
    @SerializedName("hireable")             private Boolean hireable;           // +
    @SerializedName("bio")                  private String bio;                 // +
    @SerializedName("public_repos")         private Long publicRepos;           // +
    @SerializedName("public_gists")         private Long publicGists;           // +
    @SerializedName("followers")            private Long followers;             // +
    @SerializedName("following")            private Long following;             // +
    @SerializedName("created_at")           private Date createdAt;             // +
    @SerializedName("updated_at")           private Date updatedAt;             // +

    public long getId()             { return id; }
    public String getLogin()        { return login; }
    public String getAvatarUrl()    { return avatarUrl; }

    public String getName()         { return name; }
    public String getCompany()      { return company; }
    public String getLocation()     { return location; }
    public String getBio()          { return bio; }
    public Date getCreatedAt()      { return createdAt; }
}