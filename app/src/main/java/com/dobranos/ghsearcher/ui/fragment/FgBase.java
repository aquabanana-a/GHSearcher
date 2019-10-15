package com.dobranos.ghsearcher.ui.fragment;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.dobranos.ghsearcher.R;
import com.dobranos.ghsearcher.model.logic.appearance.SwitchScreenMgr;
import com.dobranos.ghsearcher.ui.activity.ActBase;
import com.google.android.material.snackbar.Snackbar;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.io.IOException;
import java.net.SocketTimeoutException;

public abstract class FgBase extends Fragment
{
    protected FragmentActivity context;

    public abstract View onCreateViewImpl(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        context = getActivity();
        return onCreateViewImpl(inflater, container, savedInstanceState);
    }

    public void setBackArrowInsteadHamburger(boolean value)
    {
        if(context instanceof ActBase)
            ((ActBase) context).setBackArrowInsteadHamburger(value);
    }

    public void handleError(Throwable e)
    {
        Resources r = context.getResources();
        String msg = "";

        if (e instanceof HttpException)
        {
            HttpException he = (HttpException) e;
            switch (he.code())
            {
                case 403:
                    msg = r.getString(R.string.error_http_forbidden);
                    break;
                default:
                    msg = r.getString(R.string.error_http_common);
                    break;
            }
        }
        else if (e instanceof SocketTimeoutException)
        {
            msg = r.getString(R.string.error_time_out);
        }
        else if (e instanceof IOException)
        {
            msg = r.getString(R.string.error_network);
        }
        else
            msg = r.getString(R.string.error_unknown);

        Snackbar.make(((ActBase) context).getRootView(), msg, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.common_ok, view ->
            {
                SwitchScreenMgr.closeCurrentFragment(context);
            })
            .show();
    }
}