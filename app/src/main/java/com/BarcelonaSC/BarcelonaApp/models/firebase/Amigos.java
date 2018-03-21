package com.BarcelonaSC.BarcelonaApp.models.firebase;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Carlos on 24/01/2018.
 */

public class Amigos implements Parcelable {

    Long id;
    boolean bloqueado;
    Long fecha_amistad;
    String id_conversacion;
    Conversacion conversacion;

    public Amigos() {
    }

    public Amigos(boolean bloqueado, Long fecha_amistad, String id_conversacion) {
        this.bloqueado = bloqueado;
        this.fecha_amistad = fecha_amistad;
        this.id_conversacion = id_conversacion;
    }


    public static final Creator<Amigos> CREATOR = new Creator<Amigos>() {
        @Override
        public Amigos createFromParcel(Parcel in) {
            Amigos amigos = new Amigos();
            amigos.id = ((Long) in.readValue((Long.class.getClassLoader())));
            amigos.bloqueado = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            amigos.fecha_amistad = ((Long) in.readValue((Long.class.getClassLoader())));
            amigos.id_conversacion = ((String) in.readValue((String.class.getClassLoader())));
            amigos.conversacion = ((Conversacion) in.readValue((Conversacion.class.getClassLoader())));
            return amigos;
        }

        @Override
        public Amigos[] newArray(int size) {
            return new Amigos[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public Long getFecha_amistad() {
        return fecha_amistad;
    }

    public void setFecha_amistad(Long fecha_amistad) {
        this.fecha_amistad = fecha_amistad;
    }

    public String getId_conversacion() {
        return id_conversacion;
    }

    public void setId_conversacion(String id_conversacion) {
        this.id_conversacion = id_conversacion;
    }

    public Conversacion getConversacion() {
        return conversacion;
    }

    public void setConversacion(Conversacion conversacion) {
        this.conversacion = conversacion;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(bloqueado);
        dest.writeValue(fecha_amistad);
        dest.writeValue(id_conversacion);
        dest.writeValue(conversacion);
    }

    public void copy(Amigos dataAmigo) {
        id = dataAmigo.getId();
        bloqueado = dataAmigo.isBloqueado();
        fecha_amistad = dataAmigo.getFecha_amistad();
        id_conversacion = dataAmigo.getId_conversacion();

    }
}