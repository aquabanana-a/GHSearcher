package com.dobranos.ghsearcher.model.data.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.dobranos.ghsearcher.model.data.db.entity.DbRepository;
import io.reactivex.Observable;
import io.reactivex.Single;

import java.util.List;

import static androidx.room.OnConflictStrategy.IGNORE;

@Dao
public interface DbRepositoryDao
{
    @Query("SELECT * FROM repositories")
    List<DbRepository> getAll();

    @Query("SELECT * FROM repositories WHERE owner_user_login = :userLogin")
    Single<List<DbRepository>> getByUserLogin(String userLogin);

    @Insert(onConflict = IGNORE)
    long insert(DbRepository repo);

    @Insert(onConflict = IGNORE)
    List<Long> insert(List<DbRepository> repos);

    @Delete
    void delete(DbRepository repo);
}