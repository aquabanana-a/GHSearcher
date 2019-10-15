package com.dobranos.ghsearcher.di.module;

import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import com.dobranos.ghsearcher.di.scope.ActivityScope;
import com.dobranos.ghsearcher.utils.ViewUtil;
import com.google.android.material.appbar.AppBarLayout;
import dagger.Module;
import dagger.Provides;

@Module
public class DiActMainModule
{
    public Menu menu = new Menu();
    public class Menu
    {
        public MenuItem miSearch;
        public MenuItem miBookmarks;
    }
    public DiActMainModule registerMiSearch(MenuItem value)         { menu.miSearch = value; return this; }
    public DiActMainModule registerMiBookmarks(MenuItem value)      { menu.miBookmarks = value; return this; }
    @ActivityScope @Provides Menu provideMenu()                     { return menu; }

    public Behavior behavior = new Behavior();
    public class Behavior
    {
        public Toolbar toolbar;
        public ViewGroup vgToolbar;

        public View vLoading;
        public DrawerLayout drawerLayout;

        private Boolean scrollingBehavior;
        public void setScrollingBehavior(boolean value)
        {
            if (scrollingBehavior != null && scrollingBehavior == value || toolbar == null || vgToolbar == null || !(vgToolbar instanceof AppBarLayout))
                return;

            scrollingBehavior = value;

            AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
            CoordinatorLayout.LayoutParams appBarLayoutParams = (CoordinatorLayout.LayoutParams) vgToolbar.getLayoutParams();
            if (value)
            {
                params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
                appBarLayoutParams.setBehavior(new AppBarLayout.Behavior());
                vgToolbar.setLayoutParams(appBarLayoutParams);
            }
            else
            {
                params.setScrollFlags(0);
                appBarLayoutParams.setBehavior(null);
                vgToolbar.setLayoutParams(appBarLayoutParams);
            }
        }

        private void setDrawerLocked(boolean value) { if (drawerLayout == null) return; drawerLayout.setDrawerLockMode(value ? DrawerLayout.LOCK_MODE_LOCKED_CLOSED : DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.LEFT); }
        public void showLoading(boolean value)
        {
            if (vLoading == null)
                return;

            setDrawerLocked(value);
            if(value)
                ViewUtil.toVisible(vLoading);
            else
                ViewUtil.toGone(vLoading);
        }
    }
    public DiActMainModule registerToolbar(Toolbar value)               { behavior.toolbar = value; return this; }
    public DiActMainModule registerVgToolbar(ViewGroup value)           { behavior.vgToolbar = value; return this; }
    public DiActMainModule registerLoading(View value)                  { behavior.vLoading = value; return this; }
    public DiActMainModule registerDrawerLayout(DrawerLayout value)     { behavior.drawerLayout = value; return this; }
    @ActivityScope @Provides Behavior provideBehavior()                 { return behavior; }
}