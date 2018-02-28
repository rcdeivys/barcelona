package com.BarcelonaSC.BarcelonaApp.ui.monumentals;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.CustomTabLayout;
import com.BarcelonaSC.BarcelonaApp.utils.CustomViewPager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

        final SharedPreferences preferences = getActivity().getSharedPreferences(Constant.Key.MONUMETAL_ID, Context.MODE_PRIVATE);
        boolean accepted = preferences.getBoolean(Constant.Key.MONUMETAL_ID, false);
        if (!accepted) {
            initDialog();
        } else {
            initViewPager();
        }
    }

    private void initViewPager() {
        monumentalNewsFragment = new MonumentalNewsFragment();
        monumentalFragment = new MonumentalFragment();
        monumentalRankingFragment = new MonumentalRankingFragment();

        pager.setAdapter(new MonumentalPagerAdapter(getChildFragmentManager(), getActivity(), monumentalNewsFragment, monumentalFragment, monumentalRankingFragment));
        tabs.setupWithViewPager(pager);
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
                if (sessionManager.getUser().getFechaNacimiento() != null && !sessionManager.getUser().getFechaNacimiento().isEmpty()) {
                    if (dobdateValidate(sessionManager.getUser().getFechaNacimiento())) {
                        SharedPreferences.Editor editor = getActivity().getSharedPreferences(Constant.Key.MONUMETAL_ID, Context.MODE_PRIVATE).edit();
                        editor.putBoolean(Constant.Key.MONUMETAL_ID, true);
                        editor.apply();
                    }
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

    public static boolean dobdateValidate(String date) {
        boolean result = false;
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        try {
            Date parseddate = sdf.parse(date);
            Calendar c2 = Calendar.getInstance();
            c2.add(Calendar.YEAR, -18);
            if (parseddate.before(c2.getTime())) {
                result = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

}