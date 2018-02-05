package com.millonarios.MillonariosFC.ui.home.menu.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.app.App;
import com.millonarios.MillonariosFC.app.manager.ConfigurationManager;
import com.millonarios.MillonariosFC.commons.BaseFragment;
import com.millonarios.MillonariosFC.models.UserItem;
import com.millonarios.MillonariosFC.ui.home.HomeActivity;
import com.millonarios.MillonariosFC.ui.profile.di.DaggerProfileComponent;
import com.millonarios.MillonariosFC.ui.profile.di.ProfileModule;
import com.millonarios.MillonariosFC.ui.profile.fragments.HinchaFragment;
import com.millonarios.MillonariosFC.ui.profile.fragments.InfoAccountFragment;
import com.millonarios.MillonariosFC.ui.profile.mvp.ProfileContract;
import com.millonarios.MillonariosFC.ui.profile.mvp.ProfilePresenter;
import com.millonarios.MillonariosFC.ui.profile.views.adapters.ProfileViewPager;
import com.millonarios.MillonariosFC.utils.CustomTabLayout;
import com.millonarios.MillonariosFC.utils.CustomViewPager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Carlos on 01/11/2017.
 */

public class ProfileFragment extends BaseFragment implements ProfileContract.View {

    public static final String TAG = ProfileFragment.class.getSimpleName();


    @BindView(R.id.tabs)
    CustomTabLayout tabs;
    @BindView(R.id.pager)
    CustomViewPager pager;
    @BindView(R.id.progressBar_profile)
    RelativeLayout progressBar;

    HinchaFragment hinchaFragment;
    InfoAccountFragment infoAccountFragment;

    ProfileViewPager profileViewPager;

    boolean isFirst = true;

    String[] titles = new String[2];

    @Inject
    public ProfilePresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());

        initComponent();
        initViewPager();
        // presenter.loadUser(((HomeActivity) getActivity()).sessionManager.getSession().getToken());
    }

    public void initComponent() {
        DaggerProfileComponent.builder()
                .appComponent(App.get().component())
                .profileModule(new ProfileModule(this))
                .build().inject(ProfileFragment.this);
    }

    @Override
    public void setUser(UserItem data) {
        if (getActivity() != null) {
            ((HomeActivity) getActivity()).sessionManager.setUser(data);
            infoAccountFragment.setDataView(data);
            hinchaFragment.updateDataView(data);
            ((HomeActivity) getActivity()).updateHeader();
        }
    }

    @Override
    public void updateUser() {
        if (getActivity() != null) {
            presenter.loadUser(((HomeActivity) getActivity()).sessionManager.getSession().getToken());
            Toast.makeText(getActivity(), R.string.data_was_updated, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showToastError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
    }

    public void initViewPager() {

        hinchaFragment = new HinchaFragment();
        infoAccountFragment = new InfoAccountFragment();
        titles[0] = ConfigurationManager.getInstance().getConfiguration().getTit1_1();
        titles[1] = ConfigurationManager.getInstance().getConfiguration().getTit1_2();

        profileViewPager = new ProfileViewPager(getChildFragmentManager(), getActivity(), hinchaFragment, infoAccountFragment, titles);

        pager.setPagingEnabled(true);
        pager.setAdapter(profileViewPager);

        tabs.setVisibility(View.VISIBLE);
        tabs.setupWithViewPager(pager);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getChildFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
