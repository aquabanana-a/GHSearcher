package com.dobranos.ghsearcher.model.logic.appearance;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.dobranos.ghsearcher.R;
import com.dobranos.ghsearcher.ui.fragment.FgBase;

public class SwitchScreenMgr
{
    public static void switchFragment(FragmentActivity cx, final FgBase fg)
    {
        FragmentManager fm = cx.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.fg_holder, fg);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public static void closeCurrentFragment(FragmentActivity cx)
    {
        FragmentManager fm = cx.getSupportFragmentManager();
        fm.popBackStack();
    }

    public static void setFragment(FragmentActivity cx, FgBase fg)
    {
        FragmentManager fm = cx.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fg_holder, fg);
        fragmentTransaction.commit();
    }
}