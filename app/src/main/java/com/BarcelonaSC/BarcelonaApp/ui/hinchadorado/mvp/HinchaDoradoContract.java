package com.BarcelonaSC.BarcelonaApp.ui.hinchadorado.mvp;

import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;
import com.BarcelonaSC.BarcelonaApp.models.SuscripcionData;

import java.util.List;

/**
 * Created by cesar on 24/2/2018.
 */

public class HinchaDoradoContract {

    public interface ModelResultListener {

        void onGetSuscripcionSuccess(List<SuscripcionData> suscripcionData);
        void onGetSuscripcionFailed();

        void onGetStatusSuscripcionSuccess(String status);
        void onGetStatusSuscripcionFailed();

        void onPutUserSuccess();
    }

    public interface Presenter extends MVPContract.Presenter<View> {

        void onClickItem(SuscripcionData suscripcionData);
        void loadSuscripcion();
        void getStatusSuscription();

    }

    public interface View {

        void updateSuscripcion(List<SuscripcionData> suscripcionData);
        void updateStatusSuscripcion(String status);
        void onFailedStatusSuscripcion();
        void onFailedUpdate(String mensaje);
        void refresh();

    }
}
