package com.dobranos.ghsearcher.model.logic.db;

import com.dobranos.ghsearcher.di.Injector;
import com.dobranos.ghsearcher.model.data.common.IRepository;
import com.dobranos.ghsearcher.model.data.common.IUser;
import com.dobranos.ghsearcher.model.data.db.Db;
import com.dobranos.ghsearcher.model.data.db.entity.DbRepository;
import com.dobranos.ghsearcher.model.data.db.entity.DbUser;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class DbBookmarkProvider
{
    @Inject Db database;

    private List<Long> knownUserIds = new ArrayList<>();

    public DbBookmarkProvider()
    {
        Injector.getSingletonComponent()
            .inject(this);
    }

    private static DbBookmarkProvider instance;
    public static DbBookmarkProvider getInstance()
    {
        if(instance == null)
        {
            instance = new DbBookmarkProvider();

            instance.database.getUserDao().getIdsAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Long>>()
                {
                    @Override
                    public void accept(List<Long> notes) throws Exception
                    {
                        instance.knownUserIds = notes;
                    }
                });
        }
        return instance;
    }

    public boolean isKnownUser(IUser user)  { return isKnownUser(user.getId()); }
    public boolean isKnownUser(long userId) { return knownUserIds.contains(userId); }

    public Completable rememberUser(final IUser user, final List<IRepository> repos)
    {
        return Completable
            .fromAction(() ->
            {
                database.getUserDao().insert(new DbUser(user));

                List<DbRepository> dr = new ArrayList<>();
                for (IRepository r : repos)
                    dr.add(new DbRepository(r, user.getLogin()));

                database.getRepositoryDao().insert(dr);
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io());
    }

    public Completable removeUser(final IUser user) { return removeUser(user.getId()); }
    public Completable removeUser(final long userId)
    {
        return Completable
            .fromAction(() ->
            {
                database.getUserDao().delete(userId);
                knownUserIds.remove(userId);
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io());
    }
}
