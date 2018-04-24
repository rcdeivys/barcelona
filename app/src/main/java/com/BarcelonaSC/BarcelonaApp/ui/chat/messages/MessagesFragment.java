package com.BarcelonaSC.BarcelonaApp.ui.chat.messages;

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

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager;
import com.BarcelonaSC.BarcelonaApp.commons.BaseEventBusFragment;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Amigos;
import com.BarcelonaSC.BarcelonaApp.models.firebase.FirebaseEvent;
import com.BarcelonaSC.BarcelonaApp.ui.chat.ChatConsts;
import com.BarcelonaSC.BarcelonaApp.ui.chat.ChatFragment;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.ChatActivity;
import com.BarcelonaSC.BarcelonaApp.ui.chat.messages.di.DaggerMessagesComponent;
import com.BarcelonaSC.BarcelonaApp.ui.chat.messages.di.MessagesModule;
import com.BarcelonaSC.BarcelonaApp.ui.chat.messages.mvp.MessagesContract;
import com.BarcelonaSC.BarcelonaApp.ui.chat.messages.mvp.MessagesPresenter;
import com.BarcelonaSC.BarcelonaApp.ui.chat.newconversation.NewConversationActivity;
import com.crashlytics.android.Crashlytics;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Carlos on 22/01/2018.
 */

public class MessagesFragment extends BaseEventBusFragment implements MessagesContract.View, MessagesAdapter.OnItemClickListener, KeyboardView.OnAttachStateChangeListener {

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
    @BindView(R.id.iv_more_conv)
    ImageView ivMoreConv;


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
        ivMoreConv.setVisibility(View.VISIBLE);
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
    public void onSearchMsg() {
        Crashlytics.log(Log.DEBUG, "AMIGO", "---> buscando " + searchKey.getText().toString());

        presenter.findByName(searchKey.getText().toString());
    }

    @OnClick(R.id.ib_new_msg)
    void onNewMsg() {
        //TODO: lunch a new activity with friends list
        if (presenter.haveFriends()) {
            Crashlytics.log(Log.DEBUG, "AMIGO", "---> Nuevo Mensaje ");
            startActivity(NewConversationActivity.intent((ArrayList<Amigos>) FirebaseManager.getInstance().getUsuario().getAmigos(), getContext()));
        } else {
            Toast.makeText(getContext(), getText(R.string.add_friends_firts_send_msg), Toast.LENGTH_SHORT).show();
        }
    }

    public void onTextChange(String text) {
        if (text.length() > 0) {
            msgNuevo.setVisibility(View.GONE);
            presenter.findByName(text);
        } else {
            msgNuevo.setVisibility(View.GONE);
            refresh();
        }
    }

    @Override
    public void updateMessages(List<MessageModelView> messagesData) {
        if (messagesAdapter != null) {
            messagesAdapter.updateAll(messagesData);
            listMessagesData = messagesData;
            swipeRefreshLayout.setRefreshing(false);
            messagesAdapter.notifyDataSetChanged();
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
            case ChatConsts.TAG_ON_CLICK_ITEM_CONTENT: {
                if (isLongItemSelected()) {
                    clearAllListFalse();
                    ((ChatFragment) getParentFragment()).setSuicheDelete(false);
                    messagesAdapter.notifyDataSetChanged();
                } else {
                    if (getActivity() != null)
                        getActivity().startActivity(ChatActivity.intent(messageModelView.getAmigos().getId(), getContext()));
                }
                break;
            }
            case ChatConsts.TAG_ON_CLICK_VIEW_POPUP: {
              /*  FriendsPopup friendsPopup = new FriendsPopup();
                friendsPopup.setParams(messageModelView, messageModelView.getAmigos(), this);
                showDialogFragment(friendsPopup);*/
                break;
            }
            case ChatConsts.TAG_ON_CLICK_VIEW_TRASH: {
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
        // clearAllListFalse();
        ((ChatFragment) getParentFragment()).setSuicheDelete(true);

        ((ChatFragment) getParentFragment()).onViewClickeddelete();

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

    public void removeConversation() {

        for (int i = 0; i < listMessagesData.size(); i++) {

            if (listMessagesData.get(i).isPressed()) {
                presenter.removeConversation(listMessagesData.get(i).getAmigos().getConversacion());
            }
        }
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

}
