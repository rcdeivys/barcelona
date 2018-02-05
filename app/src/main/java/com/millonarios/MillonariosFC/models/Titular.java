package com.millonarios.MillonariosFC.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Carlos on 14/11/2017.
 */

public class Titular {

    @SerializedName("idjugador")
    @Expose
    private String idjugador;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("posicion_campo")
    @Expose
    private String posicionCampo;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("actividades")
    @Expose

    private int view_id;

    public String getIdjugador() {
        return idjugador;
    }

    public void setIdjugador(String idjugador) {
        this.idjugador = idjugador;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getPosicionCampo() {
        return posicionCampo;
    }

    public void setPosicionCampo(String posicionCampo) {
        this.posicionCampo = posicionCampo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getView_id() {
        return view_id;
    }

    public void setView_id(int view_id) {
        this.view_id = view_id;
    }
}
