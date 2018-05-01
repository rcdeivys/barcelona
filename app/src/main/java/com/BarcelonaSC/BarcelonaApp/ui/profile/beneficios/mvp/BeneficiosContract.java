package com.BarcelonaSC.BarcelonaApp.ui.profile.beneficios.mvp;

import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;
import com.BarcelonaSC.BarcelonaApp.models.BeneficioData;

import java.util.List;

/**
 * Created by Pedro Gomez on 20/02/2018.
 */

public class BeneficiosContract {

    public interface ModelResultListener {

        void onGetBeneficiosSuccess(List<BeneficioData> beneficios);
        void onGetBeneficiosFailed();

    }

    public interface Presenter extends MVPContract.Presenter<View> {

        void onClickItem(BeneficioData beneficio);
        void loadBeneficios();

    }

    public interface View {

        void updateBeneficios(List<BeneficioData> beneficios);
        void onFailedUpdate();
        void refresh();

    }

}
