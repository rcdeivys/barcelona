package com.BarcelonaSC.BarcelonaApp.ui.chat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.BarcelonaSC.BarcelonaApp.commons.BaseFragment;
import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager;
import com.BarcelonaSC.BarcelonaApp.commons.BaseActivity;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Amigos;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Grupo;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.Dialogs.Dialog_Users_block;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.Dialogs.Dialog_add_group;
import com.BarcelonaSC.BarcelonaApp.ui.chat.creategroup.CreateGroupActivity;
import com.BarcelonaSC.BarcelonaApp.ui.chat.newconversation.NewConversationActivity;
import com.BarcelonaSC.BarcelonaApp.utils.BannerView;
import com.BarcelonaSC.BarcelonaApp.utils.CustomTabLayout;
import com.BarcelonaSC.BarcelonaApp.utils.CustomViewPager;
import com.BarcelonaSC.BarcelonaApp.utils.SquareHeightImageView;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

/**
 * Created by Carlos on 22/01/2018.
 */

public class ChatFragment extends BaseFragment implements Dialog_add_group.OnItemClickListenerDialog {

    public static final String TAG = ChatFragment.class.getSimpleName();
    @BindView(R.id.tabs)
    CustomTabLayout tabs;
    @BindView(R.id.pager)
    CustomViewPager pager;
    Unbinder unbinder;
    @BindView(R.id.iv_more_conv)
    ImageView ivMoreConv;
    @BindView(R.id.et_msg_search)
    EditText etMsgSearch;
    int position = 0;
    @BindView(R.id.sqiv_option)
    SquareHeightImageView sqivOption;
    private int[] navIcons = {
            R.drawable.tab_icon_messages,
            R.drawable.tab_icon_group,
            R.drawable.tab_icon_friend,
            R.drawable.tab_icon_request

    };
    private boolean suicheDelete = false;


    private ChatViewPagerAdapter viewPagerAdapter;

    public static ChatFragment newInstance() {

        Bundle args = new Bundle();

        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            if (getActivity() instanceof BaseActivity)
                ((BaseActivity) getActivity()).initBanner(BannerView.Seccion.CHAT);
        }
    }

    public void setSuicheDelete(boolean suicheDelete) {
        if (suicheDelete)
            sqivOption.setVisibility(View.VISIBLE);
        else
            sqivOption.setVisibility(View.VISIBLE);
        this.suicheDelete = suicheDelete;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        unbinder = ButterKnife.bind(this, view);
        setSuicheDelete(false);
        initializeViewPager();

        return view;

    }

    @OnTextChanged(value = R.id.et_msg_search, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTextChange() {
        if (etMsgSearch == null || viewPagerAdapter == null || viewPagerAdapter.messagesFragment == null
                || viewPagerAdapter.groupsFragment == null)
            return;
        if (position == 0)
            viewPagerAdapter.messagesFragment.onTextChange(etMsgSearch.getText().toString());
        else
            viewPagerAdapter.groupsFragment.onTextChange(etMsgSearch.getText().toString());
    }

    @SuppressLint("RestrictedApi")
    public void onPopupButtonClick(View button) {
        Context wrapper = new ContextThemeWrapper(getActivity(), R.style.PopUpChat);
        PopupMenu popup = new PopupMenu(wrapper, button);
        popup.getMenuInflater().inflate(R.menu.menu_chat, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menu_conv: {
                        Crashlytics.log(Log.DEBUG, "AMIGO", "---> Nuevo Mensaje ");
                        startActivity(NewConversationActivity.intent((ArrayList<Amigos>) FirebaseManager.getInstance().getUsuario().getAmigos(), getContext()));

                        break;
                    }
                    case R.id.menu_group: {
                        Intent intent = new Intent(getActivity(), CreateGroupActivity.class);
                        startActivity(intent);
                        break;
                    }
                }
                return false;
            }
        });

        final MenuPopupHelper menuHelper = new MenuPopupHelper(wrapper, (MenuBuilder) popup.getMenu(), button);
        menuHelper.setForceShowIcon(true);
        menuHelper.show();
    }

    @SuppressLint("RestrictedApi")
    public void onPopupButtonDeleteClick(View button) {
        Context wrapper = new ContextThemeWrapper(getActivity(), R.style.PopUpChat);
        PopupMenu popup = new PopupMenu(wrapper, button);
        popup.getMenuInflater().inflate(R.menu.menu_chat_delete, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menu_trash: {
                        viewPagerAdapter.messagesFragment.removeConversation();
                        break;
                    }

                }
                return false;
            }
        });

        MenuPopupHelper menuHelper = new MenuPopupHelper(wrapper, (MenuBuilder) popup.getMenu(), button);
        menuHelper.setForceShowIcon(true);
        menuHelper.show();
    }

    @SuppressLint("RestrictedApi")
    public void onPopupButtonMenuClick(View button) {
        Context wrapper = new ContextThemeWrapper(getActivity(), R.style.PopUpChat);
        PopupMenu popup = new PopupMenu(wrapper, button);
        popup.getMenuInflater().inflate(R.menu.menu_chat_block, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menu_block: {
                        Dialog_Users_block dialog_add_group = new Dialog_Users_block();
                        showDialogFragment(dialog_add_group);
                        break;
                    }

                }
                return false;
            }
        });

        MenuPopupHelper menuHelper = new MenuPopupHelper(wrapper, (MenuBuilder) popup.getMenu(), button);
        menuHelper.setForceShowIcon(true);
        menuHelper.show();
    }

    private void showDialogFragment(DialogFragment dialogFragment) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.add(dialogFragment, dialogFragment.getTag());
        ft.commitAllowingStateLoss();
    }

    private void initializeViewPager() {

        int Numboftabs = 2;

        String[] titles = {""};

        viewPagerAdapter = new ChatViewPagerAdapter(getChildFragmentManager(), titles, Numboftabs);

        pager.setPagingEnabled(true);
        pager.setAdapter(viewPagerAdapter);
        tabs.setupWithViewPager(pager);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 1)
                    setSuicheDelete(false);
                position = tab.getPosition();
                etMsgSearch.setText("");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        for (int i = 0; i < tabs.getTabCount(); i++) {

            LinearLayout tab = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.item_tab_layout_chat, null);

            ImageView tab_icon = (ImageView) tab.findViewById(R.id.nav_icon);

            tab_icon.setImageResource(navIcons[i]);

            tabs.getTabAt(i).setCustomView(tab);
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.iv_more_conv)
    public void onViewClicked() {
        onPopupButtonClick(ivMoreConv);
    }

    @OnClick(R.id.sqiv_option)
    public void onViewClickeddelete() {
        if (suicheDelete)
            onPopupButtonDeleteClick(sqivOption);
        else
            onPopupButtonMenuClick(sqivOption);
    }

    @Override
    public void onClickDialogAddGroup(List<Amigos> friends, List<Grupo> grupos, String TAG) {

    }
}

