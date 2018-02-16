package com.BarcelonaSC.BarcelonaApp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Carlos on 14/11/2017.
 */

public class CalendarData {

    @SerializedName("equipo1")
    @Expose
    private String equipo1;
    @SerializedName("bandera1")
    @Expose
    private String bandera1;
    @SerializedName("goles1")
    @Expose
    private String goles1;
    @SerializedName("equipo2")
    @Expose
    private String equipo2;
    @SerializedName("bandera2")
    @Expose
    private String bandera2;
    @SerializedName("goles2")
    @Expose
    private String goles2;
    @SerializedName("posicion")
    @Expose
    private String posicion;
    @SerializedName("idcalendario")
    @Expose
    private String idcalendario;
    @SerializedName("tipo")
    @Expose
    private String tipo;
    @SerializedName("fecha")
    @Expose
    private String fecha;
    @SerializedName("fecha_fifa")
    @Expose
    private String fechaFifa;
    @SerializedName("estatus")
    @Expose
    private String estatus;
    @SerializedName("destacado")
    @Expose
    private String destacado;

    public String getEquipo1() {
        return equipo1;
    }

    public void setEquipo1(String equipo1) {
        this.equipo1 = equipo1;
    }

    public String getBandera1() {
        return bandera1;
    }

    public void setBandera1(String bandera1) {
        this.bandera1 = bandera1;
    }

    public String getGoles1() {
        return goles1;
    }

    public void setGoles1(String goles1) {
        this.goles1 = goles1;
    }

    public String getEquipo2() {
        return equipo2;
    }

    public void setEquipo2(String equipo2) {
        this.equipo2 = equipo2;
    }

    public String getBandera2() {
        return bandera2;
    }

    public void setBandera2(String bandera2) {
        this.bandera2 = bandera2;
    }

    public String getGoles2() {
        return goles2;
    }

    public void setGoles2(String goles2) {
        this.goles2 = goles2;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getIdcalendario() {
        return idcalendario;
    }

    public void setIdcalendario(String idcalendario) {
        this.idcalendario = idcalendario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFechaFifa() {
        return fechaFifa;
    }

    public void setFechaFifa(String fechaFifa) {
        this.fechaFifa = fechaFifa;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getDestacado() {
        return destacado;
    }

    public void setDestacado(String destacado) {
        this.destacado = destacado;
    }

    @Override
    public String toString() {
        return "CalendarData{" +
                "equipo1='" + equipo1 + '\'' +
                ", bandera1='" + bandera1 + '\'' +
                ", goles1='" + goles1 + '\'' +
                ", equipo2='" + equipo2 + '\'' +
                ", bandera2='" + bandera2 + '\'' +
                ", goles2='" + goles2 + '\'' +
                ", posicion='" + posicion + '\'' +
                ", idcalendario='" + idcalendario + '\'' +
                ", tipo='" + tipo + '\'' +
                ", fecha='" + fecha + '\'' +
                ", fechaFifa='" + fechaFifa + '\'' +
                ", estatus='" + estatus + '\'' +
                ", destacado='" + destacado + '\'' +
                '}';
    }
}