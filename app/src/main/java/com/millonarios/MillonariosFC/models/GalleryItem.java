package com.millonarios.MillonariosFC.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Leonardojpr on 10/16/17.
 */

public class GalleryItem implements Parcelable {
    @SerializedName("titulo")
    @Expose
    private String titulo;
    @SerializedName("foto")
    @Expose
    private String foto;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.titulo);
        dest.writeString(this.foto);
    }

    public GalleryItem(String titulo, String foto) {
        this.titulo = titulo;
        this.foto = foto;
    }

    protected GalleryItem(Parcel in) {
        this.titulo = in.readString();
        this.foto = in.readString();
    }

    public static final Creator<GalleryItem> CREATOR = new Creator<GalleryItem>() {
        @Override
        public GalleryItem createFromParcel(Parcel source) {
            return new GalleryItem(source);
        }

        @Override
        public GalleryItem[] newArray(int size) {
            return new GalleryItem[size];
        }
    };
}
