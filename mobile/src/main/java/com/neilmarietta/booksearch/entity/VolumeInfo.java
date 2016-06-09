package com.neilmarietta.booksearch.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VolumeInfo implements Parcelable {

    @SerializedName("title")
    private String title;

    @SerializedName("authors")
    private List<String> authors;

    @SerializedName("publisher")
    private String publisher;

    @SerializedName("publishedDate")
    private String publishedDate;

    @SerializedName("description")
    private String description;

    @SerializedName("pageCount")
    private int pageCount;

    @SerializedName("categories")
    private List<String> categories;

    @SerializedName("averageRating")
    private float averageRating;

    @SerializedName("ratingsCount")
    private int ratingsCount;

    @SerializedName("imageLinks")
    private ImageLinks imageLinks;

    @SerializedName("language")
    private String language;

    @SerializedName("previewLink")
    private String previewLink;

    @SerializedName("infoLink")
    private String infoLink;

    @SerializedName("canonicalVolumeLink")
    private String canonicalVolumeLink;

    public String getTitle() {
        return title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public int getPageCount() {
        return pageCount;
    }

    public List<String> getCategories() {
        return categories;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public int getRatingsCount() {
        return ratingsCount;
    }

    public ImageLinks getImageLinks() {
        return imageLinks;
    }

    public String getLanguage() {
        return language;
    }

    public String getPreviewLink() {
        return previewLink;
    }

    public String getInfoLink() {
        return infoLink;
    }

    public String getCanonicalVolumeLink() {
        return canonicalVolumeLink;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(title);
        out.writeStringList(authors);
        out.writeString(publisher);
        out.writeString(publishedDate);
        out.writeString(description);
        out.writeInt(pageCount);
        out.writeStringList(categories);
        out.writeFloat(averageRating);
        out.writeInt(ratingsCount);
        out.writeParcelable(imageLinks, flags);
        out.writeString(language);
        out.writeString(previewLink);
        out.writeString(infoLink);
        out.writeString(canonicalVolumeLink);
    }

    public static final Parcelable.Creator<VolumeInfo> CREATOR = new Parcelable.Creator<VolumeInfo>() {
        public VolumeInfo createFromParcel(Parcel in) {
            return new VolumeInfo(in);
        }

        public VolumeInfo[] newArray(int size) {
            return new VolumeInfo[size];
        }
    };

    private VolumeInfo(Parcel in) {
        title = in.readString();
        authors = in.createStringArrayList();
        publisher = in.readString();
        publishedDate = in.readString();
        description = in.readString();
        pageCount = in.readInt();
        categories = in.createStringArrayList();
        averageRating = in.readFloat();
        ratingsCount = in.readInt();
        imageLinks = in.readParcelable(ImageLinks.class.getClassLoader());
        language = in.readString();
        previewLink = in.readString();
        infoLink = in.readString();
        canonicalVolumeLink = in.readString();
    }
}
