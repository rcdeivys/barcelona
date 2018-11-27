package com.BarcelonaSC.BarcelonaApp.ui.wall.list;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.commons.BaseFragment;
import com.BarcelonaSC.BarcelonaApp.commons.VideoActivity;
import com.BarcelonaSC.BarcelonaApp.eventbus.WallCreatePostEvent;
import com.BarcelonaSC.BarcelonaApp.eventbus.WallEvent;
import com.BarcelonaSC.BarcelonaApp.models.WallItem;
import com.BarcelonaSC.BarcelonaApp.models.WallReportarPost;
import com.BarcelonaSC.BarcelonaApp.ui.wall.ImageExpandDialog;
import com.BarcelonaSC.BarcelonaApp.ui.wall.WallFragment;
import com.BarcelonaSC.BarcelonaApp.ui.wall.comment.WallCommentFragment;
import com.BarcelonaSC.BarcelonaApp.ui.wall.list.di.DaggerWallComponent;
import com.BarcelonaSC.BarcelonaApp.ui.wall.list.di.WallModule;
import com.BarcelonaSC.BarcelonaApp.ui.wall.list.mvp.WallContract;
import com.BarcelonaSC.BarcelonaApp.ui.wall.list.mvp.WallPresenter;
import com.BarcelonaSC.BarcelonaApp.ui.wall.list.views.adapters.WallAdapter;
import com.BarcelonaSC.BarcelonaApp.ui.wall.post.WallCreatePostActivity;
import com.BarcelonaSC.BarcelonaApp.ui.wall.search.WallSearchFragment;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.EndlessScrollListener;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Leonardojpr on 1/9/18.
 */

public class WallFragmentList extends BaseFragment implements WallContract.View, WallAdapter.WallClickListener {

    public static final String TAG = WallFragment.class.getSimpleName();

    @BindView(R.id.content_header)
    RelativeLayout contentHeader;
    @BindView(R.id.rv_wall)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    EndlessScrollListener endlessScrollListener;

    private LinearLayoutManager mLayoutManager;
    private WallAdapter wallAdapter;
    private List<Object> itemList;

    private int postPosition = 0;
    private int videoPosition = 0;

    @Inject
    public WallPresenter presenter;

    public static WallFragmentList newInstance(String type, String id) {

        WallFragmentList fragment = new WallFragmentList();
        Bundle args = new Bundle();
        args.putString("type", type);
        args.putString("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        initComponent();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wall, container, false);
        ButterKnife.bind(this, view);
        initRecyclerView();
        return view;
    }

    public void initComponent() {
        DaggerWallComponent.builder()
                .appComponent(App.get().component())
                .wallModule(new WallModule(this))
                .build().inject(WallFragmentList.this);
    }

    public void initRecyclerView() {
        endlessScrollListener = null;
        mLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(mLayoutManager);

        itemList = new ArrayList<>();
        if (!getArguments().getString("type", "").equals("profile")) {
            itemList.add(SessionManager.getInstance().getUser());
        }

        wallAdapter = new WallAdapter(getContext(), itemList);

        wallAdapter.setWallClickListener(this);
        recyclerView.setAdapter(wallAdapter);
        if (!getArguments().getString("type", "").equals("profile")) {
            presenter.load();
        } else {
            contentHeader.setVisibility(View.VISIBLE);
            presenter.loadProfile(getArguments().getString("id"));
        }
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRecyclerView();
            }
        });
    }

    @OnClick(R.id.btn_back)
    public void finish() {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right)
                .remove(getFragmentManager().findFragmentByTag(WallCommentFragment.TAG))
                .show(getFragmentManager().findFragmentByTag(WallFragmentList.TAG))
                .commit();
    }

    @OnClick(R.id.btn_top)
    public void btnTop() {
        recyclerView.smoothScrollToPosition(0);
    }

    private EndlessScrollListener initRecyclerViewScroll() {
        if (endlessScrollListener == null) {
            endlessScrollListener = new EndlessScrollListener(mLayoutManager) {
                @Override
                public void onLoadMore(int current_page) {
                    if (!swipeRefreshLayout.isRefreshing()) {
                        if (!getArguments().getString("type", "").equals("profile")) {
                            presenter.loadPage(current_page);
                        } else {
                            presenter.loadPageProfile(current_page, getArguments().getString("id"));
                        }
                    }
                }
            };
        }
        return endlessScrollListener;
    }

    @Override
    public void setLoad(List<Object> list, boolean pagination) {
        if (pagination) {
            wallAdapter.addWall(list);
        } else {
            wallAdapter.initList(list);
        }
        recyclerView.addOnScrollListener(initRecyclerViewScroll());
        wallAdapter.showNoMoreDataToDisplay();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showProgress() {
        wallAdapter.showLoading();
    }

    @Override
    public void hideProgress() {
        wallAdapter.hideLoading();
    }

    @Override
    public void showToastError() {
        wallAdapter.showNoMoreDataToDisplay();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void reportarPost() {
        showDialogSuccessPost();
    }

    @Override
    public void onCreatePostListener() {
        // google Analytics Post tag
        App.get().registerTrackScreen(Constant.Analytics.WALL + "." + Constant.WallTags.Post);

        Intent intent = new Intent(getActivity(), WallCreatePostActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getActivity().startActivityForResult(intent, 2000);
    }

    @Override
    public void onEditPostListener(WallItem wallItem) {
        // google Analytics post Tag 
        App.get().registerTrackScreen(Constant.Analytics.WALL + "." + Constant.WallTags.Post);

        Intent intent = new Intent(getActivity(), WallCreatePostActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("wallitem", wallItem);
        getActivity().startActivityForResult(intent, 2000);
    }

    @Override
    public void onProfileListener() {
        // Google Analytics Profile Tag
        App.get().registerTrackScreen(Constant.Analytics.WALL + "." + Constant.WallTags.Profile);

        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .add(R.id.cal_container, WallFragmentList.newInstance("profile", SessionManager.getInstance().getUser().getId_usuario()), WallCommentFragment.TAG)
                .commitAllowingStateLoss();
    }

    @Override
    public void onDeletePost(String id_post, WallAdapter.RemoveListener removeListener) {
        showDialogDeletedPost(id_post, removeListener);
    }

    private void showDialogDeletedPost(final String id_post, final WallAdapter.RemoveListener removeListener) {
        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog_ideal_eleven_share, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialoglayout);
        final AlertDialog alertDialog = builder.show();
        FCMillonariosTextView fcMillonariosTextView = dialoglayout.findViewById(R.id.fcm_tv_tittle);
        fcMillonariosTextView.setText("¿Deseas eliminar esta publicación?");
        Button btnNot = (Button) dialoglayout.findViewById(R.id.btn_not);
        btnNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        Button btnYes = (Button) dialoglayout.findViewById(R.id.btn_yes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.delete(id_post);
                removeListener.onSuccessDeleted();
                alertDialog.dismiss();
            }
        });
    }

    private void showDialogReportarPost(final String id_post) {
        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog_reportar_muro, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialoglayout);
        final AlertDialog alertDialog = builder.show();
        RadioGroup radioGroup = dialoglayout.findViewById(R.id.radio_group);
        final RadioButton radioButtonOne = dialoglayout.findViewById(R.id.opcion_one);
        final RadioButton radioButtonDos = dialoglayout.findViewById(R.id.opcion_dos);
        final RadioButton radioButtonTres = dialoglayout.findViewById(R.id.opcion_tres);
        final RadioButton radioButtonCuatro = dialoglayout.findViewById(R.id.opcion_cuatro);


        // fcMillonariosTextView.setText("¿Deseas eliminar esta publicación?");
        Button btnNot = (Button) dialoglayout.findViewById(R.id.btn_not);
        btnNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        final String probando = "";
        Button btnYes = (Button) dialoglayout.findViewById(R.id.btn_yes);
        final int index = radioGroup.indexOfChild(dialoglayout.findViewById(radioGroup.getCheckedRadioButtonId()));
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = "";
                if (radioButtonOne.isChecked()) {
                    text = radioButtonOne.getText().toString();
                } else if (radioButtonDos.isChecked()) {
                    text = radioButtonDos.getText().toString();
                } else if (radioButtonTres.isChecked()) {
                    text = radioButtonTres.getText().toString();
                } else if (radioButtonCuatro.isChecked()) {
                    text = radioButtonCuatro.getText().toString();
                }

                if (!text.equals("")) {
                    presenter.sendReportarPost(new WallReportarPost(SessionManager.getInstance().getSession().getToken(), text, id_post, null));
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(getContext(), "Seleccione una opcioón", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showDialogSuccessPost() {
        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog_thanks_report, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialoglayout);
        final AlertDialog alertDialog = builder.show();
        FCMillonariosTextView fcMillonariosTextView = dialoglayout.findViewById(R.id.fcm_tv_tittle);
        fcMillonariosTextView.setText("Gracias \n por reportar esta publicación. \n\n Tus comentarios son de vital importancia para hacer de la App Oficial un lugar seguro.");
        Button btnYes = (Button) dialoglayout.findViewById(R.id.btn_ok);
        btnYes.setText("VOLVER");
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
            }
        });
    }

    @Override
    public void onShowProfileListener(String userItem) {
        if (!getArguments().getString("type").equals("profile")) {
            getFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                    .add(R.id.cal_container, WallFragmentList.newInstance("profile", userItem), WallCommentFragment.TAG)
                    .commitAllowingStateLoss();
        }
    }

    @Override

    public void onClickLikeListener(String id_post, WallAdapter.CommentListener commentListener) {
        presenter.clap(id_post, commentListener);
    }

    @Override
    public void onClickCommentListener(WallItem wallItem, int position) {
        // Google Analytics Comment Listener
        App.get().registerTrackScreen(Constant.Analytics.WALL + "." + Constant.WallTags.Comments);

        postPosition = position;

        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .add(R.id.cal_container, WallCommentFragment.newInstance(wallItem, false), WallCommentFragment.TAG)
                .commitAllowingStateLoss();
    }

    @Override
    public void onExpandImagen(Drawable drawable) {
        ImageExpandDialog imageExpandDialog = new ImageExpandDialog();
        imageExpandDialog.setImageToExpand(drawable);
        showDialogFragment(imageExpandDialog);
    }

    @Override
    public void onCickSearch() {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right)
                .add(R.id.cal_container, WallSearchFragment.newInstance(), WallSearchFragment.TAG)
                .commitAllowingStateLoss();
    }

    @Override
    public void onClickReportarPost(String id) {
        showDialogReportarPost(id);
    }

    public void onFullScreenVideo(String url, int position) {
        Intent intent = new Intent(getActivity(), VideoActivity.class);
        intent.putExtra(Constant.Video.CURRENT_POSITION, position);
        intent.putExtra(Constant.Video.PLAY, true);
        intent.putExtra(Constant.Video.URL, url);
        startActivity(intent);
    }

    @Override
    public void playVideo(int position) {
        videoPosition = position;
    }

    private void showDialogFragment(DialogFragment dialogFragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(dialogFragment, dialogFragment.getTag());
        ft.commitAllowingStateLoss();
    }

    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe
    public void onMessageEvent(WallCreatePostEvent event) {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initRecyclerView();
            }
        }, 0);

    }

    @Subscribe
    public void onMessageEvent(WallEvent event) {
        for (Map.Entry<String, Object> map : event.getAction().entrySet()) {
            if (map.getKey().equals(WallEvent.WALL_CREATE_COMMENT_POST)) {
                wallAdapter.updateCountComment((int) map.getValue(), postPosition);
            }
            if (map.getKey().equals(WallEvent.WALL_DELETE_COMMENT_POST)) {
                wallAdapter.removeCountComment((int) map.getValue(), postPosition);
            }
            if (map.getKey().equals(WallEvent.WALL_DELETE_POST)) {
                wallAdapter.deletePost(postPosition);
            }
            if (map.getKey().equals(WallEvent.WALL_LIKE_POST)) {
                wallAdapter.updateClapPost((int) map.getValue(), postPosition);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDetach();
    }

    @Override
    public void onPause() {
        super.onPause();
        wallAdapter.pauseVideo(videoPosition);
    }
}
