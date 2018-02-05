package com.millonarios.MillonariosFC.ui.referredto.fragments.adapters;

/**
 * Created by Amplex on 31/10/2017.
 */

public class ReferredParser {

    private String nombre;
    private String apellido;
    private String email;
    private String apodo;
    private String celular;
    private String pais;
    private String genero;
    private String foto;
    private String status;

    public ReferredParser(String nombre, String apellido, String email, String apodo, String celular, String pais, String genero, String foto, String status) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.apodo = apodo;
        this.celular = celular;
        this.pais = pais;
        this.genero = genero;
        this.foto = foto;
        this.status = status;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}