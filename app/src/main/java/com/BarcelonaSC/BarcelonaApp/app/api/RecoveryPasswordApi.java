package com.BarcelonaSC.BarcelonaApp.app.api;

import com.BarcelonaSC.BarcelonaApp.models.SendCode;
import com.BarcelonaSC.BarcelonaApp.models.response.SendCodeResponse;
import com.BarcelonaSC.BarcelonaApp.models.response.SendEmailResponse;
import com.BarcelonaSC.BarcelonaApp.models.SendEmail;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Leonardojpr on 11/13/17.
 */

public interface RecoveryPasswordApi {
       @POST("recuperar_clave_link")
        Call<SendEmailResponse> sendEmail(@Body SendEmail recoverBody);

        @POST("ingresar_con_pin")
        Call<SendCodeResponse> sendCode(@Body SendCode pinBody);
}
