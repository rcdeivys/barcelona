package com.millonarios.MillonariosFC.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Carlos on 12/01/2018.
 */

public class EncuestaData {

    @SerializedName("idencuesta")
    @Expose
    private Integer idencuesta;
    @SerializedName("titulo")
    @Expose
    private String titulo;
    @SerializedName("fecha_inicio")
    @Expose
    private String fechaInicio;
    @SerializedName("fecha_fin")
    @Expose
    private String fechaFin;
    @SerializedName("puedevotar")
    @Expose
    private Integer puedevotar;
    @SerializedName("puedevervotos")
    @Expose
    private Integer puedevervotos;
    @SerializedName("respuestas")
    @Expose
    private List<RespuestaData> respuestas = null;

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

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getPuedevotar() {
        return puedevotar;
    }

    public void setPuedevotar(Integer puedevotar) {
        this.puedevotar = puedevotar;
    }

    public Integer getPuedevervotos() {
        return puedevervotos;
    }

    public void setPuedevervotos(Integer puedevervotos) {
        this.puedevervotos = puedevervotos;
    }

    public List<RespuestaData> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<RespuestaData> respuestas) {
        this.respuestas = respuestas;
    }

}