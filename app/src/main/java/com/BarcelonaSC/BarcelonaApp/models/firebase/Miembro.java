package com.BarcelonaSC.BarcelonaApp.models.firebase;

import android.os.Parcel;
import android.os.Parcelable;

import com.BarcelonaSC.BarcelonaApp.ui.chat.friends.FriendsModelView;

/**
 * Created by Carlos on 25/01/2018.
 */

public class Miembro implements Parcelable {

    Long id;
    String nombre;
    String apellido;
    String apodo;
    String foto;
    String chat_status;
    String created_at;
    String fotos_redes;


    public Miembro(String nombre, String apellido, String apodo, String foto, String chat_status, String created_at) {

        this.nombre = nombre;
        this.apellido = apellido;
        this.apodo = apodo;
        this.foto = foto;
        this.chat_status = chat_status;
        this.created_at = created_at;
    }

    public static final Creator<Miembro> CREATOR = new Creator<Miembro>() {
        @Override
        public Miembro createFromParcel(Parcel in) {
            Miembro miembro = new Miembro();
            miembro.id = ((Long) in.readValue((Long.class.getClassLoader())));
            miembro.nombre = ((String) in.readValue((String.class.getClassLoader())));
            miembro.apellido = ((String) in.readValue((String.class.getClassLoader())));
            miembro.apodo = ((String) in.readValue((String.class.getClassLoader())));
            miembro.foto = ((String) in.readValue((String.class.getClassLoader())));
            miembro.chat_status = ((String) in.readValue((String.class.getClassLoader())));
            miembro.created_at = ((String) in.readValue((String.class.getClassLoader())));
            miembro.fotos_redes = ((String) in.readValue((String.class.getClassLoader())));
            return miembro;
        }

        @Override
        public Miembro[] newArray(int size) {
            return new Miembro[size];
        }
    };

    @Override
    public String toString() {
        return "Miembro{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", apodo='" + apodo + '\'' +
                ", foto='" + foto + '\'' +
                ", statusChat='" + chat_status + '\'' +
                '}';
    }

    public Miembro() {
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getApodo() {
        if (apodo == null)
            apodo = nombre;
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public String getFoto() {
        if (foto == null)
            return fotos_redes;
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatusChat() {
        return chat_status;
    }

    public void setStatusChat(String statusChat) {
        this.chat_status = statusChat;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public FriendsModelView.STATUS getStatusChatAsSTATUS() {
        switch (chat_status) {
            case "1":
                return FriendsModelView.STATUS.ONLINE;
            case "-1":
                return FriendsModelView.STATUS.OFFLINE;
            case "0":
                return FriendsModelView.STATUS.BUSY;
            default:
                return FriendsModelView.STATUS.OFFLINE;
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(nombre);
        dest.writeValue(apellido);
        dest.writeValue(apodo);
        dest.writeValue(foto);
        dest.writeValue(chat_status);
        dest.writeValue(created_at);
        dest.writeValue(fotos_redes);
    }
}
