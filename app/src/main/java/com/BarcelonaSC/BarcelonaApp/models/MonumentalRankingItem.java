package com.BarcelonaSC.BarcelonaApp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Leonardojpr on 10/19/17.
 */

public class MonumentalRankingItem {

    @SerializedName("idmonumental")
    @Expose
    private String idMonumental;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("miniatura")
    @Expose
    private String miniatura;
    @SerializedName("total_votos")
    @Expose
    private Integer totalVotos;
    @SerializedName("porcentaje")
    @Expose
    private Double porcentaje;

    public String getIdMonumental() {
        return idMonumental;
    }

    public void setIdMonumental(String idMonumental) {
        this.idMonumental = idMonumental;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMiniatura() {
        return miniatura;
    }

    public void setMiniatura(String miniatura) {
        this.miniatura = miniatura;
    }

    public Integer getTotalVotos() {
        return totalVotos;
    }

    public void setTotalVotos(Integer totalVotos) {
        this.totalVotos = totalVotos;
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }

}