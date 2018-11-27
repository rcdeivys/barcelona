package com.BarcelonaSC.BarcelonaApp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JugadorData {

    @SerializedName("jugador_id")
    @Expose
    private Integer jugadorId;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("comun")
    @Expose
    private Boolean comun;

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

    public Boolean getComun() {
        return comun;
    }

    public void setComun(Boolean comun) {
        this.comun = comun;
    }

}