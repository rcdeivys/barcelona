package com.millonarios.MillonariosFC.models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RYA-Laptop on 30/01/2018.
 */

public class ReferredData {

    @SerializedName("activos")
    @Expose
    private Integer activos;
    @SerializedName("referidos")
    @Expose
    private List<Referido> referidos = null;

    public Integer getActivos() {
        return activos;
    }

    public void setActivos(Integer activos) {
        this.activos = activos;
    }

    public List<Referido> getReferidos() {
        return referidos;
    }

    public void setReferidos(List<Referido> referidos) {
        this.referidos = referidos;
    }

}