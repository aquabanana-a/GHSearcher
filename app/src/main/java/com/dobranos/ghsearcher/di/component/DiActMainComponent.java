package com.dobranos.ghsearcher.di.component;

import com.dobranos.ghsearcher.di.module.DiActMainModule;
import com.dobranos.ghsearcher.di.scope.ActivityScope;
import com.dobranos.ghsearcher.ui.activity.actMain.ActMain;
import com.dobranos.ghsearcher.ui.fragment.fgSearchResults.FgSearchResults;
import com.dobranos.ghsearcher.ui.fragment.fgUser.FgUser;
import dagger.Component;
import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {DiActMainModule.class})
public interface DiActMainComponent
{
    void inject(ActMain activity);
    void inject(FgSearchResults fragment);
    void inject(FgUser fragment);
}