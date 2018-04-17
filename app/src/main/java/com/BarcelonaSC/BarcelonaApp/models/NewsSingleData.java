package com.BarcelonaSC.BarcelonaApp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RYA-Laptop on 22/03/2018.
 */

public class NewsSingleData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("titulo")
    @Expose
    private String titulo;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("fecha")
    @Expose
    private String fecha;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("destacada")
    @Expose
    private Integer destacada;
    @SerializedName("tipo")
    @Expose
    private String tipo;
    @SerializedName("dorado")
    @Expose
    private Integer dorado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Integer getDestacada() {
        return destacada;
    }

    public void setDestacada(Integer destacada) {
        this.destacada = destacada;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getDorado() {
        return dorado;
    }

    public void setDorado(Integer dorado) {
        this.dorado = dorado;
    }

}