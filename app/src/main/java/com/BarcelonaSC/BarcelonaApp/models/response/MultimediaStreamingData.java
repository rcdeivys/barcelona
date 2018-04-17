package com.BarcelonaSC.BarcelonaApp.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Deivys on 3/29/2018.
 */

public class MultimediaStreamingData {


    @SerializedName("url_envivo")
    @Expose
    private String urlEnvivo;
    @SerializedName("foto")
    @Expose
    private String foto;

    public String getUrlEnvivo() {
        return urlEnvivo;
    }

    public void setUrlEnvivo(String urlEnvivo) {
        this.urlEnvivo = urlEnvivo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "MultimediaStreamingData{" +
                "urlEnvivo='" + urlEnvivo + '\'' +
                ", foto='" + foto + '\'' +
                '}';
    }

}
