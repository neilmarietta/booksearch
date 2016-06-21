package com.neilmarietta.booksearch.entity;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Book implements Parcelable {

    public static Book create(String kind, String id, String etag, String selfLink, VolumeInfo volumeInfo) {
        return new AutoValue_Book(kind, id, etag, selfLink, volumeInfo);
    }

    public static TypeAdapter<Book> typeAdapter(Gson gson) {
        return new AutoValue_Book.GsonTypeAdapter(gson);
    }

    @SerializedName("kind")
    @Nullable public abstract String kind();

    @SerializedName("id")
    @Nullable public abstract String id();

    @SerializedName("etag")
    @Nullable public abstract String etag();

    @SerializedName("selfLink")
    @Nullable public abstract String selfLink();

    @SerializedName("volumeInfo")
    @Nullable public abstract VolumeInfo volumeInfo();
}
