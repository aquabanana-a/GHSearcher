package com.dobranos.ghsearcher.model.data.db.dao;

import androidx.room.*;
import com.dobranos.ghsearcher.model.data.db.entity.DbUser;
import com.dobranos.ghsearcher.model.data.db.entity.DbUserNote;
import io.reactivex.Flowable;
import io.reactivex.Single;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface DbUserDao
{
    @Query("SELECT * FROM users")
    Single<List<DbUser>> getAll();

    @Query("SELECT * FROM users LIMIT :from, :to")
    Single<List<DbUser>> getAllPaged(long from, long to);

    @Query("SELECT id, login, avatar_url FROM users")
    Single<List<DbUserNote>> getNotesAll();

    @Query("SELECT id FROM users")
    Flowable<List<Long>> getIdsAll();

    @Query("SELECT * FROM users WHERE id = :id")
    Single<DbUser> getById(long id);

    @Query("SELECT * FROM users WHERE login = :login")
    Single<DbUser> getByLogin(String login);

//    @Transaction
//    @Query("SELECT * from users WHERE id = :id")
//    List<DbUserWithRepositories> getByIdWithRepositories(long id);

    @Insert(onConflict = REPLACE)
    long insert(DbUser user);

    @Update
    void update(DbUser user);

    @Delete
    void delete(DbUser user);

    @Query("DELETE FROM users WHERE id = :id")
    void delete(long id);
}