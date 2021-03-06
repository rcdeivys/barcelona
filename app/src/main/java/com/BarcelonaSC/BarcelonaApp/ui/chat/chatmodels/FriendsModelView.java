package com.BarcelonaSC.BarcelonaApp.ui.chat.chatmodels;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.BarcelonaSC.BarcelonaApp.models.firebase.Miembro;

/**
 * Created by Cesar on 23/01/2018.
 */


public class FriendsModelView implements Parcelable, Comparable<FriendsModelView> {

    private String id = "";
    private String id_usuario = "";
    private Long id_amigo;
    private String nombre_uppercase = "";
    private String apodo = "";
    private String photo = "";
    private String nombre = "";
    private String apellido = "";
    private STATUS isonline;
    private boolean isPressed;
    private boolean bloqueado; //0 no bloqueado - 1 bloqueado
    private Long fecha_amistad;
    private String id_conversacion = "";
    private String created_at = "";

    @Override
    public String toString() {
        return "FriendsModelView{" +
                "id='" + id + '\'' +
                ", id_usuario='" + id_usuario + '\'' +
                ", id_amigo=" + id_amigo +
                ", apodo='" + apodo + '\'' +
                ", photo='" + photo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", isonline=" + isonline +
                ", isPressed=" + isPressed +
                ", bloqueado=" + bloqueado +
                ", fecha_amistad=" + fecha_amistad +
                ", id_conversacion='" + id_conversacion + '\'' +
                ", created_at='" + created_at + '\'' +
                '}';
    }

    public enum STATUS {
        OFFLINE(-1), BUSY(0), ONLINE(1);
        int value;

        STATUS(int aux) {
            this.value = aux;

        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public FriendsModelView(Long id_amigo) {
        this.id_amigo = id_amigo;
        this.apodo = "";
        this.photo = "";
        this.isonline = STATUS.ONLINE;
        this.bloqueado = false;
        this.fecha_amistad = 0L;
        this.created_at = "";
        this.apellido = "";
    }


    public FriendsModelView(Long id_amigo, String apodo, String photo, String nombre) {
        this.id_amigo = id_amigo;
        this.apodo = apodo;
        this.photo = photo;
        this.nombre = nombre;
    }

    public FriendsModelView(Long id_amigo, String apodo, String photo, String nombre, String apellido) {
        this.id_amigo = id_amigo;
        this.apodo = apodo;
        this.photo = photo;
        this.nombre = nombre;
        this.apellido = apellido;
    }


    public FriendsModelView(Long id_amigo, String apodo, String photo, String nombre, String nombre_uppercase, String apellido) {
        this.id_amigo = id_amigo;
        this.apodo = apodo;
        this.photo = photo;
        this.nombre = nombre;
        this.nombre_uppercase = nombre_uppercase;
        this.apellido = apellido;
    }

    public FriendsModelView(Long id_amigo, String apodo, String photo, STATUS status, boolean bloqueado, Long fecha_amistad, String created_at) {
        this.id_amigo = id_amigo;
        this.apodo = apodo;
        this.photo = photo;
        this.isonline = status;
        this.bloqueado = bloqueado;
        this.fecha_amistad = fecha_amistad;
        this.created_at = created_at;
    }

    public FriendsModelView(Long id_amigo, String apodo, String photo, STATUS status, boolean bloqueado, Long fecha_amistad, String id_conversacion, String nombre) {
        this.id_amigo = id_amigo;
        this.apodo = apodo;
        this.photo = photo;
        this.isonline = status;
        this.bloqueado = bloqueado;
        this.fecha_amistad = fecha_amistad;
        this.id_conversacion = id_conversacion;
        this.nombre = nombre;

    }

    public FriendsModelView(Long id_amigo, String apodo, String photo, STATUS status, boolean bloqueado, Long fecha_amistad, String id_conversacion, String nombre, String created_at) {
        this.id_amigo = id_amigo;
        this.apodo = apodo;
        this.photo = photo;
        this.isonline = status;
        this.bloqueado = bloqueado;
        this.fecha_amistad = fecha_amistad;
        this.id_conversacion = id_conversacion;
        this.nombre = nombre;
        this.created_at = created_at;

    }


    public FriendsModelView(Long id_amigo, String apodo, String photo, String isonlineValue, boolean bloqueado, String id_conversacion, String nombre, String created_at) {
        this.id_amigo = id_amigo;
        this.apodo = apodo;
        this.photo = photo;
        defSTATUSChat(isonlineValue);
        this.bloqueado = bloqueado;
        this.id_conversacion = id_conversacion;
        this.nombre = nombre;
        this.created_at = created_at;
    }

    public String getNombre() {
        if(nombre==null)
            return "";
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    private void defSTATUSChat(String isonlineValue) {
        if (isonlineValue == null) {
            this.isonline = STATUS.BUSY;
            return;
        }

        switch (isonlineValue) {
            case "1":
                this.isonline = STATUS.ONLINE;
                break;
            case "-1":
                this.isonline = STATUS.OFFLINE;
                break;
            case "0":
                this.isonline = STATUS.BUSY;
                break;
        }
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public FriendsModelView(Parcel parcel) {
        id = parcel.readString();

        apodo = parcel.readString();

        if (parcel.readInt() == 1) {
            isonline = STATUS.ONLINE;
        } else if (parcel.readInt() == -1) {
            isonline = STATUS.OFFLINE;
        } else if (parcel.readInt() == 0) {
            isonline = STATUS.BUSY;
        }

        photo = parcel.readString();
    }

    public String getApellido() {
        return apellido;
    }

    public String getId() {
        return id;
    }

    public Long getLongId() {
        return Long.valueOf(id_amigo);
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Long getId_amigo() {
        return id_amigo;
    }

    public void setId_amigo(Long id_amigo) {
        this.id_amigo = id_amigo;
    }

    public String getApodo() {
        if (apodo == null)
            return nombre;
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public STATUS getIsonline() {
        return isonline;
    }

    public void setIsonline(STATUS isonline) {
        this.isonline = isonline;
    }

    public boolean isPressed() {
        return isPressed;
    }

    public String getNombre_uppercase() {
        return nombre_uppercase;
    }

    public void setPressed(boolean pressed) {
        isPressed = pressed;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public static final Creator<FriendsModelView> CREATOR =
            new Creator<FriendsModelView>() {

                @Override
                public FriendsModelView createFromParcel(Parcel parcel) {
                    return new FriendsModelView(parcel);
                }

                @Override
                public FriendsModelView[] newArray(int i) {
                    return new FriendsModelView[i];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(apodo);

        if (isonline == STATUS.ONLINE)
            parcel.writeInt(1);
        else if (isonline == STATUS.OFFLINE)
            parcel.writeInt(-1);
        else if (isonline == STATUS.BUSY)
            parcel.writeInt(0);

        parcel.writeString(photo);
    }

    public boolean getBloqueado() {
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

    public String getStatusChatAsString() {
        switch (this.isonline) {
            case ONLINE:
                return "1";
            case OFFLINE:
                return "-1";
            case BUSY:
                return "0";
            default:
                return "-1";
        }
    }

    @Override
    public int compareTo(@NonNull FriendsModelView o) {
        if (isonline.getValue() < o.isonline.getValue()) {
            return -1;
        }
        if (isonline.getValue() > o.isonline.getValue()) {
            return 1;
        }
        return 0;
    }


    public Miembro getMemberFromFriend() {
        Miembro miembro = new Miembro();
        miembro.setId(getId_amigo());
        miembro.setNombre(getNombre());
        miembro.setFoto(getPhoto());
        return miembro;
    }
}
