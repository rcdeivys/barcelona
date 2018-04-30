package com.BarcelonaSC.BarcelonaApp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by makhnnar on 30/04/18.
 */

public class SuscripcionData implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("costo_menor")
    @Expose
    private Integer costoMenor;
    @SerializedName("costo_mayor")
    @Expose
    private Integer costoMayor;
    @SerializedName("duracion")
    @Expose
    private Integer duracion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCostoMenor() {
        return costoMenor;
    }

    public void setCostoMenor(Integer costoMenor) {
        this.costoMenor = costoMenor;
    }

    public Integer getCostoMayor() {
        return costoMayor;
    }

    public void setCostoMayor(Integer costoMayor) {
        this.costoMayor = costoMayor;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }
}
