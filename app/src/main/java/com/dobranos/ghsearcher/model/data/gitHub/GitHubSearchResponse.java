package com.dobranos.ghsearcher.model.data.gitHub;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GitHubSearchResponse<T>
{
    @SerializedName("total_count")          public long totalCount;
    @SerializedName("incomplete_results")   public boolean incompleteResults;
    @SerializedName("items")                public ArrayList<T> items = new ArrayList<>();

    public long getTotalCount()             { return totalCount; }
    public boolean isIncompleteResults()    { return incompleteResults; }
    public ArrayList<T> getItems()          { return items; }
}