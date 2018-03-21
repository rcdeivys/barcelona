package com.BarcelonaSC.BarcelonaApp.models.response;

import com.BarcelonaSC.BarcelonaApp.models.MyIdealElevenData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Carlos on 17/11/2017.
 */

public class SendMyIdealElevenData {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("jugadores")
    @Expose
    private List<MyIdealElevenData> jugadores = null;
    @SerializedName("foto")
    @Expose
    private String foto = null;
    @SerializedName("url")
    @Expose
    private String url = null;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<MyIdealElevenData> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<MyIdealElevenData> jugadores) {
        this.jugadores = jugadores;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

