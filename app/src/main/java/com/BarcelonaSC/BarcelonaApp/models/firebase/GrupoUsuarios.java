package com.BarcelonaSC.BarcelonaApp.models.firebase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos on 24/01/2018.
 */

public class GrupoUsuarios {

    String idGrupo;
    List<String> usuarios;

    public GrupoUsuarios() {
        usuarios = new ArrayList<>();
    }

    public String getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(String idGrupo) {
        this.idGrupo = idGrupo;
    }

    public List<String> getUsuarios() {
        return usuarios;
    }

    public void setUsuario(String usuario) {
        this.usuarios.add(usuario);
    }
}
