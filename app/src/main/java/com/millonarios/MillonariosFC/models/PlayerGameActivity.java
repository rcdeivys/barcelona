package com.millonarios.MillonariosFC.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Carlos on 14/11/2017.
 */

public class PlayerGameActivity {


    @SerializedName("actividad")
    @Expose
    private String actividad;
    @SerializedName("minuto")
    @Expose
    private Integer minuto;

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getMinuto() {
        return String.valueOf(minuto);
    }

    public void setMinuto(String minuto) {
        this.minuto = Integer.valueOf(minuto);
    }

}