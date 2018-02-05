package com.millonarios.MillonariosFC.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Carlos on 06/12/2017.
 */

public class BannerData {

    @SerializedName("seccion")
    @Expose
    private String seccion;
    @SerializedName("target")
    @Expose
    private String target;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("seccion_destino")
    @Expose
    private String seccionDestino;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("titulo")
    @Expose
    private String titulo;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getSeccionDestino() {
        return seccionDestino;
    }

    public void setSeccionDestino(String seccionDestino) {
        this.seccionDestino = seccionDestino;
    }
}