package com.BarcelonaSC.BarcelonaApp.ui.chat.requests;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.commons.BaseEventBusFragment;
import com.BarcelonaSC.BarcelonaApp.models.firebase.FirebaseEvent;
import com.BarcelonaSC.BarcelonaApp.ui.chat.requests.adapters.RequestAdapter;
import com.BarcelonaSC.BarcelonaApp.ui.chat.requests.di.DaggerRequestComponent;
import com.BarcelonaSC.BarcelonaApp.ui.chat.requests.di.RequestModule;
import com.BarcelonaSC.BarcelonaApp.ui.chat.requests.mvp.RequestContract;
import com.BarcelonaSC.BarcelonaApp.ui.chat.requests.mvp.RequestPresenter;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

/**
 * Created by Carlos on 22/01/2018.
 */

public class RequestsFragment extends BaseEventBusFragment implements RequestContract.View, RequestAdapter.OnItemClickListener {

    public static final String TAG = RequestsFragment.class.getSimpleName();

    @BindView(R.id.rv_request)
    RecyclerView rv_request;

    @BindView(R.id.rv_suggest)
    RecyclerView rv_suggest;

    @BindView(R.id.tv_nro_solicitudes)
    FCMillonariosTextView tv_nro_solicitudes;

    @BindView(R.id.iv_msg_search)
    ImageView searchFriends;

    @BindView(R.id.et_msg_search)
    EditText searchKey;

    @Inject
    RequestPresenter presenter;

    LinearLayoutManager linearLayoutRequestManager;
    LinearLayoutManager linearLayoutSugeManager;
    Unbinder unbinder;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.ll_title_container_1)
    LinearLayout llTitleContainer1;
    @BindView(R.id.ll_title_container_2)
    LinearLayout llTitleContainer2;
    private RequestAdapter requestAdapter;
    private RequestAdapter suggerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponent();
        linearLayoutRequestManager = new LinearLayoutManager(getContext());
        linearLayoutSugeManager = new LinearLayoutManager(getContext());
    }

    public static RequestsFragment newInstance() {
        Bundle args = new Bundle();
        RequestsFragment fragment = new RequestsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public void initComponent() {
        DaggerRequestComponent.builder()
                .appComponent(App.get().component())
                .requestModule(new RequestModule(this))
                .build().inject(RequestsFragment.this);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request, container, false);

        unbinder = ButterKnife.bind(this, view);

        presenter.onAttach(this);
        showProgress();
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (searchKey.getText().toString().isEmpty()) {
                    swipeContainer.setRefreshing(true);
                    presenter.loadPendingRequest();
                } else {
                    swipeContainer.setRefreshing(false);
                }
            }
        });


        if (requestAdapter == null || suggerAdapter == null) {
            presenter.loadPendingRequest();
            presenter.loadSendRequest();
        } else {
            rv_request.setAdapter(requestAdapter);
            rv_request.setLayoutManager(linearLayoutRequestManager);
            rv_request.setAdapter(requestAdapter);
            rv_request.setLayoutManager(linearLayoutSugeManager);
            hideProgress();
        }

        return view;
    }

    @Override
    public void setRequestData(final List<RequestModelView> listRequest) {

        if (listRequest != null) {
            initRvAndAdapter(false);
            requestAdapter.setData(listRequest, false);
            setNumerosSolicitudes(listRequest.size());
            notifyDataSetChanged(false);
        }


    }

    @Override
    public void setSuggestData(List<RequestModelView> listRequest) {
        if (listRequest != null) {
            initRvAndAdapter(true);
            suggerAdapter.setData(listRequest, true);
            notifyDataSetChanged(true);
        }
    }

    private void initRvAndAdapter(boolean isSugger) {
        if (isSugger) {
            if (suggerAdapter == null) {
                suggerAdapter = new RequestAdapter(getContext(), this);
                rv_suggest.setAdapter(suggerAdapter);
                linearLayoutRequestManager.setAutoMeasureEnabled(true);
                rv_suggest.setLayoutManager(linearLayoutSugeManager);
                rv_suggest.setNestedScrollingEnabled(false);
            }
        } else if (requestAdapter == null) {
            requestAdapter = new RequestAdapter(getContext(), this);
            rv_request.setAdapter(requestAdapter);
            linearLayoutRequestManager.setAutoMeasureEnabled(true);
            rv_request.setLayoutManager(linearLayoutRequestManager);
            rv_request.setNestedScrollingEnabled(false);
        }

    }

    private void notifyDataSetChanged(boolean isSugger) {
        if (isSugger) {
            suggerAdapter.notifyDataSetChanged();
        } else {
            requestAdapter.notifyDataSetChanged();
        }
        setRefreshing(false);
        hideProgress();
    }


    private void setNumerosSolicitudes(int size) {

        if (size > 0) {
            tv_nro_solicitudes.setVisibility(View.VISIBLE);
            if (size < 99) {
                tv_nro_solicitudes.setText(String.valueOf(size));
            } else {
                tv_nro_solicitudes.setText("99+");
            }
        } else {
            tv_nro_solicitudes.setText("0");
        }
    }


    @OnTextChanged(value = R.id.et_msg_search, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTextChanged() {
        showProgress();
        presenter.findByName(searchKey.getText().toString());

    }

    @OnClick(R.id.iv_msg_search)
    void onFriendSearch() {

    }

    /**
     * interfaz requestAdapter onClickSimple
     *
     * @param requestModelView
     * @param TAG
     */
    @Override
    public void onClickItem(RequestModelView requestModelView, String TAG) {
        Log.i(TAG, "--->onClickItem TAG: " + TAG);

        switch (TAG) {
            case RequestConsts.TAG_ON_CLICK_ACEPTAR: {
                showToast("Solicitud Aceptada", Toast.LENGTH_SHORT);
                presenter.onClickAcceptUser(SessionManager.getInstance().getUser().getId_usuario(), requestModelView);
                break;
            }
            case RequestConsts.TAG_ON_CLICK_INVITED: {
                showToast("Solicitud Enviada", Toast.LENGTH_SHORT);
                presenter.onClickInvitedUser(SessionManager.getInstance().getUser().getId_usuario(), requestModelView);
                break;
            }
            case RequestConsts.TAG_ON_CLICK_CANCEL: {
                showToast("Solicitud Cancelada", Toast.LENGTH_SHORT);
                presenter.onClickCancelUser(SessionManager.getInstance().getUser().getId_usuario(), String.valueOf(requestModelView.getId()));
                break;
            }
            case RequestConsts.TAG_ON_OWNER_CLICK_CANCEL: {
                showToast("Solicitud Cancelada", Toast.LENGTH_SHORT);
                presenter.onClickCancelUser(String.valueOf(requestModelView.getId()), SessionManager.getInstance().getUser().getId_usuario());
                break;
            }
        }
    }

    @Override
    public void onLongClickItem(RequestModelView requestModelView) {

    }

    @Override
    public void setRefreshing(boolean state) {
        swipeContainer.setRefreshing(state);
    }

    @Override
    public void onCompletedSuccess(String Tag) {

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
    public void showToastError(String errror, String TAG) {
        Toast.makeText(getContext(), errror, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void prepareForSeach(boolean seach) {
        if (seach) {
            initRvAndAdapter(seach);
            suggerAdapter.setData(new ArrayList<RequestModelView>(), true);
            notifyDataSetChanged(true);
            llTitleContainer1.setVisibility(View.GONE);
            llTitleContainer2.setVisibility(View.GONE);
        } else {
            llTitleContainer1.setVisibility(View.VISIBLE);
            llTitleContainer2.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void refresh() {
        Log.i(TAG, "--->refresh()");
        presenter.loadPendingRequest();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDetach();
        unbinder.unbind();
    }

    @Subscribe
    public void onMessageEvent(FirebaseEvent event) {
        Log.i(TAG, "--->onMessageEvent REQUESTFRAGMENT");
        if (event.getEvent() == FirebaseEvent.EVENT.REFRESCAR_SOLICITUDES_ENVIADAS) {
            presenter.loadSendRequest();
        } else if (event.getEvent() == FirebaseEvent.EVENT.REFRESCAR_SOLICITUDES_PENDIENTES) {
            presenter.loadPendingRequest();

        }

    }
}
