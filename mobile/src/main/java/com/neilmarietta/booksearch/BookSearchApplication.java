package com.neilmarietta.booksearch;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.neilmarietta.booksearch.internal.di.component.ApiConnectionComponent;
import com.neilmarietta.booksearch.internal.di.component.ApplicationComponent;
import com.neilmarietta.booksearch.internal.di.component.DaggerApiConnectionComponent;
import com.neilmarietta.booksearch.internal.di.component.DaggerApplicationComponent;
import com.neilmarietta.booksearch.internal.di.module.ApplicationModule;

public class BookSearchApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .apiConnectionComponent(DaggerApiConnectionComponent.create())
                .build();

        Fresco.initialize(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public static BookSearchApplication from(@NonNull Context context) {
        return (BookSearchApplication) context.getApplicationContext();
    }
}
