package com.millonarios.MillonariosFC.models.firebase;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Carlos on 24/01/2018.
 */

public class Grupo implements Parcelable {

    String key;
    Long fecha_creacion;
    String foto;
    String id_conversacion;
    String nombre;
    Conversacion conversacion;

    public Grupo() {
    }

    public Grupo(Long fecha_creacion, String foto, String id_conversacion, String nombre) {
        this.fecha_creacion = fecha_creacion;
        this.foto = foto;
        this.id_conversacion = id_conversacion;
        this.nombre = nombre;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(key);
        dest.writeValue(fecha_creacion);
        dest.writeValue(foto);
        dest.writeValue(id_conversacion);
        dest.writeValue(nombre);
        dest.writeValue(conversacion);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Grupo> CREATOR = new Creator<Grupo>() {
        @Override
        public Grupo createFromParcel(Parcel in) {
            Grupo grupo = new Grupo();
            grupo.key = ((String) in.readValue((String.class.getClassLoader())));
            grupo.fecha_creacion = ((Long) in.readValue((Long.class.getClassLoader())));
            grupo.foto = ((String) in.readValue((String.class.getClassLoader())));
            grupo.id_conversacion = ((String) in.readValue((String.class.getClassLoader())));
            grupo.nombre = ((String) in.readValue((String.class.getClassLoader())));
            grupo.conversacion = ((Conversacion) in.readValue((Conversacion.class.getClassLoader())));
            return grupo;
        }

        @Override
        public Grupo[] newArray(int size) {
            return new Grupo[size];
        }
    };

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Long fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getId_conversacion() {
        return id_conversacion;
    }

    public void setId_conversacion(String id_conversacion) {
        this.id_conversacion = id_conversacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public Conversacion getConversacion() {
        return conversacion;
    }

    @Override
    public String toString() {
        return "Grupo{" +
                "key='" + key + '\'' +
                ", fecha_creacion='" + fecha_creacion + '\'' +
                ", foto='" + foto + '\'' +
                ", id_conversacion='" + id_conversacion + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }

    public void setConversacion(Conversacion conversaciones) {
        this.conversacion = conversaciones;
    }
}
