package com.millonarios.MillonariosFC.app.network;


import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.app.App;
import com.millonarios.MillonariosFC.utils.NoConnectivityException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Carlos on 10/10/2017.
 */

public abstract class NetworkCallBack<T> implements Callback<T> {

    private static final String TAG = NetworkCallBack.class.getSimpleName();

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            onRequestSuccess(response.body());

        } else if (response.errorBody() != null) {
            onRequestFail(App.getAppContext().getString(R.string.network_error) + " " + response.errorBody(), response.code());
        } else {
            onRequestFail(App.getAppContext().getString(R.string.network_error) + response.code(), response.code());
        }

    }

    public void processError() {

    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {

        if (t instanceof NoConnectivityException) {
            onRequestFail("Debes tener conexión a internet", 0);
        } else {
            onRequestFail("onFailure " + App.getAppContext().getString(R.string.network_error), 0);
        }
    }

    public abstract void onRequestSuccess(T response);

    public abstract void onRequestFail(String errorMessage, int errorCode);


}
