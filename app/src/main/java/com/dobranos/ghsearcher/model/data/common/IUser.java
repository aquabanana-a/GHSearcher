package com.dobranos.ghsearcher.model.data.common;

import java.util.Date;

public interface IUser extends IUserNote
{
    String getName();
    String getCompany();
    String getLocation();
    String getBio();
    Date getCreatedAt();
}