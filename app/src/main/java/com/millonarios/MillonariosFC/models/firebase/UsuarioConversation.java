package com.millonarios.MillonariosFC.models.firebase;

/**
 * Created by quint on 1/29/2018.
 */

public class UsuarioConversation {

    String id;
    Long fecha;
    boolean status;
    String ultimo_mensaje;
    Long id_participante;
    String id_primer_mensaje;

    public UsuarioConversation() {
    }

    public UsuarioConversation(Long fecha, boolean status, String ultimo_mensaje, Long id_participante) {
        this.fecha = fecha;
        this.status = status;
        this.ultimo_mensaje = ultimo_mensaje;
        this.id_participante = id_participante;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getId_participante() {
        return id_participante;
    }

    public void setId_participante(Long id_participante) {
        this.id_participante = id_participante;
    }

    public String getId_primer_mensaje() {
        return id_primer_mensaje;
    }

    public void setId_primer_mensaje(String id_primer_mensaje) {
        this.id_primer_mensaje = id_primer_mensaje;
    }

    public UsuarioConversation(boolean status) {
        this.status = status;
    }

    public Long getFecha() {
        return fecha;
    }

    public void setFecha(Long fecha) {
        this.fecha = fecha;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getUltimo_mensaje() {
        return ultimo_mensaje;
    }

    public void setUltimo_mensaje(String ultimo_mensaje) {
        this.ultimo_mensaje = ultimo_mensaje;
    }
}
