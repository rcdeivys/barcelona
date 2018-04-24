package com.BarcelonaSC.BarcelonaApp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by quint on 4/4/2018.
 */

public class ChatReportData {

    @SerializedName("usuario")
    @Expose
    private Integer usuario;
    @SerializedName("usuario_reportado")
    @Expose
    private Integer usuarioReportado;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;

    public ChatReportData(String usuario, Long usuarioReportado, String descripcion) {
        this.usuario = Integer.parseInt(usuario);
        this.usuarioReportado = Integer.parseInt(usuarioReportado + "");
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "ChatReportData{" +
                "usuario=" + usuario +
                ", usuarioReportado=" + usuarioReportado +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    public Integer getUsuarioReportado() {
        return usuarioReportado;
    }

    public void setUsuarioReportado(Integer usuarioReportado) {
        this.usuarioReportado = usuarioReportado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
