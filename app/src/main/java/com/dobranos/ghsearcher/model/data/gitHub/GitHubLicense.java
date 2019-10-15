package com.dobranos.ghsearcher.model.data.gitHub;

import com.google.gson.annotations.SerializedName;

public class GitHubLicense
{
    @SerializedName("key")      private String key;
    @SerializedName("name")     private String name;
    @SerializedName("spdx_id")  private String spxId;
    @SerializedName("url")      private String url;
    @SerializedName("node_id")  private String nodeId;

    public String getName() { return name; }
    public String getUrl()  { return url; }
}