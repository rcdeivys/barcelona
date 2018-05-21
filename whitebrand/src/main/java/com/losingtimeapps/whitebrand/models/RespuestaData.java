package com.losingtimeapps.whitebrand.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Carlos on 12/01/2018.
 */

public class RespuestaData {
    @SerializedName("idrespuesta")
    @Expose
    private Integer idrespuesta;
    @SerializedName("respuesta")
    @Expose
    private String respuesta;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("votos")
    @Expose
    private Integer votos;
    @SerializedName("miniatura")
    @Expose
    private String miniatura;
    @SerializedName("yavoto")
    @Expose
    private String yaVoto;


    public Integer getIdrespuesta() {
        return idrespuesta;
    }

    public void setIdrespuesta(Integer idrespuesta) {
        this.idrespuesta = idrespuesta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Integer getVotos() {
        return votos;
    }

    public void setVotos(Integer votos) {
        this.votos = votos;
    }

    public String getMiniatura() {
        return miniatura;
    }

    public void setMiniatura(String miniatura) {
        this.miniatura = miniatura;
    }

    public String getYaVoto() {
        return yaVoto;
    }

    public void setYaVoto(String yaVoto) {
        this.yaVoto = yaVoto;
    }
}