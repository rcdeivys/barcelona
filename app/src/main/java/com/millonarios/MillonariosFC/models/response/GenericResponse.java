package com.millonarios.MillonariosFC.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenericResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("error")
    @Expose
    private List<String> error;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public List<String> getError() {
        return error;
    }

    public void setError(List<String> error) {
        this.error = error;
    }

    public class Data {
        @SerializedName("mensaje_pin")
        @Expose
        private String mensaje_pin;

        public String getMensaje_pin() {
            return mensaje_pin;
        }

        public void setMensaje_pin(String mensaje_pin) {
            this.mensaje_pin = mensaje_pin;
        }
    }
}