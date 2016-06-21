package com.neilmarietta.booksearch.entity;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@AutoValue
public abstract class BookSearchResult {

    public static BookSearchResult create(String kind, int totalItems, List<Book> items) {
        return new AutoValue_BookSearchResult(kind, totalItems, items);
    }

    public static TypeAdapter<BookSearchResult> typeAdapter(Gson gson) {
        return new AutoValue_BookSearchResult.GsonTypeAdapter(gson);
    }

    @SerializedName("kind")
    public abstract String kind();

    @SerializedName("totalItems")
    public abstract int totalItems();

    @SerializedName("items")
    public abstract List<Book> items();
}
