package com.BarcelonaSC.BarcelonaApp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ranking {

    @SerializedName("jugadores")
    @Expose
    private List<JugadorData> jugadores = null;
    @SerializedName("votos")
    @Expose
    private Integer votos;
    @SerializedName("porcentaje")
    @Expose
    private Double porcentaje;

    public List<JugadorData> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<JugadorData> jugadores) {
        this.jugadores = jugadores;
    }

    public Integer getVotos() {
        return votos;
    }

    public void setVotos(Integer votos) {
        this.votos = votos;
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }

}