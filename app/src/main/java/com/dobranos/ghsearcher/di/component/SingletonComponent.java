package com.dobranos.ghsearcher.di.component;

import com.dobranos.ghsearcher.di.module.DiActMainModule;
import com.dobranos.ghsearcher.di.module.DiAppModule;
import com.dobranos.ghsearcher.model.logic.db.DbBookmarkProvider;
import com.dobranos.ghsearcher.model.logic.db.DbServiceProvider;
import com.dobranos.ghsearcher.ui.activity.actMain.ActMain;
import com.dobranos.ghsearcher.ui.fragment.fgSearchResults.FgSearchResults;
import com.dobranos.ghsearcher.ui.fragment.fgUser.FgUser;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {/*SingletonModule.class, */DiAppModule.class})
public interface SingletonComponent
{
    DiActMainComponent newComponent(DiActMainModule a);

    void inject(DbServiceProvider value);
    void inject(DbBookmarkProvider value);

    void inject(ActMain value);
//    void inject(FgSearchResults value);
//    void inject(FgUser value);
}