package com.dobranos.ghsearcher.ui.activity;

import android.content.Context;
import android.graphics.PointF;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.databinding.*;
import androidx.drawerlayout.widget.DrawerLayout;
import com.dobranos.ghsearcher.BR;
import com.dobranos.ghsearcher.R;
import com.dobranos.ghsearcher.utils.DeviceUtil;
import com.dobranos.ghsearcher.utils.ViewUtil;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;

public abstract class ActBase extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    public class ViewData extends BaseObservable
    {
        @Bindable public final ObservableFloat screenWidthDp = new ObservableFloat();
        @Bindable public final ObservableFloat screenHeightDp = new ObservableFloat();

        public ViewData(Context cx)
        {
            PointF ss = DeviceUtil.getScreenSizeInDp(cx);
            screenWidthDp.set(ss.x);
            screenHeightDp.set(ss.y);
        }
    }

    protected abstract int getMainLayoutResId();
    protected int getLoadingResId()         { return -1; }
    protected int getLoadingTxtResId()      { return -1; }
    protected int getToolbarResId()         { return -1; }
    protected int getToolbarViewResId()     { return -1; }
    protected int getDrawerLayoutResId()    { return -1; }
    protected int getAdViewResId()          { return -1; }
    protected int getAdViewCloseBtnResId()  { return -1; }
    protected int getAdBannerType()         { return -1; }
    protected int getNavViewResId()         { return -1; }

    protected View rootView;

    protected View loading;
    protected TextView tvLoading;
    protected Toolbar toolbar;
    protected ViewGroup toolbarView;
    protected DrawerLayout drawerLayout;
    protected NavigationView navigationView;
    protected ViewData viewData;

    private ActionBarDrawerToggle drawerToggle;
    private boolean mToolBarNavigationListenerIsRegistered = false;

    public View getRootView() { return rootView; }

    protected void onCreateBase()
    {
        //AppMgr.setCurrentContext(this);

        rootView = findViewById(getMainLayoutResId());

        loading = findViewById(getLoadingResId());
        tvLoading = findViewById(getLoadingTxtResId());
        toolbar = (Toolbar) findViewById(getToolbarResId());
        toolbarView = findViewById(getToolbarViewResId());
        drawerLayout = (DrawerLayout) findViewById(getDrawerLayoutResId());
        navigationView = (NavigationView) findViewById(getNavViewResId());

        if (toolbar != null)
        {
            setSupportActionBar(toolbar);

            toolbar.setNavigationOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    onBackPressed();
                }
            });
        }

        if (toolbar != null && drawerLayout != null)
        {
            drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
            drawerLayout.addDrawerListener(drawerToggle);
            drawerToggle.syncState();
        }

        if (navigationView != null)
            navigationView.setNavigationItemSelectedListener(this);
    }

    public void onCreateBinding(ViewDataBinding binder)
    {
        if (binder != null)
            binder.setVariable(BR.vd, viewData = new ViewData(this));
    }

    @Override
    public void setContentView(int layoutResID)
    {
        super.setContentView(layoutResID);
        onCreateBase();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onNavigationItemSelected(MenuItem item)
    {
        if (drawerLayout != null)
            drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setDrawerLocked(boolean value)
    {
        if (drawerLayout == null)
            return;

        drawerLayout.setDrawerLockMode(value ? DrawerLayout.LOCK_MODE_LOCKED_CLOSED : DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.LEFT);
    }

    public void showDrawer(int gravity/*Gravity.RIGHT*/)
    {
        drawerLayout.openDrawer(gravity);
    }

    public void hideDrawer()
    {
        drawerLayout.closeDrawers();
    }

    public void setBackArrowInsteadHamburger(boolean enable)
    {
        // To keep states of ActionBar and ActionBarDrawerToggle synchronized,
        // when you enable on one, you disable on the other.
        // And as you may notice, the order for this operation is disable first, then enable - VERY VERY IMPORTANT.
        if (enable)
        {
            //You may not want to open the drawer on swipe from the left in this case
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            // Remove hamburger
            drawerToggle.setDrawerIndicatorEnabled(false);
            // Show back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // when DrawerToggle is disabled i.e. setDrawerIndicatorEnabled(false), navigation icon
            // clicks are disabled i.e. the UP button will not work.
            // We need to add a listener, as in below, so DrawerToggle will forward
            // click events to this listener.
            if (!mToolBarNavigationListenerIsRegistered)
            {
                drawerToggle.setToolbarNavigationClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        // Doesn't have to be onBackPressed
                        onBackPressed();
                    }
                });

                mToolBarNavigationListenerIsRegistered = true;
            }

        }
        else
        {
            //You must regain the power of swipe for the drawer.
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

            // Remove back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            // Show hamburger
            drawerToggle.setDrawerIndicatorEnabled(true);
            // Remove the/any drawer toggle listener
            drawerToggle.setToolbarNavigationClickListener(null);
            mToolBarNavigationListenerIsRegistered = false;
        }

        // So, one may think "Hmm why not simplify to:
        // .....
        // getSupportActionBar().setDisplayHomeAsUpEnabled(enable);
        // mDrawer.setDrawerIndicatorEnabled(!enable);
        // ......
        // To re-iterate, the order in which you enable and disable views IS important #dontSimplify.
    }
}
