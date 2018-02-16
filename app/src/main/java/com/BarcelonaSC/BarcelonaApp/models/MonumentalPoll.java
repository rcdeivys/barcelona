package com.BarcelonaSC.BarcelonaApp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Erick on 20/10/2017.
 */

public class MonumentalPoll {

    @SerializedName("idencuesta")
    @Expose
    private Integer idencuesta;
    @SerializedName("titulo")
    @Expose
    private String titulo;
    @SerializedName("fecha_fin")
    @Expose
    private String fecha_fin;
    @SerializedName("total_votos")
    @Expose
    private String total_votos;

    @SerializedName("monumentales")
    @Expose
    private List<MonumentalData> monumentales;

    public Integer getIdencuesta() {
        return idencuesta;
    }

    public void setIdencuesta(Integer idencuesta) {
        this.idencuesta = idencuesta;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public String getTotal_votos() {
        return total_votos;
    }

    public void setTotal_votos(String total_votos) {
        this.total_votos = total_votos;
    }

    public List<MonumentalData> getMonumentales() {
        return monumentales;
    }

    public void setMonumentales(List<MonumentalData> monumentales) {
        this.monumentales = monumentales;
    }

}