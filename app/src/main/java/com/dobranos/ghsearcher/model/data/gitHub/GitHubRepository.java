package com.dobranos.ghsearcher.model.data.gitHub;

import com.dobranos.ghsearcher.model.data.common.IRepository;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class GitHubRepository implements IRepository
{
    @SerializedName("id")                   private long id;
    @SerializedName("node_id")              private String nodeId;
    @SerializedName("name")                 private String name;
    @SerializedName("full_name")            private String fullName;
    @SerializedName("owner")                private GitHubUser owner;
    @SerializedName("private")              private boolean isPrivate;
    @SerializedName("html_url")             private String htmlUrl;
    @SerializedName("description")          private String description;
    @SerializedName("fork")                 private boolean fork;
    @SerializedName("url")                  private String url;
    @SerializedName("archive_url")          private String archiveUrl;
    @SerializedName("assignees_url")        private String assigneesUrl;
    @SerializedName("blobs_url")            private String blobsUrl;
    @SerializedName("branches_url")         private String branchesUrl;
    @SerializedName("collaborators_url")    private String collaboratorsUrl;
    @SerializedName("comments_url")         private String commentsUrl;
    @SerializedName("commits_url")          private String commitsUrl;
    @SerializedName("compare_url")          private String compareUrl;
    @SerializedName("contents_url")         private String contentsUrl;
    @SerializedName("contributors_url")     private String contributorsUrl;
    @SerializedName("deployments_url")      private String deploymentsUrl;
    @SerializedName("downloads_url")        private String downloadsUrl;
    @SerializedName("events_url")           private String eventsUrl;
    @SerializedName("forks_url")            private String forksUrl;
    @SerializedName("git_commits_url")      private String gitCommitsUrl;
    @SerializedName("git_refs_url")         private String gitRefsUrl;
    @SerializedName("git_tags_url")         private String gitTagsUrl;
    @SerializedName("git_url")              private String gitUrl;
    @SerializedName("issue_comment_url")    private String issueCommentUrl;
    @SerializedName("issue_events_url")     private String issueEventsUrl;
    @SerializedName("issues_url")           private String issuesUrl;
    @SerializedName("keys_url")             private String keysUrl;
    @SerializedName("labels_url")           private String labelsUrl;
    @SerializedName("languages_url")        private String languagesUrl;
    @SerializedName("merges_url")           private String mergesUrl;
    @SerializedName("milestones_url")       private String milestonesUrl;
    @SerializedName("notifications_url")    private String notificationsUrl;
    @SerializedName("pulls_url")            private String pullsUrl;
    @SerializedName("releases_url")         private String releasesUrl;
    @SerializedName("ssh_url")              private String sshUrl;
    @SerializedName("stargazers_url")       private String stargazersUrl;
    @SerializedName("statuses_url")         private String statusesUrl;
    @SerializedName("subscribers_url")      private String subscribersUrl;
    @SerializedName("subscription_url")     private String subscriptionUrl;
    @SerializedName("tags_url")             private String tagsUrl;
    @SerializedName("teams_url")            private String teamsUrl;
    @SerializedName("trees_url")            private String treesUrl;
    @SerializedName("clone_url")            private String cloneUrl;
    @SerializedName("mirror_url")           private String mirrorUrl;
    @SerializedName("hooks_url")            private String hooksUrl;
    @SerializedName("svn_url")              private String svnUrl;
    @SerializedName("homepage")             private String homepage;
    @SerializedName("language")             private String language;
    @SerializedName("forks_count")          private long forksCount;
    @SerializedName("stargazers_count")     private long stargazersCount;
    @SerializedName("watchers_count")       private long watchersCount;
    @SerializedName("size")                 private long size;
    @SerializedName("default_branch")       private String defaultBranch;
    @SerializedName("open_issues_count")    private long openIssuesCount;
    @SerializedName("is_template")          private boolean isTemplate;
    @SerializedName("topics")               private ArrayList<String> topics = new ArrayList<>();
    @SerializedName("has_issues")           private boolean hasIssues;
    @SerializedName("has_projects")         private boolean hasProjects;
    @SerializedName("has_wiki")             private boolean hasWiki;
    @SerializedName("has_pages")            private boolean hasPages;
    @SerializedName("has_downloads")        private boolean hasDownloads;
    @SerializedName("archived")             private boolean archived;
    @SerializedName("disabled")             private boolean disabled;
    @SerializedName("pushed_at")            private Date pushedAt;
    @SerializedName("created_at")           private Date createdAt;
    @SerializedName("updated_at")           private Date updatedAt;
    @SerializedName("permissions")          private GitHubPermissions permissions;
    @SerializedName("template_repository")  private String templateRepository;
    @SerializedName("subscribers_count")    private long subscribersCount;
    @SerializedName("network_count")        private long networkCount;
    @SerializedName("license")              private GitHubLicense license;

    public long getId()                 { return id; }
    public String getName()             { return name; }
    public String getFullName()         { return fullName; }
    public String getDescription()      { return description; }
    public String getLanguage()         { return language; }
    public long getStargazersCount()    { return stargazersCount; }
    public long getForksCount()         { return forksCount; }
    public long getWatchersCount()      { return watchersCount; }
    public Date getUpdatedAt()          { return updatedAt; }
    public String getUrl()              { return url; }
    public String getHtmlUrl()          { return htmlUrl; }
}