package com.BarcelonaSC.BarcelonaApp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Leonardojpr on 10/19/17.
 */

public class WebViewItem {

    @SerializedName("url_tabla")
    @Expose
    private String urlTabla;
    @SerializedName("url_simulador")
    @Expose
    private String urlSimulador;
    @SerializedName("url_juramento")
    @Expose
    private String urlJuramento;
    @SerializedName("url_livestream")
    @Expose
    private String urlLivestream;

    public String getUrlTabla() {
        return urlTabla;
    }

    public void setUrlTabla(String urlTabla) {
        this.urlTabla = urlTabla;
    }

    public String getUrlSimulador() {
        return urlSimulador;
    }

    public void setUrlSimulador(String urlSimulador) {
        this.urlSimulador = urlSimulador;
    }

    public String getUrlJuramento() {
        return urlJuramento;
    }

    public void setUrlJuramento(String urlJuramento) {
        this.urlJuramento = urlJuramento;
    }

    public String getUrlLivestream() {
        return urlLivestream;
    }

    public void setUrlLivestream(String urlLivestream) {
        this.urlLivestream = urlLivestream;
    }

}
