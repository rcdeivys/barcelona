package com.BarcelonaSC.BarcelonaApp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by RYA-Laptop on 15/03/2018.
 */

public class MapData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("titulo")
    @Expose
    private String titulo;
    @SerializedName("latitud")
    @Expose
    private String latitud;
    @SerializedName("longitud")
    @Expose
    private String longitud;
    @SerializedName("direccion")
    @Expose
    private String direccion;
    @SerializedName("ciudad")
    @Expose
    private String ciudad;
    @SerializedName("pais")
    @Expose
    private String pais;
    @SerializedName("fecha_evento")
    @Expose
    private String fechaEvento;
    @SerializedName("hora")
    @Expose
    private String hora;
    @SerializedName("icono")
    @Expose
    private String icono;
    @SerializedName("imagenes")
    @Expose
    private List<Imagene> imagenes = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(String fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public List<Imagene> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<Imagene> imagenes) {
        this.imagenes = imagenes;
    }

}
