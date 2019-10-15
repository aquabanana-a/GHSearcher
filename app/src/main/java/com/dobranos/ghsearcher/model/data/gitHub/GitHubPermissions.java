package com.dobranos.ghsearcher.model.data.gitHub;

import com.google.gson.annotations.SerializedName;

public class GitHubPermissions
{
    @SerializedName("admin")    private boolean admin;
    @SerializedName("push")     private boolean push;
    @SerializedName("pull")     private boolean pull;

    public boolean isAdmin()        { return admin; }
    public boolean isPushEnabled()  { return push; }
    public boolean isPullEnabled()  { return pull; }
}