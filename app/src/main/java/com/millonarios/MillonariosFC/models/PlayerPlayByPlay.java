package com.millonarios.MillonariosFC.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Carlos on 14/11/2017.
 */

public class PlayerPlayByPlay {

    @SerializedName("idjugador")
    @Expose
    private Integer idjugador;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("posicion")
    @Expose
    private Integer posicion;
    @SerializedName("actividades")
    @Expose
    private List<PlayerGameActivity> actividades = null;

    public Integer getIdjugador() {
        return idjugador;
    }

    public void setIdjugador(Integer idjugador) {
        this.idjugador = idjugador;
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

    public Integer getPosicion() {
        return posicion;
    }

    public void setPosicion(Integer posicion) {
        this.posicion = posicion;
    }

    public List<PlayerGameActivity> getActividades() {
        return actividades;
    }

    public void setActividades(List<PlayerGameActivity> actividades) {
        this.actividades = actividades;
    }
}