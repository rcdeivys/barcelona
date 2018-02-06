package com.BarcelonaSC.BarcelonaApp.models.firebase;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Carlos on 24/01/2018.
 */

public class Conversacion implements Parcelable {

    String id;
    long fecha;
    Map<String, Mensajes> mensajes;
    List<Miembro> miembros;

    public Conversacion() {

    }

    public Conversacion(long fecha) {
        this.fecha = fecha;
    }


    public static final Creator<Conversacion> CREATOR = new Creator<Conversacion>() {
        @Override
        public Conversacion createFromParcel(Parcel in) {
            Conversacion conversacion = new Conversacion();
            conversacion.id = ((String) in.readValue((String.class.getClassLoader())));
            conversacion.fecha = ((Long) in.readValue((Long.class.getClassLoader())));
            conversacion.miembros = in.createTypedArrayList(Miembro.CREATOR);
            //      conversacion.setMensajes((in.createTypedArrayList(Mensajes.CREATOR)));
            return conversacion;
        }

        @Override
        public Conversacion[] newArray(int size) {
            return new Conversacion[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeValue(id);
        dest.writeValue(fecha);
        dest.writeTypedList(miembros);
        //    dest.writeTypedList(getMensajes());
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getFecha() {
        return fecha;
    }

    public void setFecha(Long fecha) {
        this.fecha = fecha;
    }


    public void setMensajes(List<Mensajes> lista) {
        Map<String, Mensajes> map = new HashMap<>();
        for (Mensajes mensaje : lista) {
            map.put(mensaje.getId(), mensaje);
        }
        mensajes = map;

    }

    public List<Mensajes> getMensajes() {
        if (mensajes != null) {
            List<Mensajes> mensajeList = new ArrayList<>();
            mensajeList.addAll(mensajes.values());
            return mensajeList;
        }
        return new ArrayList<>();
    }

    public List<Miembro> getMiembros() {
        return miembros;
    }

    public void setMiembros(List<Miembro> miembros) {
        this.miembros = miembros;
    }

    public void addMiembro(Miembro miembro) {

        if (this.miembros == null)
            this.miembros = new ArrayList<>();
        Log.i("TAG", "--->DADADA" + miembro);
        for (int i = 0; i < this.miembros.size(); i++)
            if (this.miembros.get(i).getId().equals(miembro.getId())) {
                this.miembros.remove(i);
                this.miembros.add(i, miembro);
                return;
            }

        this.miembros.add(miembro);

    }

    public Miembro getMiembro(Long idMiembro) {
        if (miembros == null)
            miembros = new ArrayList<>();
        for (Miembro miembro : miembros) {
            if (miembro.getId().equals(idMiembro))
                return miembro;
        }
        return null;
    }

    public void copy(Conversacion conversacion) {
        this.fecha = conversacion.getFecha();
        this.mensajes = conversacion.mensajes;

    }
}
