package com.BarcelonaSC.BarcelonaApp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserItem implements Serializable {

    @SerializedName("idusuario")
    @Expose
    private String id_usuario;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("apellido")
    @Expose
    private String apellido;
    @SerializedName("ci")
    @Expose
    private String ci;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("dorado")
    @Expose
    private int dorado;
    @SerializedName("apodo")
    @Expose
    private String apodo;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("celular")
    @Expose
    private String celular;
    @SerializedName("pais")
    @Expose
    private String pais;
    @SerializedName("fecha_nacimiento")
    @Expose
    private String fechaNacimiento;
    @SerializedName("fecha_vencimiento")
    @Expose
    private String fechaVencimiento;
    @SerializedName("genero")
    @Expose
    private String genero;
    @SerializedName("created_at")
    @Expose
    private String fechaRegistro;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("referido")
    @Expose
    private String referido;
    @SerializedName("codigo")
    @Expose
    private String codigo;

    public String getReferido() {
        return referido;
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

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setReferido(String referido) {
        this.referido = referido;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public boolean isDorado() {
        return dorado == 1;
    }

    public void setDorado(boolean dorado) {
        this.dorado = (dorado ? 1 : 0);
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }
}