package com.millonarios.MillonariosFC.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Carlos on 12/01/2018.
 */

public class ChooseProfileData {
    @SerializedName("respuesta")
    @Expose
    private String respuesta;
    @SerializedName("banner")
    @Expose
    private String banner;
    @SerializedName("votos")
    @Expose
    private Integer votos;
    @SerializedName("noticias")
    @Expose
    private List<News> noticias = null;

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public Integer getVotos() {
        return votos;
    }

    public void setVotos(Integer votos) {
        this.votos = votos;
    }

    public List<News> getNoticias() {
        return noticias;
    }

    public void setNoticias(List<News> noticias) {
        this.noticias = noticias;
    }

}