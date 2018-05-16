package com.BarcelonaSC.BarcelonaApp.models;

import com.BarcelonaSC.BarcelonaApp.models.UserItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Leonardojpr on 1/23/18.
 */

public class WallCommentItem implements Serializable{

    @SerializedName("idcomentario")
    @Expose
    private String idcomentario;
    @SerializedName("comentario")
    @Expose
    private String comentario;
    @SerializedName("fecha")
    @Expose
    private String fecha;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("usuario")
    @Expose
    private UserItem usuario;
    @SerializedName("naplausos")
    @Expose
    private Integer naplausos;
    @SerializedName("yaaplaudio")
    @Expose
    private Integer yaaplaudio;

    public String getIdcomentario() {
        return idcomentario;
    }

    public void setIdcomentario(String idcomentario) {
        this.idcomentario = idcomentario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
