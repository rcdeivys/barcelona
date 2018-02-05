package com.millonarios.MillonariosFC.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by leonardojpr on 1/11/18.
 */

public class WallItem implements Serializable {

    @SerializedName("idpost")
    @Expose
    private String idpost;
    @SerializedName("mensaje")
    @Expose
    private String mensaje;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("fecha")
    @Expose
    private String fecha;
    @SerializedName("usuario")
    @Expose
    private UserItem usuario;
    @SerializedName("ncomentarios")
    @Expose
    private Integer ncomentarios;
    @SerializedName("naplausos")
    @Expose
    private Integer naplausos;
    @SerializedName("yaaplaudio")
    @Expose
    private Integer yaaplaudio;

    public String getIdpost() {
        return idpost;
    }

    public void setIdpost(String idpost) {
        this.idpost = idpost;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public UserItem getUsuario() {
        return usuario;
    }

    public void setUsuario(UserItem usuario) {
        this.usuario = usuario;
    }

    public Integer getNcomentarios() {
        return ncomentarios;
    }

    public void setNcomentarios(Integer ncomentarios) {
        this.ncomentarios = ncomentarios;
    }

    public Integer getNaplausos() {
        return naplausos;
    }

    public void setNaplausos(Integer naplausos) {
        this.naplausos = naplausos;
    }

    public Integer getYaaplaudio() {
        return yaaplaudio;
    }

    public void setYaaplaudio(Integer yaaplaudio) {
        this.yaaplaudio = yaaplaudio;
    }
}
