package com.BarcelonaSC.BarcelonaApp.models;

/**
 * Created by root on 7/18/17.
 */

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthItem {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("idusuario")
    @Expose
    private String idUser;
    @SerializedName("codigo")
    @Expose
    private String codigo;

    public String getToken() {
        Log.i("TAG", "--->" + token);
        return token;
    }

    public void setToken(String token) {
        Log.i("TAG", "--->" + token);
        this.token = token;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}