package com.BarcelonaSC.BarcelonaApp.ui.monumentals;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.commons.BaseFragment;
import com.BarcelonaSC.BarcelonaApp.ui.home.HomeActivity;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.adapters.MonumentalPagerAdapter;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.MonumentalFragment;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.news.MonumentalNewsFragment;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.ranking.MonumentalRankingFragment;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.CustomDate;
import com.BarcelonaSC.BarcelonaApp.utils.CustomTabLayout;
import com.BarcelonaSC.BarcelonaApp.utils.CustomViewPager;
import com.BarcelonaSC.BarcelonaApp.utils.PreferenceManager;

import org.joda.time.Period;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RYA-Laptop on 16/02/2018.
 */

public class MonumentalMainFragment extends BaseFragment {

    public static final String TAG = MonumentalMainFragment.class.getSimpleName();

    @BindView(R.id.tabs)
    CustomTabLayout tabs;
    @BindView(R.id.pager)
    CustomViewPager pager;
    Dialog dialog;

    MonumentalNewsFragment monumentalNewsFragment;
    MonumentalFragment monumentalFragment;
    MonumentalRankingFragment monumentalRankingFragment;

    SessionManager sessionManager;

    boolean accepted;

    protected String subseccion;

    public static MonumentalMainFragment newInstance(String subseccion){
        MonumentalMainFragment fragment = new MonumentalMainFragment();
        Log.i(TAG, "newInstance: subseccion: ---> "+subseccion);
        try{
            fragment.subseccion = subseccion.toLowerCase();
        }catch (Exception e){
            fragment.subseccion = null;
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_monumentals, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        sessionManager = new SessionManager(getActivity());

        accepted = PreferenceManager.getInstance().getBoolean(Constant.Key.MONUMETAL_ID, false);

        //if (!accepted || !dobdateValidate()) {
        //    initDialog();
        //} else {
        initViewPager();
        //}
    }

    private void initViewPager() {
        monumentalNewsFragment = new MonumentalNewsFragment();
        monumentalFragment = new MonumentalFragment();
        monumentalRankingFragment = new MonumentalRankingFragment();

        pager.setAdapter(new MonumentalPagerAdapter(getChildFragmentManager(), getActivity(), monumentalNewsFragment, monumentalFragment, monumentalRankingFragment));
        tabs.setupWithViewPager(pager);
        try {
            switch(subseccion){
                case Constant.SubSeccion.GALERY:
                    pager.setCurrentItem(0);
                    break;
                case Constant.SubSeccion.VOTATION:
                    pager.setCurrentItem(1);
                    break;
                case Constant.SubSeccion.RANKING:
                    pager.setCurrentItem(2);
                    break;
            }
        }catch (Exception e){
            pager.setCurrentItem(0);
            Log.i(TAG, "initializeViewPager: ---> error: "+e.getLocalizedMessage()+" msg: "+e.getMessage());
        }
    }

    private void initDialog() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        final View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_monumental, null);
        Button accept = v.findViewById(R.id.btn_accept);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dobdateValidate()) {
                    PreferenceManager.getInstance().setBoolean(Constant.Key.MONUMETAL_ID, true);
                }
                initViewPager();
                dialog.dismiss();
            }
        });
        Button cancel = v.findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity) getActivity()).presenter.newsProfessional();
                dialog.dismiss();
            }
        });

        dialog.setContentView(v);
        dialog.show();
    }

    public static boolean dobdateValidate() {
        if (SessionManager.getInstance().getUser().getFechaNacimiento() != null && !SessionManager.getInstance().getUser().getFechaNacimiento().isEmpty()) {
            Period edad = CustomDate.diferenceBetewnDate(Commons.getDateString(SessionManager.getInstance().getUser().getFechaNacimiento()).getTime(), new Date().getTime());
            if (edad.getYears() > 18) {
                return true;
            }
        }

        return false;
    }

}