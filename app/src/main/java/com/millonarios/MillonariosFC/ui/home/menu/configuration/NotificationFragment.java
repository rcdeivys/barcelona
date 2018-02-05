package com.millonarios.MillonariosFC.ui.home.menu.configuration;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.commons.DBController;
import com.millonarios.MillonariosFC.commons.Services.NotificationSettingController;
import com.millonarios.MillonariosFC.db.NotificationSetting;
import com.millonarios.MillonariosFC.utils.FCMillonariosTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Leonardojpr on 11/26/17.
 */

public class NotificationFragment extends Fragment {

    public static final String TAG = NotificationFragment.class.getSimpleName();
    private static final int REMINDER = 0;
    private static final int ALIGMENT = 1;
    private static final int START_GAME = 2;
    private static final int GOALS = 3;
    private static final int OUTSTANDIGN_NEWS_AND_VIDEO = 4;
    Unbinder unbinder;

    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_enable_notification)
    FCMillonariosTextView btnEnableNotification;
    @BindView(R.id.switch_reminder)
    public Switch aSwitchReminder;
    @BindView(R.id.switch_aligment)
    public Switch aSwitchAligment;
    @BindView(R.id.switch_start_game)
    public Switch aSwitchStartGame;
    @BindView(R.id.switch_goals)
    public Switch aSwitchGoals;
    @BindView(R.id.switch_outstanding)
    public Switch aSwitchDestacados;

    List<NotificationSetting> itemList;

    LinearLayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification_configuration, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    public void init() {
        itemList = DBController.getControler().geNotificationSettingList(getActivity());
        Log.d(TAG, "size " + itemList.size() + " nombre : " + itemList.get(0).getTopic());
        aSwitchReminder.setChecked(itemList.get(0).getStatus());
        aSwitchAligment.setChecked(itemList.get(1).getStatus());
        aSwitchStartGame.setChecked(itemList.get(2).getStatus());
        aSwitchGoals.setChecked(itemList.get(3).getStatus());
        aSwitchDestacados.setChecked(itemList.get(4).getStatus());

        for (NotificationSetting notificationSetting : itemList) {
            Log.d(TAG, "topic " + notificationSetting.getTopic() + " suscribe " + notificationSetting.getStatus());
        }
    }

    @OnCheckedChanged({R.id.switch_reminder, R.id.switch_aligment, R.id.switch_start_game, R.id.switch_goals, R.id.switch_outstanding})
    public void onRadioButtonCheckChanged(CompoundButton button, boolean checked) {
        onCheckedChangedSwitch(button, checked);
    }

    private void onCheckedChangedSwitch(View button, final boolean checked) {
        switch (button.getId()) {
            case R.id.switch_reminder:
                itemList.get(REMINDER).setStatus(checked);
                break;
            case R.id.switch_aligment:
                itemList.get(ALIGMENT).setStatus(checked);
                break;
            case R.id.switch_start_game:
                itemList.get(START_GAME).setStatus(checked);
                break;
            case R.id.switch_goals:
                itemList.get(GOALS).setStatus(checked);
                break;
            case R.id.switch_outstanding:
                itemList.get(OUTSTANDIGN_NEWS_AND_VIDEO).setStatus(checked);
                break;
        }
    }


    @OnClick(R.id.btn_save)
    public void saveNotificationConfiguration() {
        NotificationSettingController.saveTopicStatusFirebase(getContext(), itemList);
        Toast.makeText(getActivity(), "Notificaciones actualizadas con éxito", Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.btn_enable_notification)
    public void initDisableNotification() {
        Intent intent = new Intent();
        intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
        intent.putExtra("app_package", getActivity().getPackageName());
        intent.putExtra("app_uid", getActivity().getApplicationInfo().uid);
        intent.putExtra("android.provider.extra.APP_PACKAGE", getActivity().getPackageName());

        getActivity().startActivity(intent);

    }
}
