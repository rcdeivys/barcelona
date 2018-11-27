package com.BarcelonaSC.BarcelonaApp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OnceIdealData {

@SerializedName("jugador_id")
@Expose
private Integer jugadorId;
@SerializedName("comun")
@Expose
private Boolean comun;
@SerializedName("nombre")
@Expose
private String nombre;

public Integer getJugadorId() {
return jugadorId;
}

public void setJugadorId(Integer jugadorId) {
this.jugadorId = jugadorId;
}

public boolean getComun() {
return comun;
}

public void setComun(boolean comun) {
this.comun = comun;
}

public String getNombre() {
return nombre;
}

public void setNombre(String nombre) {
this.nombre = nombre;
}

}