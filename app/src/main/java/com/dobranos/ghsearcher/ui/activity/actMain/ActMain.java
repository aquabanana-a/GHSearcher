package com.dobranos.ghsearcher.ui.activity.actMain;

import android.view.Menu;
import android.view.MenuItem;
import android.os.Bundle;
import android.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.dobranos.ghsearcher.R;
import com.dobranos.ghsearcher.di.Injector;
import com.dobranos.ghsearcher.di.component.DiActMainComponent;
import com.dobranos.ghsearcher.di.module.DiActMainModule;
import com.dobranos.ghsearcher.model.logic.appearance.SwitchScreenMgr;
import com.dobranos.ghsearcher.model.logic.db.DbServiceProvider;
import com.dobranos.ghsearcher.model.logic.gitHub.GitHubServiceProvider;
import com.dobranos.ghsearcher.ui.activity.ActBase;
import com.dobranos.ghsearcher.ui.fragment.fgSearchResults.FgSearchResults;
import com.dobranos.ghsearcher.ui.fragment.fgUser.FgUser;

import javax.inject.Inject;
import java.util.List;

public class ActMain extends ActBase implements FgSearchResults.InjectorProvider, FgUser.InjectorProvider
{
    @Inject GitHubServiceProvider networkProvider;
    @Inject DbServiceProvider storageProvider;

    private DiActMainModule dim;
    private DiActMainComponent dic = Injector.getSingletonComponent().newComponent(dim = new DiActMainModule());

    @Override protected int getMainLayoutResId()    { return R.id.act_main; }
    @Override protected int getLoadingResId()       { return R.id.v_loading; }
    @Override protected int getLoadingTxtResId()    { return R.id.tv_loading; }
    @Override protected int getToolbarResId()       { return R.id.toolbar; }
    @Override protected int getToolbarViewResId()   { return R.id.toolbar_view; }
    @Override protected int getDrawerLayoutResId()  { return R.id.act_main; }
    @Override protected int getNavViewResId()       { return R.id.nav_rootView; }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
//        Injector.getSingletonComponent()
//            .inject(this);

        super.onCreate(savedInstanceState);
        super.onCreateBinding(DataBindingUtil.setContentView(this, R.layout.act_main));
        setTitle("");

        dim.registerToolbar(toolbar)
            .registerVgToolbar(toolbarView)
            .registerDrawerLayout(drawerLayout)
            .registerLoading(loading);

        if(savedInstanceState == null)
            SwitchScreenMgr.setFragment(this, new FgSearchResults());

        getSupportFragmentManager().addOnBackStackChangedListener(() ->
        {
            FragmentManager manager = getSupportFragmentManager();
            if (manager == null)
                return;

            List<Fragment> fs = manager.getFragments();
            for (int i=0; i<fs.size(); i++)
                fs.get(i).onResume();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.mu_toolbar, menu);

        dim.registerMiSearch(menu.findItem(R.id.menu_search))
            .registerMiBookmarks(menu.findItem(R.id.menu_bookmarks));

        final SearchView sv = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(final String query)
            {
                SwitchScreenMgr.setFragment(ActMain.this, new FgSearchResults()
                    .withQuery(query)
                    .withServiceProvider(networkProvider));

                sv.setQuery("", false);
                sv.setIconified(true);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_bookmarks:
                SwitchScreenMgr.switchFragment(ActMain.this, new FgSearchResults()
                    .withQuery("*")
                    .withServiceProvider(storageProvider)
                    .withMarkAsBookmarks());
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void inject(FgSearchResults fragment)
    {
        dic.inject(this);
        dic.inject(fragment);
    }

    @Override
    public void inject(FgUser fragment)
    {
        dic.inject(this);
        dic.inject(fragment);
    }
}
