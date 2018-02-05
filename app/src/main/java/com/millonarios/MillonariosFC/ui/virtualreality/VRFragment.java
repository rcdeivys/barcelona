package com.millonarios.MillonariosFC.ui.virtualreality;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.app.App;
import com.millonarios.MillonariosFC.commons.BaseFragment;
import com.millonarios.MillonariosFC.models.VideoReality;
import com.millonarios.MillonariosFC.ui.virtualreality.di.VRModule;
import com.millonarios.MillonariosFC.ui.virtualreality.mvp.VRContract;
import com.millonarios.MillonariosFC.ui.virtualreality.mvp.VRPresenter;
import com.millonarios.MillonariosFC.utils.Constants.Constant;
import com.millonarios.MillonariosFC.ui.virtualreality.di.DaggerVRComponent;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Carlos on 08/11/2017.
 */

public class VRFragment extends BaseFragment implements VRContract.View, VRAdapter.OnItemClickListener {


    public static final String TAG = VRFragment.class.getSimpleName();


    @BindView(R.id.rv_virtual_reality)
    RecyclerView rvVirtualReality;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.btn_top)
    ImageButton btnTop;
    Unbinder unbinder;


    private LinearLayoutManager linearLayoutManager;
    private VRAdapter vrAdapter;

    @Inject
    VRPresenter presenter;

    public static VRFragment newInstance() {

        return new VRFragment();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        linearLayoutManager = new LinearLayoutManager(getContext());
        initComponent();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vr, container, false);

        unbinder = ButterKnife.bind(this, view);
        presenter.onAttach(this);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(true);
                presenter.loadVideos();
            }
        });

        if (vrAdapter == null) {
            showProgress();
            presenter.loadVideos();
        } else {
            rvVirtualReality.setAdapter(vrAdapter);
            rvVirtualReality.setLayoutManager(linearLayoutManager);
        }

        btnTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvVirtualReality.smoothScrollToPosition(0);
            }
        });

        return view;
    }


    public void initComponent() {
        DaggerVRComponent.builder()
                .appComponent(App.get().component())
                .vRModule(new VRModule(this))
                .build().inject(VRFragment.this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDetach();
        unbinder.unbind();
    }


    private void initRvAndAdapter() {
        if (vrAdapter == null) {
            vrAdapter = new VRAdapter(this);
            rvVirtualReality.setAdapter(vrAdapter);
            rvVirtualReality.setLayoutManager(linearLayoutManager);
        }

    }

    private void notifyDataSetChanged() {
        vrAdapter.notifyDataSetChanged();
        setRefreshing(false);
        hideProgress();
    }

    @Override
    public void showProgress() {
        progressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressbar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setRefreshing(boolean state) {
        swipeContainer.setRefreshing(state);
    }

    @Override
    public void showToast(String message) {
        showToast(message, Toast.LENGTH_SHORT);
        setRefreshing(false);
        hideProgress();
    }

    @Override
    public void setVideos(ArrayList<VideoReality> videoRealities) {
        initRvAndAdapter();
        vrAdapter.setData(videoRealities);
        notifyDataSetChanged();
    }

    @Override
    public void navigateVirtualActivity(VideoReality videoReality) {
        Intent intent = new Intent(getActivity(), VirtualActivity.class);
        intent.putExtra(Constant.Key.VIRTUAL_REALITY_SECTION, videoReality);
        String url = videoReality.getUrl().replace("\\", "");
        intent.putExtra("url", url);
        navigator.navigateToActivity(intent);
    }

    @Override
    public void onClickItem(int position) {
        presenter.onclickVideoItem(position);
    }
}
