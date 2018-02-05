package com.millonarios.MillonariosFC.models;


import java.io.Serializable;

/**
 * Created by root on 7/18/17.
 */

public class User implements Serializable{

    private String nombre;
    private String apellido;
    private String email;
    private String clave;
    private String apodo;
    private String descripcion;
    private String celular;
    private String pais;
    private String fecha_nacimiento;
    private String genero;
    private String foto;
    private String token;
    private String source;
    private String userID_facebook;
    private String userID_google;
    private String foto_redes;
    private String registrado;

    public User() {
    }

    public User(String email, String clave) {
        this.email = email;
        this.clave = clave;
    }

    public User(String nombre, String apellido, String email, String clave, String apodo, String foto) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.clave = clave;
        this.apodo = apodo;
        this.foto = foto;
    }

    public User(String name, String apellido, String email, String clave, String apodo, String descripcion, String cedular, String pais, String fecha_nacimiento, String genero, String foto) {
        this.nombre = name;
        this.apellido = apellido;
        this.email = email;
        this.clave = clave;
        this.apodo = apodo;
        this.descripcion = descripcion;
        this.celular = cedular;
        this.pais = pais;
        this.fecha_nacimiento = fecha_nacimiento;
        this.genero = genero;
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String name) {
        this.nombre = name;
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

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCedular() {
        return celular;
    }

    public void setCedular(String cedular) {
        this.celular = cedular;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFullName() {
        return getApellido() + " " + getNombre();
    }

    public String toString() {
        return getFullName() + " " + getEmail();
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUserID_facebook() {
        return userID_facebook;
    }

    public void setUserID_facebook(String userID_facebook) {
        this.userID_facebook = userID_facebook;
    }

    public String getUserID_google() {
        return userID_google;
    }

    public void setUserID_google(String userID_google) {
        this.userID_google = userID_google;
    }

    public String getRegistrado() {
        return registrado;
    }

    public void setRegistrado(String registrado) {
        this.registrado = registrado;
    }

    public String getFoto_redes() {
        return foto_redes;
    }

    public void setFoto_redes(String foto_redes) {
        this.foto_redes = foto_redes;
    }
}
