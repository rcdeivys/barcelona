package com.BarcelonaSC.BarcelonaApp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Carlos on 07/11/2017.
 */

public class VideoReality implements Serializable {

    @SerializedName("titulo")
    @Expose
    private String titulo;

    @SerializedName("descripcion")
    @Expose
    private String descripcion;

    @SerializedName("foto")
    @Expose
    private String foto;

    @SerializedName("video")
    @Expose
    private String url;

    @SerializedName("dorado")
    @Expose
    private int dorado;

    @SerializedName("id")
    @Expose
    private String id = null;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isDorado() {
        return dorado == 1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "VideoReality{" +
                "titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", foto='" + foto + '\'' +
                ", url='" + url + '\'' +
                ", dorado=" + dorado +
                ", id='" + id + '\'' +
                '}';
    }
}

