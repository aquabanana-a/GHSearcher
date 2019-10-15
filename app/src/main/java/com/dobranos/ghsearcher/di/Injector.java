package com.dobranos.ghsearcher.di;

import android.content.Context;
import androidx.annotation.NonNull;
import com.dobranos.ghsearcher.di.component.DaggerSingletonComponent;
import com.dobranos.ghsearcher.di.component.SingletonComponent;
import com.dobranos.ghsearcher.di.module.DiAppModule;

public final class Injector
{
    private static final Injector INSTANCE = new Injector();

    private SingletonComponent singletonComponent;
    private Injector() { }

    public static void initialize(@NonNull Context cx)
    {
        INSTANCE.singletonComponent = DaggerSingletonComponent.builder()
            .diAppModule(new DiAppModule(cx))
            //.singletonModule(new SingletonModule())
            .build();
    }

    public static SingletonComponent getSingletonComponent()
    {
        return INSTANCE.singletonComponent;
    }
}