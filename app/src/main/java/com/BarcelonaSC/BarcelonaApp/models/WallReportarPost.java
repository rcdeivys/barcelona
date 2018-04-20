package com.BarcelonaSC.BarcelonaApp.models;

/**
 * Created by Leonardojpr on 3/29/18.
 */

public class WallReportarPost {

    String token;
    String tipo;
    String post_id;
    String comentario_id;

    public WallReportarPost(String token, String tipo, String post_id, String comentario_id) {
        this.token = token;
        this.tipo = tipo;
        this.post_id = post_id;
        this.comentario_id = comentario_id;
    }
}
