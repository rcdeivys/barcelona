package com.millonarios.MillonariosFC.models.firebase;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Carlos on 24/01/2018.
 */

public class Mensajes implements Parcelable {

    String id;
    Long emisor_mensaje;
    Long fecha_mensaje;
    String texto_mensaje;
    String tipo_mensaje;
    String url_imagen;
    String imagen_emisor;

    public Mensajes() {
    }

    public Mensajes( Long emisor_mensaje, Long fecha_mensaje, String texto_mensaje, String tipo_mensaje, String url_imagen,String imagen_emisor) {
        this.id = id;
        this.emisor_mensaje = emisor_mensaje;
        this.fecha_mensaje = fecha_mensaje;
        this.texto_mensaje = texto_mensaje;
        this.tipo_mensaje = tipo_mensaje;
        this.url_imagen = url_imagen;
    }

    public Mensajes(Long emisor_mensaje, Long fecha_mensaje, String texto_mensaje, String tipo_mensaje,String imagen_emisor) {
        this.id = id;
        this.emisor_mensaje = emisor_mensaje;
        this.fecha_mensaje = fecha_mensaje;
        this.texto_mensaje = texto_mensaje;
        this.tipo_mensaje = tipo_mensaje;
    }


    public Mensajes(String id, Long emisor_mensaje, Long fecha_mensaje, String texto_mensaje, String tipo_mensaje, String url_imagen,String imagen_emisor) {
        this.id = id;
        this.emisor_mensaje = emisor_mensaje;
        this.fecha_mensaje = fecha_mensaje;
        this.texto_mensaje = texto_mensaje;
        this.tipo_mensaje = tipo_mensaje;
        this.url_imagen = url_imagen;
    }

    public Mensajes(String id, Long emisor_mensaje, Long fecha_mensaje, String texto_mensaje, String tipo_mensaje, String imagen_emisor) {
        this.id = id;
        this.emisor_mensaje = emisor_mensaje;
        this.fecha_mensaje = fecha_mensaje;
        this.texto_mensaje = texto_mensaje;
        this.tipo_mensaje = tipo_mensaje;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(emisor_mensaje);
        dest.writeValue(fecha_mensaje);
        dest.writeValue(texto_mensaje);
        dest.writeValue(tipo_mensaje);
        dest.writeValue(url_imagen);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Mensajes> CREATOR = new Creator<Mensajes>() {
        @Override
        public Mensajes createFromParcel(Parcel in) {
            Mensajes mensajes = new Mensajes();
            mensajes.id = ((String) in.readValue((String.class.getClassLoader())));
            mensajes.emisor_mensaje = ((Long) in.readValue((Long.class.getClassLoader())));
            mensajes.fecha_mensaje = ((Long) in.readValue((Long.class.getClassLoader())));
            mensajes.texto_mensaje = ((String) in.readValue((String.class.getClassLoader())));
            mensajes.tipo_mensaje = ((String) in.readValue((String.class.getClassLoader())));
            mensajes.url_imagen = ((String) in.readValue((String.class.getClassLoader())));
            return mensajes;
        }

        @Override
        public Mensajes[] newArray(int size) {
            return new Mensajes[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getEmisor_mensaje() {
        return emisor_mensaje;
    }

    public void setEmisor_mensaje(Long emisor_mensaje) {
        this.emisor_mensaje = emisor_mensaje;
    }

    public Long getFecha_mensaje() {
        return fecha_mensaje;
    }

    public void setFecha_mensaje(Long fecha_mensaje) {
        this.fecha_mensaje = fecha_mensaje;
    }


    public String getTexto_mensaje() {
        return texto_mensaje;
    }

    public void setTexto_mensaje(String texto_mensaje) {
        this.texto_mensaje = texto_mensaje;
    }

    public String getTipo_mensaje() {
        return tipo_mensaje;
    }

    public void setTipo_mensaje(String tipo_mensaje) {
        this.tipo_mensaje = tipo_mensaje;
    }

    public String getUrl_imagen() {
        return url_imagen;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }
}
