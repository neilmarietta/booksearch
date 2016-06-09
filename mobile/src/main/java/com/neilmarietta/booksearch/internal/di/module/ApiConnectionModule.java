package com.neilmarietta.booksearch.internal.di.module;

import com.neilmarietta.booksearch.data.repository.net.ApiConnection;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

@Module
public class ApiConnectionModule {

    @Provides
    @Singleton
    Retrofit provideRestAdapter(OkHttpClient okHttpClient) {
        return ApiConnection.createAdapter(okHttpClient);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor) {
        return ApiConnection.createOkHttpClient(httpLoggingInterceptor);
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return ApiConnection.createHttpLoggingInterceptor();
    }
}
