package com.neilmarietta.booksearch.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ImageLinks implements Parcelable {

    @SerializedName("smallThumbnail")
    private String smallThumbnail;

    @SerializedName("thumbnail")
    private String thumbnail;

    @SerializedName("small")
    private String small;

    @SerializedName("medium")
    private String medium;

    @SerializedName("large")
    private String large;

    @SerializedName("extraLarge")
    private String extraLarge;

    public String getSmall() {
        return smallThumbnail != null ? smallThumbnail : thumbnail;
    }

    public String getHigh() {
        return extraLarge != null ? extraLarge :
                large != null ? large :
                        medium != null ? medium :
                                small != null ? small :
                                        thumbnail != null ? thumbnail :
                                                smallThumbnail;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(smallThumbnail);
        out.writeString(thumbnail);
        out.writeString(small);
        out.writeString(medium);
        out.writeString(large);
        out.writeString(extraLarge);
    }

    public static final Parcelable.Creator<ImageLinks> CREATOR = new Parcelable.Creator<ImageLinks>() {
        public ImageLinks createFromParcel(Parcel in) {
            return new ImageLinks(in);
        }

        public ImageLinks[] newArray(int size) {
            return new ImageLinks[size];
        }
    };

    private ImageLinks(Parcel in) {
        smallThumbnail = in.readString();
        thumbnail = in.readString();
        small = in.readString();
        medium = in.readString();
        large = in.readString();
        extraLarge = in.readString();
    }
}
