package com.millonarios.MillonariosFC.app.api;

import com.millonarios.MillonariosFC.models.SendCode;
import com.millonarios.MillonariosFC.models.response.SendCodeResponse;
import com.millonarios.MillonariosFC.models.response.SendEmailResponse;
import com.millonarios.MillonariosFC.models.SendEmail;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Leonardojpr on 11/13/17.
 */

public interface RecoveryPasswordApi {
       @POST("recuperar_clave")
        Call<SendEmailResponse> sendEmail(@Body SendEmail recoverBody);

        @POST("ingresar_con_pin")
        Call<SendCodeResponse> sendCode(@Body SendCode pinBody);
}
