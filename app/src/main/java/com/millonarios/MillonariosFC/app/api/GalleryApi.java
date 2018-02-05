package com.millonarios.MillonariosFC.app.api;

import com.millonarios.MillonariosFC.models.response.GalleryResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Leonardojpr on 10/16/17.
 */

public interface GalleryApi {

    @GET("noticia_fotos/{id}")
    Call<GalleryResponse> getGallery(@Path("id") int id);
}
