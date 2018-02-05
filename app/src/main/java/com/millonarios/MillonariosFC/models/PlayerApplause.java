package com.millonarios.MillonariosFC.models;

/**
 * Created by Carlos on 12/10/2017.
 */

public class PlayerApplause {
    private String idjugador;
    private int idpartido;
    private String imei;

    public PlayerApplause(String idjugador, int idpartido, String imei) {
        this.idjugador = idjugador;
        this.idpartido = idpartido;
        this.imei = imei;
    }

    public int getIdpartido() {
        return idpartido;
    }

    public void setIdpartido(int idpartido) {
        this.idpartido = idpartido;
    }

    public String getIdjugador() {
        return idjugador;
    }

    public void setIdjugador(String idjugador) {
        this.idjugador = idjugador;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
