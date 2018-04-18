package com.BarcelonaSC.BarcelonaApp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Carlos on 12/10/2017.
 */

public class NominaItem {
    @SerializedName("idjugador")
    @Expose
    private int idJugador;
    @SerializedName("banner")
    @Expose
    private String banner;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("nombre")
    @Expose
    private String nombre;

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idjugador) {
        this.idJugador = idjugador;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "NominaItem{" +
                "idJugador=" + idJugador +
                ", banner='" + banner + '\'' +
                ", foto='" + foto + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
