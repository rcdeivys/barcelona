package com.BarcelonaSC.BarcelonaApp.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Deivys on 3/29/2018.
 */

public class MultimediaDataResponse {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("titulo")
    @Expose
    private String titulo;
    @SerializedName("foto")
    @Expose
    private String foto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

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
    public String toString() {
        return "MultimediaDataResponse{" +
                "id=" + id +
                ", link='" + link + '\'' +
                ", titulo='" + titulo + '\'' +
                '}';
    }
}
