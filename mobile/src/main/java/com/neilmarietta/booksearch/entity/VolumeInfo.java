package com.neilmarietta.booksearch.entity;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@AutoValue
public abstract class VolumeInfo implements Parcelable {

    public static VolumeInfo create(String title, int pageCount,
                                    float averageRating, int ratingsCount, ImageLinks imageLinks) {
        return new AutoValue_VolumeInfo(title, null, null, null, null, pageCount,
                null, averageRating, ratingsCount, imageLinks, null, null,
                null, null);
    }

    public static VolumeInfo create(String title, List<String> authors, String publisher,
                                    String publisherDate, String description, int pageCount,
                                    List<String> categories, float averageRating, int ratingsCount,
                                    ImageLinks imageLinks, String language, String previewLink,
                                    String infoLink, String canonicalVolumeLink) {
        return new AutoValue_VolumeInfo(title, authors, publisher, publisherDate, description, pageCount,
                categories, averageRating, ratingsCount, imageLinks, language, previewLink,
                infoLink, canonicalVolumeLink);
    }

    public static TypeAdapter<VolumeInfo> typeAdapter(Gson gson) {
        return new AutoValue_VolumeInfo.GsonTypeAdapter(gson);
    }

    @SerializedName("title")
    public abstract String title();

    @SerializedName("authors")
    @Nullable public abstract List<String> authors();

    @SerializedName("publisher")
    @Nullable public abstract String publisher();

    @SerializedName("publishedDate")
    @Nullable public abstract String publishedDate();

    @SerializedName("description")
    @Nullable public abstract String description();

    @SerializedName("pageCount")
    public abstract int pageCount();

    @SerializedName("categories")
    @Nullable public abstract List<String> categories();

    @SerializedName("averageRating")
    public abstract float averageRating();

    @SerializedName("ratingsCount")
    public abstract int ratingsCount();

    @SerializedName("imageLinks")
    @Nullable public abstract ImageLinks imageLinks();

    @SerializedName("language")
    @Nullable public abstract String language();

    @SerializedName("previewLink")
    @Nullable public abstract String previewLink();

    @SerializedName("infoLink")
    @Nullable public abstract String infoLink();

    @SerializedName("canonicalVolumeLink")
    @Nullable public abstract String canonicalVolumeLink();
}
