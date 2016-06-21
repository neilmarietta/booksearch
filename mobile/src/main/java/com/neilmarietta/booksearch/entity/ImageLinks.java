package com.neilmarietta.booksearch.entity;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class ImageLinks implements Parcelable {

    public static ImageLinks create(String smallThumbnail, String thumbnail, String small,
                                    String medium, String large, String extraLarge) {
        return new AutoValue_ImageLinks(smallThumbnail, thumbnail, small, medium, large, extraLarge);
    }

    public static TypeAdapter<ImageLinks> typeAdapter(Gson gson) {
        return new AutoValue_ImageLinks.GsonTypeAdapter(gson);
    }

    @SerializedName("smallThumbnail")
    @Nullable public abstract String smallThumbnail();

    @SerializedName("thumbnail")
    @Nullable public abstract String thumbnail();

    @SerializedName("small")
    @Nullable public abstract String small();

    @SerializedName("medium")
    @Nullable public abstract String medium();

    @SerializedName("large")
    @Nullable public abstract String large();

    @SerializedName("extraLarge")
    @Nullable public abstract String extraLarge();

    public String getSmall() {
        return smallThumbnail() != null ? smallThumbnail() : thumbnail();
    }

    public String getHigh() {
        return extraLarge() != null ? extraLarge() :
                large() != null ? large() :
                        medium() != null ? medium() :
                                small() != null ? small() :
                                        thumbnail() != null ? thumbnail() :
                                                smallThumbnail();
    }
}
