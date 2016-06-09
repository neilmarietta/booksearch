package com.neilmarietta.booksearch.internal.di.component;

import android.app.Application;

import com.neilmarietta.booksearch.internal.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    Application application();
}
