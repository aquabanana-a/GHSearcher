package com.dobranos.ghsearcher.model.data.db.entity;

import androidx.room.*;
import com.dobranos.ghsearcher.model.data.common.IUser;
import com.dobranos.ghsearcher.model.data.db.converters.DbDateConverter;

import java.util.Date;

@Entity(tableName = "users", indices =
    {
        @Index(value = "id"),
        @Index(value = "login", unique = true)
    })
public class DbUser implements IUser
{
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")                    private long id;
    @ColumnInfo(name = "login")                 public String login;
    @ColumnInfo(name = "avatar_url")            public String avatarUrl;
    @ColumnInfo(name = "name")                  public String name;
    @ColumnInfo(name = "company")               public String company;
    @ColumnInfo(name = "location")              public String location;
    @ColumnInfo(name = "bio")                   public String bio;
    @TypeConverters({DbDateConverter.class})
    @ColumnInfo(name = "created_at")            public Date createdAt;

//    @Ignore
//    public boolean isFull;

    public long getId()             { return id; }
    public String getLogin()        { return login; }
    public String getAvatarUrl()    { return avatarUrl; }

    public String getName()         { return name; }
    public String getCompany()      { return company; }
    public String getLocation()     { return location; }
    public String getBio()          { return bio; }
    public Date getCreatedAt()      { return createdAt; }

    public DbUser(long id) { this.id = id; }
    public DbUser(IUser user)
    {
        this.id         = user.getId();
        this.login      = user.getLogin();
        this.avatarUrl  = user.getAvatarUrl();
        this.name       = user.getName();
        this.company    = user.getCompany();
        this.location   = user.getLocation();
        this.bio        = user.getBio();
        this.createdAt  = user.getCreatedAt();
    }
}