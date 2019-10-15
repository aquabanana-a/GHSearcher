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
    private Object knownUserIdsLo = new Object();

    public List<Long> getKnownUserIds() { synchronized (knownUserIdsLo) { return new ArrayList<>(knownUserIds); }}

    public DbBookmarkProvider()
    {
        Injector.getSingletonComponent()
            .inject(this);

        database.getUserDao().getIdsAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<List<Long>>()
            {
                @Override
                public void accept(List<Long> notes) throws Exception
                {
                    synchronized (knownUserIdsLo)
                    {
                        knownUserIds = notes;
                    }
                }
            });
    }

    public boolean isKnownUser(IUser user)  { return isKnownUser(user.getId()); }
    public boolean isKnownUser(long userId) { synchronized (knownUserIdsLo) { return knownUserIds.contains(userId); } }

    public Completable rememberUser(final IUser user, final List<IRepository> repos)
    {
        return Completable
            .fromAction(() ->
            {
                synchronized (knownUserIdsLo)
                {
                    List<DbRepository> dr = new ArrayList<>();
                    for (IRepository r : repos)
                        dr.add(new DbRepository(r, user.getLogin()));

                    database.getUserDao().insert(new DbUser(user));
                    database.getRepositoryDao().insert(dr);
                    knownUserIds.add(user.getId());
                }
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
                synchronized (knownUserIdsLo)
                {
                    database.getUserDao().delete(userId);
                    knownUserIds.remove(userId);
                }
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io());
    }
}
