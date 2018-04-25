package com.BarcelonaSC.BarcelonaApp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Cesar on 20/03/2018.
 */

public class SetTokenToFirebaseIdRequest {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("notificacionToken")
    @Expose
    private String notificacionToken;

    public SetTokenToFirebaseIdRequest(String token, String notificacionToken) {
        this.token = token;
        this.notificacionToken = notificacionToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNotificacionToken() {
        return notificacionToken;
    }

    public void setNotificacionToken(String notificacionToken) {
        this.notificacionToken = notificacionToken;
    }
}
