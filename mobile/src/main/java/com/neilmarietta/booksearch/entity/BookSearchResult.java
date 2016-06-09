package com.neilmarietta.booksearch.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookSearchResult {

    @SerializedName("kind")
    private String kind;

    @SerializedName("totalItems")
    private int totalItems;

    @SerializedName("items")
    private List<Book> items;

    public String getKind() {
        return kind;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public List<Book> getItems() {
        return items;
    }
}
