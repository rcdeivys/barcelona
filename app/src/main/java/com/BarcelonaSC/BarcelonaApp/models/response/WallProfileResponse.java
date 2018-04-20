package com.BarcelonaSC.BarcelonaApp.models.response;

import com.BarcelonaSC.BarcelonaApp.models.WallItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WallProfileResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("post")
    @Expose
    private List<WallItem> post;
    @SerializedName("usuario")
    @Expose
    private Usuario_ usuario;
    @SerializedName("aplausos_recibidos")
    @Expose
    private Integer aplausosRecibidos;
    @SerializedName("comentario_recibidos")
    @Expose
    private Integer comentarioRecibidos;
    @SerializedName("comentario_dados")
    @Expose
    private Integer comentarioDados;
    @SerializedName("publicaciones")
    @Expose
    private Integer publicaciones;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<WallItem> getPost() {
        return post;
    }

    public void setPost(List<WallItem> post) {
        this.post = post;
    }

    public Usuario_ getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario_ usuario) {
        this.usuario = usuario;
    }

    public Integer getAplausosRecibidos() {
        return aplausosRecibidos;
    }

    public void setAplausosRecibidos(Integer aplausosRecibidos) {
        this.aplausosRecibidos = aplausosRecibidos;
    }

    public Integer getComentarioRecibidos() {
        return comentarioRecibidos;
    }

    public void setComentarioRecibidos(Integer comentarioRecibidos) {
        this.comentarioRecibidos = comentarioRecibidos;
    }

    public Integer getComentarioDados() {
        return comentarioDados;
    }

    public void setComentarioDados(Integer comentarioDados) {
        this.comentarioDados = comentarioDados;
    }

    public Integer getPublicaciones() {
        return publicaciones;
    }

    public void setPublicaciones(Integer publicaciones) {
        this.publicaciones = publicaciones;
    }

    public class Usuario_ {

        @SerializedName("nombre")
        @Expose
        private String nombre;
        @SerializedName("apellido")
        @Expose
        private String apellido;
        @SerializedName("apodo")
        @Expose
        private String apodo;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("foto")
        @Expose
        private String foto;
        @SerializedName("dede")
        @Expose
        private String dede;

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
            return apodo;
        }

        public void setApodo(String apodo) {
            this.apodo = apodo;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFoto() {
            return foto;
        }

        public void setFoto(String foto) {
            this.foto = foto;
        }

        public String getDede() {
            return dede;
        }

        public void setDede(String dede) {
            this.dede = dede;
        }

    }


}
