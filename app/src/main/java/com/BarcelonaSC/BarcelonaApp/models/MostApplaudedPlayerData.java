package com.BarcelonaSC.BarcelonaApp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Carlos on 12/10/2017.
 */

public class MostApplaudedPlayerData {
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
    @SerializedName("partido_actual")
    @Expose
    private ArrayList<PlayersApplause> partido_actual;
    @SerializedName("acumulado")
    @Expose
    private ArrayList<PlayersApplause> acumulado;

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

    public ArrayList<PlayersApplause> getPartido_actual() {
        return partido_actual;
    }

    public void setPartido_actual(ArrayList<PlayersApplause> partido_actual) {
        this.partido_actual = partido_actual;
    }

    public ArrayList<PlayersApplause> getAcumulado() {
        return acumulado;
    }

    public void setAcumulado(ArrayList<PlayersApplause> acumulado) {
        this.acumulado = acumulado;
    }
}
