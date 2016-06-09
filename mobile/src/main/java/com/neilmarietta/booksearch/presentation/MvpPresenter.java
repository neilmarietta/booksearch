package com.neilmarietta.booksearch.presentation;

import android.os.Bundle;

public interface MvpPresenter<V extends MvpView> {

    void attachView(V view, Bundle savedInstanceState);

    void detachView();

    void onSaveInstanceState(Bundle outState);
}