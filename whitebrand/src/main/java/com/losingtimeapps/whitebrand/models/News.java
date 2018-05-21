package com.losingtimeapps.whitebrand.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Clase para administrar el objeto News
 */

public class News {

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
    @SerializedName("active")
    @Expose
    private Integer active;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("aparecetimelineppal")
    @Expose
    private Integer aparecetimelineppal;
    @SerializedName("destacada")
    @Expose
    private Integer destacada;
    @SerializedName("tipo")
    @Expose
    private String tipo;
    @SerializedName("aparevetimelinemonumentales")
    @Expose
    private Integer aparevetimelinemonumentales;
    @SerializedName("created_at")
    @Expose
    private Object createdAt;
    @SerializedName("dorado")
    @Expose
    private int dorado;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;

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

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Integer getAparecetimelineppal() {
        return aparecetimelineppal;
    }

    public void setAparecetimelineppal(Integer aparecetimelineppal) {
        this.aparecetimelineppal = aparecetimelineppal;
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

    public Integer getAparevetimelinemonumentales() {
        return aparevetimelinemonumentales;
    }

    public void setAparevetimelinemonumentales(Integer aparevetimelinemonumentales) {
        this.aparevetimelinemonumentales = aparevetimelinemonumentales;
    }

    public boolean isDorado() {
        return dorado == 1;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

}