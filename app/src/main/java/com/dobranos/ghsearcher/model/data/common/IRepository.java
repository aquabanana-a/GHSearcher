package com.dobranos.ghsearcher.model.data.common;

import java.util.Date;

public interface IRepository
{
    long getId();
    String getName();
    String getDescription();
    String getLanguage();
    long getStargazersCount();
    long getForksCount();
    Date getUpdatedAt();
    String getHtmlUrl();
}
