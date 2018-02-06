package com.BarcelonaSC.BarcelonaApp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Carlos on 12/10/2017.
 */

public class PlayerData {
    @SerializedName("idjugador")
    @Expose
    private Integer idJugador;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("fecha_nacimiento")
    @Expose
    private String fechaNacimiento;
    @SerializedName("nacionalidad")
    @Expose
    private String nacionalidad;
    @SerializedName("n_camiseta")
    @Expose
    private String nCamiseta;
    @SerializedName("posicion")
    @Expose
    private String posicion;
    @SerializedName("banner")
    @Expose
    private String banner;
    @SerializedName("sepuedeaplaudir")
    @Expose
    private Integer sepuedeaplaudir;
    @SerializedName("idpartido")
    @Expose
    private Integer idpartido;
    @SerializedName("equipo_1")
    @Expose
    private String equipo1;
    @SerializedName("bandera_1")
    @Expose
    private String bandera1;
    @SerializedName("goles_1")
    @Expose
    private Integer goles1;
    @SerializedName("equipo_2")
    @Expose
    private String equipo2;
    @SerializedName("bandera_2")
    @Expose
    private String bandera2;
    @SerializedName("instagram")
    @Expose
    private String instagram;
    @SerializedName("goles_2")
    @Expose
    private Integer goles2;
    @SerializedName("fecha")
    @Expose
    private String fecha;
    @SerializedName("fecha_etapa")
    @Expose
    private String fechaEtapa;
    @SerializedName("estadio")
    @Expose
    private String estadio;
    @SerializedName("apalusos_ultimo_partido")
    @Expose
    private Integer apalusosUltimoPartido;
    @SerializedName("aplausos_acumulado")
    @Expose
    private Integer aplausosAcumulado;
    @SerializedName("noticias")
    @Expose
    private List<News> newsList;
    @SerializedName("peso")
    @Expose
    private String peso;
    @SerializedName("estatura")
    @Expose
    private String estatura;

    public Integer getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(Integer idJugador) {
        this.idJugador = idJugador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getnCamiseta() {
        return nCamiseta;
    }

    public void setnCamiseta(String nCamiseta) {
        this.nCamiseta = nCamiseta;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public Integer getSepuedeaplaudir() {
        return sepuedeaplaudir;
    }

    public void setSepuedeaplaudir(Integer sepuedeaplaudir) {
        this.sepuedeaplaudir = sepuedeaplaudir;
    }

    public Integer getIdpartido() {
        return idpartido;
    }

    public void setIdpartido(Integer idpartido) {
        this.idpartido = idpartido;
    }

    public String getEquipo1() {
        return equipo1;
    }

    public void setEquipo1(String equipo1) {
        this.equipo1 = equipo1;
    }

    public String getBandera1() {
        return bandera1;
    }

    public void setBandera1(String bandera1) {
        this.bandera1 = bandera1;
    }

    public Integer getGoles1() {
        return goles1;
    }

    public void setGoles1(Integer goles1) {
        this.goles1 = goles1;
    }

    public String getEquipo2() {
        return equipo2;
    }

    public void setEquipo2(String equipo2) {
        this.equipo2 = equipo2;
    }

    public String getBandera2() {
        return bandera2;
    }

    public void setBandera2(String bandera2) {
        this.bandera2 = bandera2;
    }

    public Integer getGoles2() {
        return goles2;
    }

    public void setGoles2(Integer goles2) {
        this.goles2 = goles2;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFechaEtapa() {
        return fechaEtapa;
    }

    public void setFechaEtapa(String fechaEtapa) {
        this.fechaEtapa = fechaEtapa;
    }

    public String getEstadio() {
        return estadio;
    }

    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }

    public Integer getApalusosUltimoPartido() {
        return apalusosUltimoPartido;
    }

    public void setApalusosUltimoPartido(Integer apalusosUltimoPartido) {
        this.apalusosUltimoPartido = apalusosUltimoPartido;
    }

    public Integer getAplausosAcumulado() {
        return aplausosAcumulado;
    }

    public void setAplausosAcumulado(Integer aplausosAcumulado) {
        this.aplausosAcumulado = aplausosAcumulado;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getEstatura() {
        return estatura;
    }

    public void setEstatura(String estatura) {
        this.estatura = estatura;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }
}
