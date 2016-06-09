package com.neilmarietta.booksearch.presentation;

public interface MvpLoadView extends MvpView {

    void showLoading();

    void hideLoading();

    void showError(String message);
}
