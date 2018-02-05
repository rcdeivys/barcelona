package com.millonarios.MillonariosFC.ui.gallery;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Leonardojpr on 10/12/17.
 */

public class GalleryModel implements Parcelable {

    private String img;

    public GalleryModel(String img) {
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.img);
    }

    protected GalleryModel(Parcel in) {
        this.img = in.readString();
    }

    public static final Creator<GalleryModel> CREATOR = new Creator<GalleryModel>() {
        @Override
        public GalleryModel createFromParcel(Parcel source) {
            return new GalleryModel(source);
        }

        @Override
        public GalleryModel[] newArray(int size) {
            return new GalleryModel[size];
        }
    };
}
