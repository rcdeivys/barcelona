package com.BarcelonaSC.BarcelonaApp.ui.referredto.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.commons.BaseFragment;
import com.BarcelonaSC.BarcelonaApp.models.ReferredData;
import com.BarcelonaSC.BarcelonaApp.ui.referredto.fragments.adapters.ReferredParser;
import com.BarcelonaSC.BarcelonaApp.ui.referredto.fragments.adapters.ReferredRecyclerAdapter;
import com.BarcelonaSC.BarcelonaApp.ui.referredto.fragments.di.DaggerReferredComponent;
import com.BarcelonaSC.BarcelonaApp.ui.referredto.fragments.di.ReferredModule;
import com.BarcelonaSC.BarcelonaApp.ui.referredto.fragments.mvp.ReferredContract;
import com.BarcelonaSC.BarcelonaApp.ui.referredto.fragments.mvp.ReferredPresenter;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.EndlessScrollListener;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by RYA-Laptop on 05/01/2018.
 */

public class ReferredListFragment extends BaseFragment implements ReferredContract.View, ReferredRecyclerAdapter.StatusInactiveListener {

    private static final String SHOW = "show_instr";
    private static String TAG = ReferredListFragment.class.getSimpleName();
    ArrayList<ReferredParser> dataObjects;
    @BindView(R.id.referidos_list)
    RecyclerView referidosList;
    @BindView(R.id.lin_referred_by)
    LinearLayout linReferredBy;
    @BindView(R.id.referido_por)
    TextView referidoPor;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.other_swipe_container)
    SwipeRefreshLayout otherSwipeContainer;
    @BindView(R.id.btn_back)
    AppCompatImageButton btnBack;
    @BindView(R.id.btn_share)
    Button btnShare;
    @BindView(R.id.btn_instrucciones)
    Button btnInst;
    @BindView(R.id.btn_terminos)
    Button btnTerms;
    @BindView(R.id.no_referral)
    RelativeLayout noReferral;
    @BindView(R.id.total_referidos)
    TextView totalReferidos;
    String token;

    SessionManager sessionManager;
    ReferredRecyclerAdapter adapter;
    private LinearLayoutManager mLayoutManager;

    int page = 1;
    private int currentPage = 1;
    private int countItem = 0;
    private int maxcount = 10;

    @Inject
    ReferredPresenter presenter;

    Unbinder unbinder;

    public void initComponent() {
        DaggerReferredComponent.builder()
                .appComponent(App.get().component())
                .referredModule(new ReferredModule(this))
                .build().inject(ReferredListFragment.this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponent();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_referred_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sessionManager = new SessionManager(getActivity());
        token = sessionManager.getSession().getToken();

        String referido = sessionManager.getUser().getReferido();

        if (referido != null) {
            if (referido.length() > 0) {
                referidoPor.setText(referido);
            } else {
                linReferredBy.setVisibility(View.GONE);
            }
        } else {
            linReferredBy.setVisibility(View.GONE);
        }

        presenter.onAttach(this);

        presenter.getReferidos(token, String.valueOf(page));

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                swipeContainer.setRefreshing(true);
                presenter.getReferidos(token, String.valueOf(page));
            }
        });

        otherSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                otherSwipeContainer.setRefreshing(true);
                presenter.getReferidos(token, String.valueOf(page));
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share();
            }
        });

        btnInst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.ref_container, ReferredToFragment.instance(true))
                        .commit();
            }
        });

        btnTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTermsOn();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.onDetach();
    }

    public void share() {
        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND);
        // para que salga la url con texto previo: "Ola ke ase \n" + resto del text.
        share.putExtra(Intent.EXTRA_TEXT, "¡YA SOY HINCHA OFICIAL!\n"
                + "¡Demuestra tu compromiso por el TORERO y sigamos demostrando porque somos el ÍDOLO DEL ECUADOR!\n"
                + "Regístrate y descárgala en el siguiente link:\n"
                + Commons.getString(R.string.url_api).replace("api/", "") + "compartir/referidos/" + sessionManager.getUser().getCodigo());
        share.setType("text/plain");
        startActivity(Intent.createChooser(share, "Compartir en: "));
    }

    private void setTermsOn() {
        ReferredTermsFragment referredTermsFragment = new ReferredTermsFragment();
        showDialogFragment(referredTermsFragment);
    }

    private void showDialogFragment(DialogFragment dialogFragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(dialogFragment, dialogFragment.getTag());
        ft.commitAllowingStateLoss();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setRefreshing(boolean state) {
        swipeContainer.setRefreshing(state);
        otherSwipeContainer.setRefreshing(state);
    }

    @Override
    public void showToast(String message) {
        showToast(message, Toast.LENGTH_SHORT);
        setRefreshing(false);
        hideProgress();
    }

    @Override
    public void setReferidos(ReferredData referidos) {
        if (referidos.getActivos() != 0 && referidos.getActivos() != null) {
            totalReferidos.setText(String.valueOf(referidos.getActivos()));
        }
        if (page == 1) {
            otherSwipeContainer.setRefreshing(false);
            if (referidos.getReferidos().size() > 0) {
                swipeContainer.setVisibility(View.VISIBLE);
                otherSwipeContainer.setVisibility(View.GONE);
                setReferredList(referidos);
            } else {
                swipeContainer.setVisibility(View.GONE);
                otherSwipeContainer.setVisibility(View.VISIBLE);
            }
        } else {
            if (referidos.getReferidos().size() > 0) {
                page = +1;
                setAddReferredList(referidos);
            }
        }
    }

    private void setReferredList(ReferredData referidos) {
        dataObjects = new ArrayList<>();
        for (int i = 0; i < referidos.getReferidos().size(); i++) {
            dataObjects.add(new ReferredParser(referidos.getReferidos().get(i).getNombre()
                    , referidos.getReferidos().get(i).getApellido()
                    , referidos.getReferidos().get(i).getEmail()
                    , referidos.getReferidos().get(i).getApodo()
                    , referidos.getReferidos().get(i).getCelular()
                    , referidos.getReferidos().get(i).getPais()
                    , referidos.getReferidos().get(i).getGenero()
                    , referidos.getReferidos().get(i).getFoto()
                    , referidos.getReferidos().get(i).getEstatus()));
        }

        mLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        referidosList.setLayoutManager(mLayoutManager);
        adapter = new ReferredRecyclerAdapter(getActivity(), dataObjects);
        adapter.setStatusInactiveListener(this);
        referidosList.setAdapter(adapter);
        if (referidos.getReferidos().size() == maxcount && page == 1) {
            referidosList.addOnScrollListener(initRecyclerViewScroll());
        }
        notifyDataSetChanged();
    }

    private void setAddReferredList(ReferredData referidos) {
        dataObjects = new ArrayList<>();
        for (int i = 0; i < referidos.getReferidos().size(); i++) {
            dataObjects.add(new ReferredParser(referidos.getReferidos().get(i).getNombre()
                    , referidos.getReferidos().get(i).getApellido()
                    , referidos.getReferidos().get(i).getEmail()
                    , referidos.getReferidos().get(i).getApodo()
                    , referidos.getReferidos().get(i).getCelular()
                    , referidos.getReferidos().get(i).getPais()
                    , referidos.getReferidos().get(i).getGenero()
                    , referidos.getReferidos().get(i).getFoto()
                    , referidos.getReferidos().get(i).getEstatus()));
        }
        adapter.referred.addAll(this.dataObjects.size(), dataObjects);
        adapter.notifyDataSetChanged();
    }

    private EndlessScrollListener initRecyclerViewScroll() {
        return new EndlessScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                if (!swipeContainer.isRefreshing()) {
                    page = current_page;
                    presenter.getReferidos(token, String.valueOf(current_page));
                }
            }
        };
    }

    private void notifyDataSetChanged() {
        setRefreshing(false);
        hideProgress();
    }

    private void inactiveDialog() {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialoglayout = inflater.inflate(R.layout.dialog_referred_inactive, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialoglayout);
        final AlertDialog alertDialog = builder.show();
        TextView fcMillonariosTextView = dialoglayout.findViewById(R.id.text_description);
        fcMillonariosTextView.setText("Este amigo aún no está activo en la APP.\n" +
                "Para que tus amigos cuenten como referidos deben:\n\n" +
                "1. Registrarse\n" +
                "2. Descargar la App\n" +
                "3. Mantenerse activos al menos una vez a la semana.");

        Button btnYes = dialoglayout.findViewById(R.id.aceptar);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }

    @Override
    public void onInactiveClickListener() {
        inactiveDialog();
    }

}