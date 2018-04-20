package com.BarcelonaSC.BarcelonaApp.ui.wall.singlepost.mvp;

import com.BarcelonaSC.BarcelonaApp.app.api.WallApi;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.app.network.NetworkCallBack;
import com.BarcelonaSC.BarcelonaApp.models.WallItem;
import com.BarcelonaSC.BarcelonaApp.ui.wall.singlepost.SinglePostResponse;

/**
 * Created by Leonardojpr on 1/16/18.
 */

public class SinglePostModel extends NetworkCallBack<SinglePostResponse> {
    private static final String TAG = SinglePostModel.class.getSimpleName();
    private WallApi wallApi;

    SinglePostContract.ModelResultListener result;

    public SinglePostModel(WallApi wallApi) {
        this.wallApi = wallApi;
    }

    public void loadComment(String id_post, int page, SinglePostContract.ModelResultListener result) {
        this.result = result;
        wallApi.getIDPost(SessionManager.getInstance().getSession().getToken(), id_post).enqueue(this);
    }

    @Override
    public void onRequestSuccess(SinglePostResponse response) {
        if (response.getStatus().equals("exito")) {
            WallItem wallItem = new WallItem();
            wallItem.setIdpost(response.getData().get(0).getIdpost());
            wallItem.setMensaje(response.getData().get(0).getMensaje());
            wallItem.setFoto(response.getData().get(0).getFoto());
            wallItem.setFecha(response.getData().get(0).getFecha());
            wallItem.setUsuario(response.getData().get(0).getUsuario());
            wallItem.setNcomentarios(response.getData().get(0).getNcomentarios());
            wallItem.setNaplausos(response.getData().get(0).getNaplausos());
            wallItem.setYaaplaudio(response.getData().get(0).getYaaplaudio());
            wallItem.setUsuariosAplausos(response.getData().get(0).getUsuariosAplausos());
            result.onWallCommentPost(wallItem);
        }
    }

    @Override
    public void onRequestFail(String errorMessage, int errorCode) {
        result.onWallFailed(errorMessage);
    }

}
