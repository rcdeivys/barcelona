package com.BarcelonaSC.BarcelonaApp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Carlos on 06/12/2017.
 */

public class BannerData {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("partido")
    @Expose
    private String partido;
    @SerializedName("partidofb")
    @Expose
    private String partidofb;
    @SerializedName("seccion")
    @Expose
    private String seccion;
    @SerializedName("titulo")
    @Expose
    private String titulo;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPartido() {
        return partido;
    }

    public void setPartido(String partido) {
        this.partido = partido;
    }

    public String getPartidofb() {
        return partidofb;
    }

    public void setPartidofb(String partidofb) {
        this.partidofb = partidofb;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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

    public String getSeccionDestino() {
        return seccionDestino;
    }

    public void setSeccionDestino(String seccionDestino) {
        this.seccionDestino = seccionDestino;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "BannerData{" +
                "type='" + type + '\'' +
                ", partido='" + partido + '\'' +
                ", partidofb='" + partidofb + '\'' +
                ", seccion='" + seccion + '\'' +
                ", titulo='" + titulo + '\'' +
                ", target='" + target + '\'' +
                ", url='" + url + '\'' +
                ", seccionDestino='" + seccionDestino + '\'' +
                ", foto='" + foto + '\'' +
                '}';
    }
}