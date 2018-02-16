package com.BarcelonaSC.BarcelonaApp.models.firebase;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Carlos on 24/01/2018.
 */
@IgnoreExtraProperties
public class Solicitud {

    String key;
    Long fecha;
    long status;
    Miembro miembro;

    public Miembro getMiembro() {
        return miembro;
    }

    public Solicitud() {
    }

    public Solicitud(Long fecha, long status) {
        this.fecha = fecha;
        this.status = status;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getFecha() {
        return fecha;
    }

    public void setFecha(Long fecha) {
        this.fecha = fecha;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Solicitud{" +
                "key='" + key + '\'' +
                ", fecha='" + fecha + '\'' +
                ", status=" + status +
                '}';
    }

    public void setMiembro(Miembro miembro) {
        this.miembro = miembro;
    }

    public void copy(Solicitud solicitud) {
        this.fecha = solicitud.fecha;
        this.status = solicitud.status;
    }
}
