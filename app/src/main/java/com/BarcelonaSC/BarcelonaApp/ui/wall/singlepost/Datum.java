package com.BarcelonaSC.BarcelonaApp.ui.wall.singlepost;

import com.BarcelonaSC.BarcelonaApp.models.UserItem;
import com.BarcelonaSC.BarcelonaApp.models.UsuariosAplauso;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Datum {

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
    @SerializedName("usuarios_aplausos")
    @Expose
    private List<UsuariosAplauso> usuariosAplausos = null;


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

    public List<UsuariosAplauso> getUsuariosAplausos() {
        return usuariosAplausos;
    }

    public void setUsuariosAplausos(List<UsuariosAplauso> usuariosAplausos) {
        this.usuariosAplausos = usuariosAplausos;
    }
}