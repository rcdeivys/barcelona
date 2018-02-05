package com.millonarios.MillonariosFC.models.firebase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quint on 1/28/2018.
 */

public class UsuarioGrupo {

    String userId;
    List<String> grupos;

    public UsuarioGrupo() {
        this.grupos = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setGrupo(String grupoId) {
        grupos.add(grupoId);
    }

    public List<String> getGrupos() {
        return grupos;
    }

    public String toString(){
        if(grupos!=null)
            return "{ id: "+userId+" cant_grupos: "+grupos.size()+" }";
        else
            return "{ id: "+userId+" cant_grupos: 0 }";
    }
}
