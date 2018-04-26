package com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Amigos;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Miembro;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.ChatActivity;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.adapter.BlockUserAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cesar on 11/4/2018.
 */

public class Dialog_Users_block extends DialogFragment implements BlockUserAdapter.OnItemClickListener {

    @BindView(R.id.block_recycler_view)
    RecyclerView block_recycler_view;

    @BindView(R.id.btn_volver)
    Button btn_volver;

    private LinearLayoutManager mLayoutManager;
    List<Miembro> members;
    BlockUserAdapter blockUserAdapter;
    private String TAG = this.getClass().getSimpleName();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialogo = super.onCreateDialog(savedInstanceState);
        dialogo.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogo.setContentView(R.layout.dialog_users_block);
        ButterKnife.bind(this, dialogo);

        initRecyclerViewBlockUsers();

        return dialogo;
    }

    private void initRecyclerViewBlockUsers() {
        mLayoutManager = new LinearLayoutManager(getContext());
        block_recycler_view.setLayoutManager(mLayoutManager);
        members = new ArrayList<>();
        blockUserAdapter = new BlockUserAdapter(members, getContext());
        blockUserAdapter.setOnItemClickListener(this);
        block_recycler_view.setAdapter(blockUserAdapter);

        refresh();
    }

    public void refresh() {
        FirebaseManager.getInstance().getBlockUsers(new com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseControllers.friend.FriendControllers.BlockMemberListener() {
            @Override
            public void onBlockMemberDataChange(List<Miembro> member) {
                Log.i(TAG, "--->onBlockMemberDataChange" + member);
                if (member != null && member.size() > 0) {
                    Log.i(TAG, "--->updateALL" + member);
                    blockUserAdapter.updateAll(member);
                } else {
                    showMessage("No posee usuarios bloqueados");
                }
            }

            @Override
            public void onError() {
                Log.i(TAG, "--->onError get block users");
                showMessage("Error al obtener usuarios bloqueados");
            }
        });
    }

    @OnClick(R.id.btn_volver)
    public void volver() {
        this.dismissAllowingStateLoss();
    }

    private void showMessage(String message) {
        if (getActivity() != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClickFriend(Miembro miembro) {
        List<Amigos> ListAmigos = FirebaseManager.getInstance().getUsuario().getAmigos();
        for (Amigos amigoaux : ListAmigos) {
            if (amigoaux.getId().equals(miembro.getId())) {
                if (getActivity() != null)
                    getActivity().startActivity(ChatActivity.intent(amigoaux.getId(), getContext()));
                this.dismissAllowingStateLoss();
                break;
            }
        }
    }
}
