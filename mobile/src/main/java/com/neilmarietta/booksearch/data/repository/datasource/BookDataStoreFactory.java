package com.neilmarietta.booksearch.data.repository.datasource;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class BookDataStoreFactory {

    private final Retrofit mAdapter;

    @Inject
    public BookDataStoreFactory(Retrofit adapter) {
        mAdapter = adapter;
    }

    public BookDataStore create() {
        return createCloudBookDataStore();
    }

    public BookDataStore create(String volumeId) {
        // We could return a cachedBookDataStore
        return createCloudBookDataStore();
    }

    private BookDataStore createCloudBookDataStore() {
        return new CloudBookDataStore(mAdapter);
    }
}
