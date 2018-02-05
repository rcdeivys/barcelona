package com.millonarios.MillonariosFC.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Carlos on 12/10/2017.
 */

public class PlayerVote {
    @SerializedName("idjugador")
    @Expose
    private String idjugador;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("votos")
    @Expose
    private String votos;
    @SerializedName("porcentaje")
    @Expose
    private float porcentaje;

    public String getIdjugador() {
        return idjugador;
    }

    public void setIdjugador(String idjugador) {
        this.idjugador = idjugador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getVotos() {
        return votos;
    }

    public void setVotos(String votos) {
        this.votos = votos;
    }

    public float getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(float porcentaje) {
        this.porcentaje = porcentaje;
    }
}
