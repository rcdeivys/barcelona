package com.BarcelonaSC.BarcelonaApp.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Erick on 01/11/2017.
 */

public class SCalendarData {

    @SerializedName("idpartido")
    @Expose
    private Integer idpartido;
    @SerializedName("estado")
    @Expose
    private String estado;
    @SerializedName("equipo_1")
    @Expose
    private String equipo1;
    @SerializedName("bandera_1")
    @Expose
    private String bandera1;
    @SerializedName("goles_1")
    @Expose
    private Integer goles1;
    @SerializedName("equipo_2")
    @Expose
    private String equipo2;
    @SerializedName("bandera_2")
    @Expose
    private String bandera2;
    @SerializedName("goles_2")
    @Expose
    private Integer goles2;
    @SerializedName("fecha")
    @Expose
    private String fecha;
    @SerializedName("fecha_etapa")
    @Expose
    private String fechaEtapa;
    @SerializedName("estadio")
    @Expose
    private String estadio;
    @SerializedName("noticias")
    @Expose
    private List<News> noticias = null;

    public Integer getIdpartido() {
        return idpartido;
    }

    public void setIdpartido(Integer idpartido) {
        this.idpartido = idpartido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

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

    public Integer getGoles1() {
        return goles1;
    }

    public void setGoles1(Integer goles1) {
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

    public Integer getGoles2() {
        return goles2;
    }

    public void setGoles2(Integer goles2) {
        this.goles2 = goles2;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFechaEtapa() {
        return fechaEtapa;
    }

    public void setFechaEtapa(String fechaEtapa) {
        this.fechaEtapa = fechaEtapa;
    }

    public String getEstadio() {
        return estadio;
    }

    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }

    public List<News> getNoticias() {
        return noticias;
    }

    public void setNoticias(List<News> noticias) {
        this.noticias = noticias;
    }

}
