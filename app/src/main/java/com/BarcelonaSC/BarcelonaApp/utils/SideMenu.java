package com.BarcelonaSC.BarcelonaApp.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.ConfigurationManager;
import com.BarcelonaSC.BarcelonaApp.models.DrawerItem;
import com.BarcelonaSC.BarcelonaApp.models.UserItem;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Gianni
 * <p>
 * Class to manage side Menu
 */

public class SideMenu extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int HEADER = 1000;
    private static final int MENU = 1001;

    private List<Object> ivSideMenuList;
    private OnClickMenuListener onClickMenuListener;

    public SessionManager sessionManager;

    public SideMenu(List<Object> ivSideMenuList) {
        this.ivSideMenuList = ivSideMenuList;
        sessionManager = new SessionManager(App.get().getBaseContext());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case HEADER:
                return MenuHeaderViewHolder.getInstance(parent);
            case MENU:
                return MenuItemViewHolder.getInstance(parent);
            default:
                return MenuHeaderViewHolder.getInstance(parent);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (ivSideMenuList.get(position) instanceof String) {
            return HEADER;
        } else {
            return MENU;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MenuItemViewHolder) {
            ((MenuItemViewHolder) holder).setData((DrawerItem) ivSideMenuList.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickMenuListener.onClickMenuItem(((DrawerItem) ivSideMenuList.get(position)).getKey());
                }
            });
        } else {
            ((MenuHeaderViewHolder) holder).setData(sessionManager.getUser());
            ((MenuHeaderViewHolder) holder).btnSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickMenuListener.onClickMenuItem(Constant.Menu.Setting);
                }
            });
            ((MenuHeaderViewHolder) holder).btnProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickMenuListener.onClickMenuItem(Constant.Menu.PROFILE);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return ivSideMenuList.size();
    }

    public void setOnClickMenuListener(OnClickMenuListener onClickMenuListener) {
        this.onClickMenuListener = onClickMenuListener;
    }

    public interface OnClickMenuListener {

        void onClickMenuItem(String fragment);
    }


    public static class MenuHeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.first_name)
        FCMillonariosTextView firstName;
        @BindView(R.id.last_name)
        FCMillonariosTextView lastName;
        @BindView(R.id.btn_profile)
        LinearLayout btnProfile;
        @BindView(R.id.btn_setting)
        ImageView btnSetting;
        @BindView(R.id.text_profile)
        FCMillonariosTextView textProfile;
        @BindView(R.id.img_perfil)
        CircleImageView imgProfile;

        Context context;

        public static MenuHeaderViewHolder getInstance(ViewGroup parent) {
            return new MenuHeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.side_menu_header, parent, false));
        }

        public MenuHeaderViewHolder(View itemView) {
            super(itemView);
            this.context = itemView.getContext();
            ButterKnife.bind(this, itemView);
        }

        public void setData(UserItem userItem) {
            textProfile.setText(ConfigurationManager.getInstance().getConfiguration().getTit1());
            firstName.setText(userItem.getNombre());
            lastName.setText(userItem.getApellido());
            Glide.with(context).load(userItem.getFoto()).apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta)).into(imgProfile);
        }
    }

    public static class MenuItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.menu_icon)
        ImageView menuIcon;
        @BindView(R.id.menu_title)
        FCMillonariosTextView menuTitle;

        public static MenuItemViewHolder getInstance(ViewGroup parent) {
            return new MenuItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false));
        }

        public MenuItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(DrawerItem data) {
            menuIcon.setImageResource(data.getIcon());

            menuTitle.setText(data.getTitle());
        }

    }


}
