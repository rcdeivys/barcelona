package com.BarcelonaSC.BarcelonaApp.ui.chat.chatview;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Amigos;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Grupo;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Media;
import com.BarcelonaSC.BarcelonaApp.models.firebase.UsuarioGrupo;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatmodels.GroupModelView;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.adapter.FriendSelectedAdapter;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.adapter.GroupCommonsAdapter;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.adapter.MultimediaAdapter;
import com.BarcelonaSC.BarcelonaApp.ui.chat.groups.mvp.GroupsModel;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.CustomTextView;
import com.BarcelonaSC.BarcelonaApp.utils.PhotoUpload;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.theartofdev.edmodo.cropper.CropImage;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by cesar on 4/4/2018.
 */

public class ProfileUserFragment extends Fragment {

    private Unbinder unbinder;
    private View rootView;

    @BindView(R.id.civ_photo)
    CircleImageView civ_photo;

    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    @BindView(R.id.tv_apodo)
    CustomTextView tv_apodo;

    @BindView(R.id.tv_message_content)
    TextView tv_message_content;

    @BindView(R.id.tv_date_content)
    TextView tv_date_content;

    @BindView(R.id.tv_nro_hincha)
    CustomTextView tv_nro_hincha;

    @BindView(R.id.rv_photos_videos)
    RecyclerView rv_photos_videos;

    @BindView(R.id.rv_group_commons)
    RecyclerView rv_group_commons;


    public static final String TAG_GROUP = "group_chat";
    public static final String TAG_PRIVATE = "private_chat";
    private Grupo grupo = null;
    private Amigos amigo = null;
    private MultimediaAdapter multimediaAdapter;
    private String TAG = ProfileUserFragment.class.getSimpleName();
    private GridLayoutManager mGLayoutManager;
    private GroupCommonsAdapter groupCommonsAdapter;
    private FriendSelectedAdapter friendSelectedAdapter;
    private boolean isReady = false;

    public static ProfileUserFragment getInstance(Long idFriend) {
        ProfileUserFragment profileUserFragment = new ProfileUserFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TAG_PRIVATE, String.valueOf(idFriend));
        profileUserFragment.setArguments(bundle);
        return profileUserFragment;
    }

    public static ProfileUserFragment getInstance(String idGrupo) {
        ProfileUserFragment profileUserFragment = new ProfileUserFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TAG_GROUP, idGrupo);
        profileUserFragment.setArguments(bundle);
        return profileUserFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            if (getArguments().getString(TAG_PRIVATE) != null) {
                String idFriend = getArguments().getString(TAG_PRIVATE);
                for (Amigos amigo : FirebaseManager.getInstance().getUsuario().getAmigos()) {
                    if ((idFriend != null && amigo != null) && idFriend.equals(String.valueOf(amigo.getId()))) {
                        this.amigo = amigo;
                    }
                }

            } else if (getArguments().getString(TAG_GROUP) != null) {
                String idGrupo = getArguments().getString(TAG_GROUP);
                for (Grupo grupo : FirebaseManager.getInstance().getUsuario().getGrupos()) {
                    if ((idGrupo != null && grupo != null) && idGrupo.equals(String.valueOf(grupo.getKey()))) {
                        this.grupo = grupo;
                    }
                }
            }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isReady = false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view;

        if (grupo != null) {
            view = inflater.inflate(R.layout.fragment_profile_group_chat, container, false);
            rootView = view.findViewById(R.id.root_view);
            unbinder = ButterKnife.bind(this, view);
            initRecyclerParticipan();
            setvalueGrupos();
        } else {
            view = inflater.inflate(R.layout.fragment_profile_chat, container, false);
            rootView = view.findViewById(R.id.root_view);
            unbinder = ButterKnife.bind(this, view);
            initRecyclerGroupsCommons();
            setvalueAmigos();
        }

        setMultimedia();
        isReady = true;
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (!isReady)
                return;
            if (grupo != null) {
                setvalueGrupos();
            } else {
                setvalueAmigos();
            }
            setMultimedia();
        }

    }

    private void setvalueGrupos() {

        Glide.with(App.get())
                .load(grupo.getFoto())
                .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta))
                .into(civ_photo);
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTimeInMillis(grupo.getFecha_creacion());

        tv_apodo.setText(grupo.getNombre());
        try {
            tv_date_content.setText(calendar.get(Calendar.DAY_OF_MONTH) + " de " + getMonthForInt(calendar.get(Calendar.MONTH)) + " " + calendar.get(Calendar.YEAR));
        } catch (Exception e) {
            tv_date_content.setText("");
        }
        updateGroup(grupo.getFoto());
        civ_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editPhoto();
            }
        });
    }

    private void editPhoto() {
        if (CropImage.isExplicitCameraPermissionRequired(getContext())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE);
            }
        } else {
            CropImage.startPickImageActivity(getActivity());
        }
    }


    private void setMultimedia() {
        String idConversacion;
        boolean isGrupo = false;
        if (grupo != null) {
            idConversacion = grupo.getId_conversacion();
            isGrupo = true;
        } else {
            isGrupo = false;
            idConversacion = amigo.getId_conversacion();
        }
        FirebaseManager.getInstance().getMediaConversation(idConversacion, isGrupo, new com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseControllers.media.MediaController.OnGetMediaListener() {
            @Override
            public void success(List<Media> urlMedia) {
                if (urlMedia != null) {
                    Log.i(TAG, "--->size imagenes " + urlMedia.size());
                    initRecyclerMultimedia();
                    multimediaAdapter.updateAll(urlMedia);
                }
            }

            @Override
            public void failed() {

            }

        });
    }

    private void setvalueAmigos() {

        getGruposComunes();
        String foto = amigo.getConversacion().getMiembros().get(0).getFoto();
        if (foto == null || !URLUtil.isValidUrl(foto)) {
            civ_photo.setImageDrawable(ContextCompat.getDrawable(App.get(), R.drawable.silueta));
            civ_photo.setScaleType(ImageView.ScaleType.CENTER_CROP);
            civ_photo.setTag(R.string.accept, amigo.getId());
            PhotoUpload.uploadFoto(amigo.getId()
                    , foto, new PhotoUpload.PhotoListener() {
                        @Override
                        public void onPhotoSucces(String foto) {
                            amigo.getConversacion().getMiembros().get(0).setFoto(foto);
                            if (civ_photo.getTag(R.string.accept) == amigo.getId())
                                Glide.with(App.get())
                                        .load(foto)
                                        .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta).centerCrop())
                                        .into(civ_photo);
                        }

                        @Override
                        public void onError() {

                        }
                    });
        } else
            Glide.with(App.get())
                    .load(amigo.getConversacion().getMiembros().get(0).getFoto())
                    .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta).centerCrop())
                    .into(civ_photo);


        tv_apodo.setText(amigo.getConversacion().

                getMiembros().

                get(0).

                getNombre());
        String fecha = amigo.getConversacion().getMiembros().get(0).getCreated_at();
        try

        {
            tv_date_content.setText(Commons.simpleDateFormat(fecha).substring(0, 2) + " de " + getMonthForInt(Integer.parseInt(Commons.simpleDateFormat(fecha).substring(3, 5))) + " " + fecha.substring(0, 4));
        } catch (
                Exception e)

        {
            tv_date_content.setText("");
        }

        tv_nro_hincha.setText(String.valueOf(amigo.getConversacion().

                getMiembros().

                get(0).

                getId()));

    }

    String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols(new Locale("es", "ES"));
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 12) {
            month = months[num];
        }
        return month;
    }

    private void initRecyclerMultimedia() {

        rv_photos_videos.setLayoutManager(new GridLayoutManager(getContext(), 3));
        multimediaAdapter = new MultimediaAdapter(getContext(), new ArrayList<Media>());
        rv_photos_videos.setAdapter(multimediaAdapter);
    }

    private void initRecyclerGroupsCommons() {
        mGLayoutManager = new GridLayoutManager(getContext(), 4);
        rv_group_commons.setLayoutManager(mGLayoutManager);
        List<GroupModelView> groupModelViews = new ArrayList<>();
        groupCommonsAdapter = new GroupCommonsAdapter(groupModelViews, getContext());
        rv_group_commons.setAdapter(groupCommonsAdapter);
    }

    private void initRecyclerParticipan() {
        mGLayoutManager = new GridLayoutManager(getContext(), 4);
        rv_group_commons.setLayoutManager(mGLayoutManager);

        friendSelectedAdapter = new FriendSelectedAdapter(grupo.getConversacion().getMiembros(), getContext());
        rv_group_commons.setAdapter(friendSelectedAdapter);
        friendSelectedAdapter.notifyDataSetChanged();
    }


    public void getGruposComunes() {
        FirebaseManager.getInstance().getUserGroup(String.valueOf(amigo.getId()), new FirebaseManager.FireListener<UsuarioGrupo>() {
            @Override
            public void onDataChanged(UsuarioGrupo data) {
                groupCommonsAdapter.updateAll(ordenarGruposComunes(data.getGrupos()));
            }

            @Override
            public void onDataDelete(String id) {

            }

            @Override
            public void onCancelled() {

            }
        });
    }

    private List<GroupModelView> ordenarGruposComunes(List<String> amigosGrupos) {
        List<Grupo> misGruposList = FirebaseManager.getInstance().getUsuario().getGrupos();
        List<Grupo> gruposComunes = new ArrayList<>();


        for (int i = 0; i < amigosGrupos.size(); i++) {
            String id = amigosGrupos.get(i);
            Log.i("--->amigosGrupos key: ", id);
            for (int j = 0; j < misGruposList.size(); j++) {
                Log.i("--->misGruposList key: ", misGruposList.get(j).getKey());
                if (id.equals(misGruposList.get(j).getKey())) {
                    gruposComunes.add(misGruposList.get(j));
                }
            }
        }
        return GroupsModel.getAsGroupModelArrayLis(gruposComunes);
    }

    public void updateGroup(String photoUrl) {
        if (photoUrl != null && civ_photo != null) {
            Glide.with(App.get())
                    .load(photoUrl)
                    .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta))
                    .into(civ_photo);
            grupo.setFoto(photoUrl);
            if (getActivity() != null)
                ((ChatActivity) getActivity()).changedGroupImage(photoUrl);
        }
    }

    private void groupNewUpdate() {
        Intent resultIntent = new Intent();

        resultIntent.putExtra("foto", grupo.getFoto());
        getActivity().setResult(5, resultIntent);
    }

    public void showProgress() {
        Log.i(TAG, "--->showProgress");
        if (progressbar != null)
            progressbar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        Log.i(TAG, "--->showProgress");
        if (progressbar != null)
            progressbar.setVisibility(View.GONE);
    }
}
