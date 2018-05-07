package com.BarcelonaSC.BarcelonaApp.models.firebase;

import android.util.Log;

import com.BarcelonaSC.BarcelonaApp.models.UserItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos on 24/01/2018.
 */

public class Usuario {

    private Long id;
    private String nombre;
    private String apellido;
    private String apodo;
    private String clave;
    private String email;
    private String created_at;
    private String foto;
    private String genero;
    private String pais;
    private String puntos;
    private String ranking;
    private String chat_status;
    private String fotos_redes;

    private List<Solicitud> solicitesEnviadas;
    private List<UsuarioConversation> usuarioConversations;
    private List<Solicitud> solicitudesPendientes;
    private List<Amigos> amigos;
    private List<Grupo> grupos;

    public void copy(Usuario usuario) {
        this.id = usuario.id;
        this.nombre = usuario.nombre;
        this.apellido = usuario.apellido;
        this.apodo = usuario.apodo;
        this.clave = usuario.clave;
        this.created_at = usuario.created_at;
        this.email = usuario.email;
        this.foto = usuario.foto;
        this.genero = usuario.genero;
        this.pais = usuario.pais;
        this.puntos = usuario.puntos;
        this.ranking = usuario.ranking;
        this.chat_status = usuario.chat_status;
        this.fotos_redes = usuario.fotos_redes;
    }

    public Usuario() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
            return nombre;
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoto() {
        if (foto == null)
            return fotos_redes;
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getPuntos() {
        return puntos;
    }

    public void setPuntos(String puntos) {
        this.puntos = puntos;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getChat_status() {
        return chat_status;
    }

    public void setChat_status(String chat_status) {
        this.chat_status = chat_status;
    }

    public List<Solicitud> getSolicitudesEnviadas() {
        if (this.solicitesEnviadas == null)
            this.solicitesEnviadas = new ArrayList<>();
        return solicitesEnviadas;
    }

    public List<UsuarioConversation> getUsuarioConversations() {
        if (this.usuarioConversations == null)
            this.usuarioConversations = new ArrayList<>();
        return usuarioConversations;
    }

    public void setUsuarioConversations(List<UsuarioConversation> usuarioConversations) {

        this.usuarioConversations = usuarioConversations;
    }

    public void setUsuarioConversations(UsuarioConversation usuarioConversations) {
        if (this.usuarioConversations == null)
            this.usuarioConversations = new ArrayList<>();
        Log.i("TAG", "--->DADADA" + usuarioConversations);
        for (int i = 0; i < this.usuarioConversations.size(); i++)
            if (this.usuarioConversations.get(i).getId().equals(usuarioConversations.getId())) {
                this.usuarioConversations.remove(i);
                this.usuarioConversations.add(i, usuarioConversations);
                return;
            }

        this.usuarioConversations.add(usuarioConversations);
    }

    public void setSolicitudEnviadas(Solicitud solicitud) {
        if (this.solicitesEnviadas == null)
            this.solicitesEnviadas = new ArrayList<>();
        Log.i("TAG", "--->DADADA" + solicitud);
        for (int i = 0; i < this.solicitesEnviadas.size(); i++)
            if (this.solicitesEnviadas.get(i).getKey().equals(solicitud.getKey())) {
                this.solicitesEnviadas.remove(i);
                this.solicitesEnviadas.add(i, solicitud);
                return;
            }

        this.solicitesEnviadas.add(solicitud);
    }

    public List<Solicitud> getSolicitudesPendientes() {
        if (this.solicitudesPendientes == null)
            this.solicitudesPendientes = new ArrayList<>();
        return solicitudesPendientes;
    }

    public void setSolicitudPendientes(Solicitud solicitud) {
        if (this.solicitudesPendientes == null)
            this.solicitudesPendientes = new ArrayList<>();
        Log.i("TAG", "--->DADADA" + solicitud);
        for (int i = 0; i < this.solicitudesPendientes.size(); i++)
            if (this.solicitudesPendientes.get(i).getKey().equals(solicitud.getKey())) {
                this.solicitudesPendientes.remove(i);
                this.solicitudesPendientes.add(i, solicitud);
                return;
            }

        this.solicitudesPendientes.add(solicitud);
    }


    public List<Amigos> getAmigos() {
        if (this.amigos == null)
            this.amigos = new ArrayList<>();
        return amigos;
    }

    public void setAmigo(Amigos amigo) {
        if (this.amigos == null)
            this.amigos = new ArrayList<>();
        Log.i("TAG", "--->DADADA" + amigo);
        for (int i = 0; i < this.amigos.size(); i++)
            if (this.amigos.get(i).getId().equals(amigo.getId())) {
                this.amigos.remove(i);
                this.amigos.add(i, amigo);
                return;
            }

        this.amigos.add(amigo);
    }

    public List<Grupo> getGrupos() {
        if (this.grupos == null)
            this.grupos = new ArrayList<>();
        return grupos;
    }

    public void setGrupos(Grupo grupo) {
        if (this.grupos == null)
            this.grupos = new ArrayList<>();
        Log.i("GRUPOS", "---> DATA " + grupo.toString());
        for (int i = 0; i < this.grupos.size(); i++)
            if (this.grupos.get(i).getKey().equals(grupo.getKey())) {
                this.grupos.remove(i);
                this.grupos.add(i, grupo);
                return;
            }
        this.grupos.add(grupo);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", apodo='" + apodo + '\'' +
                ", clave='" + clave + '\'' +
                ", email='" + email + '\'' +
                ", foto='" + foto + '\'' +
                ", genero='" + genero + '\'' +
                ", pais='" + pais + '\'' +
                ", puntos='" + puntos + '\'' +
                ", ranking='" + ranking + '\'' +
                ", chat_status='" + chat_status + '\'' +
                '}';
    }

    public void deleteFriend(String id) {
        if (this.amigos == null || id == null)
            this.amigos = new ArrayList<>();
        for (int i = 0; i < this.amigos.size(); i++)
            if (this.amigos.get(i).getId().equals(id)) {
                this.amigos.remove(i);
            }
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void deleteSolicitudPendientes(String id) {
        if (this.solicitudesPendientes == null || id == null)
            this.solicitudesPendientes = new ArrayList<>();

        for (int i = 0; i < this.solicitudesPendientes.size(); i++)
            if (this.solicitudesPendientes.get(i).getKey().equals(id)) {
                this.solicitudesPendientes.remove(i);
            }
    }


    public void deleteUsuarioConversaciones(String id) {
        if (this.usuarioConversations == null || id == null)
            this.usuarioConversations = new ArrayList<>();

        for (int i = 0; i < this.usuarioConversations.size(); i++)
            if (this.usuarioConversations.get(i).getId().equals(id)) {
                this.usuarioConversations.remove(i);
            }
    }

    public void deleteSolicitudEnviadas(String id) {
        if (this.solicitesEnviadas == null || id == null)
            this.solicitesEnviadas = new ArrayList<>();

        for (int i = 0; i < this.solicitesEnviadas.size(); i++)
            if (this.solicitesEnviadas.get(i).getKey().equals(id)) {
                this.solicitesEnviadas.remove(i);
            }
    }

    public void setUser(UserItem user) {
        if (user.getNombre() == null)
            return;
        this.nombre = user.getNombre();
        this.apodo = user.getApodo();
    }
}
