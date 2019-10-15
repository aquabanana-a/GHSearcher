package com.dobranos.ghsearcher.model.data.common;

import io.reactivex.Observable;

import java.util.List;

public interface IServiceProvider
{
    Observable<List<IUser>> searchUsers(final String query, final long page, final long pageSize);

    Observable<IUser> getUser(final String login);

    Observable<List<IRepository>> getUserRepositories(final String login);
}