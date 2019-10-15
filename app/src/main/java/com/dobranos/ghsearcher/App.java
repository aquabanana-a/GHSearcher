package com.dobranos.ghsearcher;

import android.app.Application;
import com.dobranos.ghsearcher.di.Injector;
import com.facebook.drawee.backends.pipeline.Fresco;

import javax.inject.Inject;

public class App extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        Injector.initialize(this);
        Fresco.initialize(this);
    }
}
