package com.dobranos.ghsearcher.model.data.db.entity;

import androidx.room.ColumnInfo;
import com.dobranos.ghsearcher.model.data.common.IUser;
import com.dobranos.ghsearcher.model.data.common.IUserNote;

public class DbUserNote implements IUserNote
{
    @ColumnInfo(name = "id")            private long id;
    @ColumnInfo(name = "login")         public String login;
    @ColumnInfo(name = "avatar_url")    public String avatarUrl;

    public long getId()             { return id; }
    public String getLogin()        { return login; }
    public String getAvatarUrl()    { return avatarUrl; }

    public DbUserNote(long id) { this.id = id; }
    public DbUserNote(IUser user)
    {
        this.id         = user.getId();
        this.login      = user.getLogin();
        this.avatarUrl  = user.getAvatarUrl();
    }
}