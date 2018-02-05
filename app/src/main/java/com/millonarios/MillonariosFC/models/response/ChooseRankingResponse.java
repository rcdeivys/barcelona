package com.millonarios.MillonariosFC.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.millonarios.MillonariosFC.models.RespuestaData;

import java.util.List;

/**
 * Created by Carlos on 14/01/2018.
 */

public class ChooseRankingResponse {

    @SerializedName("respuestas")
    @Expose
    private List<RespuestaData> respuestas = null;

    public List<RespuestaData> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<RespuestaData> respuestas) {
        this.respuestas = respuestas;
    }

}
