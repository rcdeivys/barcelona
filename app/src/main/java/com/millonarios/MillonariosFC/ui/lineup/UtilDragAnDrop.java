package com.millonarios.MillonariosFC.ui.lineup;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.FrameLayout;

import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.app.App;
import com.millonarios.MillonariosFC.app.manager.SessionManager;
import com.millonarios.MillonariosFC.models.IdealElevenData;
import com.millonarios.MillonariosFC.models.NominaItem;
import com.millonarios.MillonariosFC.models.response.SendMyIdealElevenData;
import com.millonarios.MillonariosFC.utils.CircleDragDrop;
import com.millonarios.MillonariosFC.utils.Constants.Constant;
import com.millonarios.MillonariosFC.models.MyIdealElevenData;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UtilDragAnDrop {

    private static int cancha210toSizeX(int x, int xSizeLayout) {
        return (x * xSizeLayout) / Constant.CanchaSize.CANCHA_LARGO;
    }

    public static int sizeXToCancha210(int x, int xSizeLayout) {
        return (x * Constant.CanchaSize.CANCHA_LARGO) / xSizeLayout;
    }


    private static int cancha310toSizeY(int y, int ySizeLayout) {
        return (y * ySizeLayout) / Constant.CanchaSize.CANCHA_ALTO;
    }

    public static int sizeYToCancha310(int y, int ySizeLayout) {
        return (y * Constant.CanchaSize.CANCHA_ALTO) / ySizeLayout;
    }


    public static int cpSizeImage(int xSizeLayout, int ySizeLayout) {
        return (xSizeLayout + ySizeLayout) * Constant.CanchaSize.SIZE_IMAGEN
                / (Constant.CanchaSize.CANCHA_ALTO + Constant.CanchaSize.CANCHA_LARGO);
    }


    public static void paintPlayerDropDialog(FrameLayout soccesFieldView, List<IdealElevenData> idealElevenDatas
            , Context context) {

        int sizeImage = cpSizeImage(soccesFieldView.getWidth(), soccesFieldView.getHeight());

        for (IdealElevenData idealElevenData : idealElevenDatas) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(sizeImage, sizeImage);
            CircleDragDrop circleImageView = new CircleDragDrop(context);
            Glide.with(App.getAppContext())
                    .load(idealElevenData.getPlayerPlayByPlay().getFoto())
                    .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta))
                    .into(circleImageView);
            circleImageView.setBorderColor(ContextCompat.getColor(App.getAppContext(), R.color.white));
            circleImageView.setBorderWidth(2);
            circleImageView.setPlayerPlayByPlay(idealElevenData.getPlayerPlayByPlay());
            circleImageView.setPositionX(idealElevenData.getPositionX());
            circleImageView.setPositionY(idealElevenData.getPositionY());

            int x = cancha210toSizeX(idealElevenData.getPositionX(), soccesFieldView.getWidth());
            int y = cancha310toSizeY(idealElevenData.getPositionY(), soccesFieldView.getHeight());

            if (x + sizeImage > soccesFieldView.getWidth())
                x = x - (x + sizeImage - soccesFieldView.getWidth());
            if (y + sizeImage > soccesFieldView.getHeight())
                y = y - (y + sizeImage - soccesFieldView.getHeight());

            params.leftMargin = x;
            params.topMargin = y;
            soccesFieldView.addView(circleImageView, params);
        }
    }

    public static void paintPlayerDrop(List<CircleImageView> circleImageViews
            , FrameLayout soccesFieldView, List<IdealElevenData> idealElevenDatas
            , Context context) {

        if (circleImageViews != null) {
            for (CircleImageView circleImageView : circleImageViews)
                soccesFieldView.removeView(circleImageView);
            circleImageViews.clear();
            soccesFieldView.invalidate();
        }
        int sizeImage = cpSizeImage(soccesFieldView.getWidth(), soccesFieldView.getHeight());
        for (IdealElevenData idealElevenData : idealElevenDatas) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(sizeImage, sizeImage);
            CircleImageView circleImageView = new CircleImageView(context);
            Glide.with(App.getAppContext())
                    .load(idealElevenData.getPlayerPlayByPlay().getFoto())
                    .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta))
                    .into(circleImageView);
            circleImageView.setBorderColor(ContextCompat.getColor(App.getAppContext(), R.color.white));
            circleImageView.setBorderWidth(2);

            int x = cancha210toSizeX(idealElevenData.getPositionX(), soccesFieldView.getWidth());
            int y = cancha310toSizeY(idealElevenData.getPositionY(), soccesFieldView.getHeight());

            if (x + sizeImage > soccesFieldView.getWidth())
                x = x - (x + sizeImage - soccesFieldView.getWidth());
            if (y + sizeImage > soccesFieldView.getHeight())
                y = y - (y + sizeImage - soccesFieldView.getHeight());


            params.leftMargin = x;
            params.topMargin = y;
            soccesFieldView.addView(circleImageView, params);
            if (circleImageViews != null)
                circleImageViews.add(circleImageView);
        }
    }

    public static SendMyIdealElevenData llenardata(List<IdealElevenData> idealElevenData, String imageEncode) {

        SendMyIdealElevenData sendMyIdealElevenData = new SendMyIdealElevenData();
        List<MyIdealElevenData> myIdealElevenList = new ArrayList<>();

        sendMyIdealElevenData.setToken(new SessionManager(App.getAppContext()).getSession().getToken());
        Log.i("TAG", "--->TOKEN: " + sendMyIdealElevenData.getToken() + "  " + imageEncode);
        sendMyIdealElevenData.setFoto("data:image/png;base64," + imageEncode);
        for (int i = 0; i < idealElevenData.size(); i++) {

            MyIdealElevenData myIdealElevenData = new MyIdealElevenData();
            myIdealElevenData.setIdjugador(idealElevenData.get(i).getPlayerPlayByPlay().getIdJugador());
            myIdealElevenData.setX(idealElevenData.get(i).getPositionX());
            myIdealElevenData.setY(idealElevenData.get(i).getPositionY());
            myIdealElevenList.add(myIdealElevenData);
        }
        sendMyIdealElevenData.setJugadores(myIdealElevenList);
        return sendMyIdealElevenData;
    }


    public static List<IdealElevenData> generateIdealElevenData(List<MyIdealElevenData> myIdealElevenData, List<NominaItem> playersList) {


        List<IdealElevenData> idealElevenDatas = new ArrayList<>();
        IdealElevenData idealElevenData;
        for (int i = 0; i < myIdealElevenData.size(); i++) {

            if (sameId(myIdealElevenData.get(i).getIdjugador(), playersList) != null) {
                idealElevenData = new IdealElevenData();
                idealElevenData.setPlayerPlayByPlay(sameId(myIdealElevenData.get(i).getIdjugador(), playersList));
                idealElevenData.setPositionX(myIdealElevenData.get(i).getX());
                idealElevenData.setPositionY(myIdealElevenData.get(i).getY());
                idealElevenDatas.add(idealElevenData);
            }

        }


        return idealElevenDatas;
    }

    private static NominaItem sameId(int id, List<NominaItem> playersSummoned) {

        for (NominaItem playerSummoned : playersSummoned) {
            if (playerSummoned.getIdJugador() == id) {
                return playerSummoned;
            }
        }
        return null;
    }
}
