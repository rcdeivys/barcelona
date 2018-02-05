package com.millonarios.MillonariosFC.ui.chat.messages;

import android.content.Intent;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.app.App;
import com.millonarios.MillonariosFC.app.manager.FirebaseManager;
import com.millonarios.MillonariosFC.commons.BaseEventBusFragment;
import com.millonarios.MillonariosFC.commons.BaseFragment;
import com.millonarios.MillonariosFC.models.firebase.Amigos;
import com.millonarios.MillonariosFC.models.firebase.FirebaseEvent;
import com.millonarios.MillonariosFC.ui.chat.chatview.ChatActivity;
import com.millonarios.MillonariosFC.ui.chat.friends.Dialogs.FriendsPopup;
import com.millonarios.MillonariosFC.ui.chat.friends.FriendsActivity;
import com.millonarios.MillonariosFC.ui.chat.friends.FriendsModelView;
import com.millonarios.MillonariosFC.ui.chat.messages.di.MessagesModule;
import com.millonarios.MillonariosFC.ui.chat.messages.mvp.MessagesContract;
import com.millonarios.MillonariosFC.ui.chat.messages.mvp.MessagesPresenter;
import com.millonarios.MillonariosFC.ui.chat.messages.di.DaggerMessagesComponent;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

/**
 * Created by Carlos on 22/01/2018.
 */

public class MessagesFragment extends BaseEventBusFragment implements MessagesContract.View, MessagesAdapter.OnItemClickListener, KeyboardView.OnAttachStateChangeListener, FriendsPopup.OnItemClickListener {

    public static final String TAG = MessagesFragment.class.getSimpleName();
    @BindView(R.id.rv_messages)
    RecyclerView rvMessages;

    @BindView(R.id.et_msg_search)
    EditText searchKey;

    @BindView(R.id.ib_new_msg)
    ImageButton msgNuevo;

    @BindView(R.id.iv_msg_search)
    ImageView searchMsg;

    @BindView(R.id.message_swipe)
    SwipeRefreshLayout swipeRefreshLayout;

    Unbinder unbinder;

    MessagesAdapter messagesAdapter;
    LinearLayoutManager linearLayoutManager;

    @Inject
    MessagesPresenter presenter;
    List<MessageModelView> listMessagesData;


    public static MessagesFragment newInstance() {
        Bundle args = new Bundle();
        MessagesFragment fragment = new MessagesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponent();
    }

    public void initComponent() {
        DaggerMessagesComponent.builder()
                .appComponent(App.get().component())
                .messagesModule(new MessagesModule(this))
                .build().inject(MessagesFragment.this);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        unbinder = ButterKnife.bind(this, view);
        initRecyclerView();
        presenter.onAttach(this);
        refresh();
        return view;
    }

    public void initRecyclerView() {
        messagesAdapter = new MessagesAdapter(getContext(), this, new ArrayList<MessageModelView>());
        linearLayoutManager = new LinearLayoutManager(getContext());
        rvMessages.setLayoutManager(linearLayoutManager);
        rvMessages.setAdapter(messagesAdapter);
        presenter.onAttach(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRecyclerView();
                refresh();
                //progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @OnClick(R.id.iv_msg_search)
    void onSearchMsg() {
        Crashlytics.log(Log.DEBUG, "AMIGO", "---> buscando " + searchKey.getText().toString());

        presenter.findByName(searchKey.getText().toString());
    }

    @OnClick(R.id.ib_new_msg)
    void onNewMsg() {
        //TODO: lunch a new activity with friends list
        if (presenter.haveFriends()) {
            Crashlytics.log(Log.DEBUG, "AMIGO", "---> Nuevo Mensaje ");
            startActivity(FriendsActivity.intent((ArrayList<Amigos>) FirebaseManager.getInstance().getUsuario().getAmigos(), getContext()));
        } else {
            Toast.makeText(getContext(), getText(R.string.add_friends_firts_send_msg), Toast.LENGTH_SHORT).show();
        }
    }

    @OnTextChanged(value = R.id.et_msg_search, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTextChange() {
        if (searchKey.getText().toString().length() > 0) {
            msgNuevo.setVisibility(View.GONE);
            presenter.findByName(searchKey.getText().toString());
        } else {
            msgNuevo.setVisibility(View.VISIBLE);
            refresh();
        }
    }

    @Override
    public void updateMessages(List<MessageModelView> messagesData) {
        if (messagesAdapter != null) {
            messagesAdapter.updateAll(messagesData);
            listMessagesData = messagesData;
            swipeRefreshLayout.setRefreshing(false);
            //progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void refresh() {
        presenter.loadMessages();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDetach();
        unbinder.unbind();
    }


    public void onClickMessages(MessageModelView messageModelView, String TAG) {

        Crashlytics.log(Log.DEBUG, "CHAT", " ---> INICIANDO");

        switch (TAG) {
            case MessagesConsts.TAG_ON_CLICK_ITEM_CONTENT: {
                if (isLongItemSelected()) {
                    clearAllListFalse();
                    messagesAdapter.notifyDataSetChanged();
                } else {
                    startActivity(ChatActivity.intent(messageModelView.getAmigos(), getContext()));
                }
                break;
            }
            case MessagesConsts.TAG_ON_CLICK_VIEW_POPUP: {
                FriendsPopup friendsPopup = new FriendsPopup();
                friendsPopup.setParams(new FriendsModelView(messageModelView.getAmigos().getId(),
                        messageModelView.getApodo(),
                        messageModelView.getFoto(),
                        messageModelView.getStatus(),
                        messageModelView.getAmigos().isBloqueado(),
                        messageModelView.getAmigos().getFecha_amistad(),
                        messageModelView.getAmigos().getId_conversacion(),
                        messageModelView.getApodo()), messageModelView.getAmigos(), this);
                showDialogFragment(friendsPopup);
                break;
            }
            case MessagesConsts.TAG_ON_CLICK_VIEW_TRASH: {
                presenter.removeConversation(messageModelView.getAmigos().getConversacion());
                break;
            }
        }
        messagesAdapter.notifyDataSetChanged();
    }

    private void showDialogFragment(DialogFragment dialogFragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(dialogFragment, dialogFragment.getTag());
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onLongClickMessage(MessageModelView messageModelView) {
        Log.i(TAG, "--->onLongClickItem: " + messageModelView.getApodo());
        if (listMessagesData == null)
            return;
        clearAllListFalse();
        for (int i = 0; i < listMessagesData.size(); i++) {
            if (listMessagesData.get(i).getIdSender().equals(messageModelView.getIdSender())) {
                if (listMessagesData.get(i).isPressed()) {
                    listMessagesData.get(i).setPressed(false);
                } else {
                    listMessagesData.get(i).setPressed(true);
                }
                break;
            }
        }
        messagesAdapter.notifyDataSetChanged();
    }

    public boolean isLongItemSelected() {
        for (int i = 0; i < listMessagesData.size(); i++) {
            if (listMessagesData.get(i).isPressed()) {
                return true;
            }
        }
        return false;
    }

    private void clearAllListFalse() {
        for (int i = 0; i < listMessagesData.size(); i++) {
            listMessagesData.get(i).setPressed(false);
        }
    }

    @Override
    public void onViewAttachedToWindow(View view) {
        Crashlytics.log(Log.DEBUG, "AMIGO", "---> TECLADO APARECIO");
    }

    @Override
    public void onViewDetachedFromWindow(View view) {
        Crashlytics.log(Log.DEBUG, "AMIGO", "---> TECLADO DESAPARECIO");
    }


    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe
    public void onMessageEvent(FirebaseEvent event) {
        Log.i("tag", "--->onMessageEvent update fragment");
        if (event.getEvent() == FirebaseEvent.EVENT.USER_CONVERSATION || event.getEvent() == FirebaseEvent.EVENT.REFRESCAR_AMIGOS) {
            presenter.loadMessages();
        }


    }

    @Override
    public void onClickItemPopup(String TAG) {

    }
}
