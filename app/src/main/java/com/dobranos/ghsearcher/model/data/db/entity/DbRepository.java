package com.dobranos.ghsearcher.model.data.db.entity;

import androidx.room.*;
import com.dobranos.ghsearcher.model.data.common.IRepository;
import com.dobranos.ghsearcher.model.data.db.converters.DbDateConverter;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName="repositories", foreignKeys=@ForeignKey(entity = DbUser.class, parentColumns = "login", childColumns = "owner_user_login", deferred=true, onDelete = CASCADE))
public class DbRepository implements IRepository
{
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")                                private long id;
    @ColumnInfo(name = "owner_user_login", index = true)    private String ownerUserLogin;
    @ColumnInfo(name = "name")                              public String name;
    @ColumnInfo(name = "description")                       public String description;
    @ColumnInfo(name = "language")                          public String language;
    @ColumnInfo(name = "stargazers_count")                  public long stargazersCount;
    @ColumnInfo(name = "forks_count")                       public long forksCount;
    @TypeConverters({DbDateConverter.class})
    @ColumnInfo(name = "updated_at")                        public Date updatedAt;
    @ColumnInfo(name = "html_url")                          public String htmlUrl;

    public long getId()                 { return id; }
    public String getOwnerUserLogin()   { return ownerUserLogin; }
    public String getName()             { return name; }
    public String getDescription()      { return description; }
    public String getLanguage()         { return language; }
    public long getStargazersCount()    { return stargazersCount; }
    public long getForksCount()         { return forksCount; }
    public Date getUpdatedAt()          { return updatedAt; }
    public String getHtmlUrl()          { return htmlUrl; }

    public DbRepository(long id, String ownerUserLogin) { this.id = id; this.ownerUserLogin = ownerUserLogin; }
    public DbRepository(IRepository repo, String ownerUserLogin)
    {
        this.id                 = repo.getId();
        this.ownerUserLogin     = ownerUserLogin;
        this.name               = repo.getName();
        this.description        = repo.getDescription();
        this.language           = repo.getLanguage();
        this.stargazersCount    = repo.getStargazersCount();
        this.forksCount         = repo.getForksCount();
        this.updatedAt          = repo.getUpdatedAt();
        this.htmlUrl            = repo.getHtmlUrl();
    }
}