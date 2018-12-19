package com.BarcelonaSC.BarcelonaApp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AlineacionOficialData {

    @SerializedName("jugador_id")
    @Expose
    private Integer jugadorId;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("posicion")
    @Expose
    private String posicion;

    public Integer getJugadorId() {
        return jugadorId;
    }

    public void setJugadorId(Integer jugadorId) {
        this.jugadorId = jugadorId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

}