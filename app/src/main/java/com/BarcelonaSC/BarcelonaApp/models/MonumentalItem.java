package com.BarcelonaSC.BarcelonaApp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by root on 10/20/17.
 */

public class MonumentalItem {

    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("total_votos")
    @Expose
    private Integer totalVotos;
    @SerializedName("instagram")
    @Expose
    private String instagram;
    @SerializedName("noticias")
    @Expose
    private List<News> newsList;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Integer getTotalVotos() {
        return totalVotos;
    }

    public void setTotalVotos(Integer totalVotos) {
        this.totalVotos = totalVotos;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

}