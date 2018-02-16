package com.BarcelonaSC.BarcelonaApp.models;

/**
 * Created by Leonardojpr on 10/20/17.
 */

public class VotesMonumental {

    private int idencuesta;
    private int idmonumental;
    private String imei;

    public VotesMonumental(int idencuesta, int idmonumental, String imei) {
        this.idencuesta = idencuesta;
        this.idmonumental = idmonumental;
        this.imei = imei;
    }

    public int getIdencuesta() {
        return idencuesta;
    }

    public void setIdencuesta(int idencuesta) {
        this.idencuesta = idencuesta;
    }

    public int getIdmonumental() {
        return idmonumental;
    }

    public void setIdmonumental(int idmonumental) {
        this.idmonumental = idmonumental;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

}