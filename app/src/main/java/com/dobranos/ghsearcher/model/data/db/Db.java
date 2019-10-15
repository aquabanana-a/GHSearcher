package com.dobranos.ghsearcher.model.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.dobranos.ghsearcher.model.data.db.dao.DbRepositoryDao;
import com.dobranos.ghsearcher.model.data.db.dao.DbUserDao;
import com.dobranos.ghsearcher.model.data.db.entity.DbRepository;
import com.dobranos.ghsearcher.model.data.db.entity.DbUser;

@Database(entities = {DbUser.class, DbRepository.class}, exportSchema = true, version = 1)
public abstract class Db extends RoomDatabase
{
    public abstract DbUserDao getUserDao();
    public abstract DbRepositoryDao getRepositoryDao();
}