package com.BarcelonaSC.BarcelonaApp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Erick on 14/02/2018.
 */

public class MonumentalData {

    @SerializedName("idmonumental")
    @Expose
    private Integer idmonumental;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("banner")
    @Expose
    private String banner;

    public Integer getIdmonumental() {
        return idmonumental;
    }

    public void setIdmonumental(Integer idmonumental) {
        this.idmonumental = idmonumental;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

}