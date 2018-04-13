package com.BarcelonaSC.BarcelonaApp.utils;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.ConfigurationManager;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager;
import com.BarcelonaSC.BarcelonaApp.app.network.NetworkCallBack;
import com.BarcelonaSC.BarcelonaApp.commons.WebViewActivity;
import com.BarcelonaSC.BarcelonaApp.models.DrawerItem;
import com.BarcelonaSC.BarcelonaApp.models.UserItem;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.models.response.ConfigurationResponse;
import com.BarcelonaSC.BarcelonaApp.ui.news.NewsInfografyActivity;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Erick
 * <p>
 * Class to manage side Menu
 */

public class SideMenu extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int HEADER = 1000;
    private static final int SOCIAL = 1001;
    private static final int MENU = 1002;

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
            case SOCIAL:
                return MenuSocialViewHolder.getInstance(parent);
            case MENU:
                return MenuItemViewHolder.getInstance(parent);
            default:
                return MenuHeaderViewHolder.getInstance(parent);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (ivSideMenuList.get(position) instanceof String) {
            if (position == 0) {
                return HEADER;
            } else if (position == 1) {
                return SOCIAL;
            }
        } else {
            return MENU;
        }
        return -1;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (ivSideMenuList.get(position) instanceof String) {
            if (position == 0) {
                ((MenuHeaderViewHolder) holder).setData(sessionManager.getUser());
                ((MenuHeaderViewHolder) holder).btnSetting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClickMenuListener.onClickMenuItem(Constant.Menu.Setting);
                    }
                });
                ((MenuHeaderViewHolder) holder).imgProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClickMenuListener.onClickMenuItem(Constant.Menu.PROFILE);
                    }
                });
                ((MenuHeaderViewHolder) holder).firstName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClickMenuListener.onClickMenuItem(Constant.Menu.PROFILE);
                    }
                });
            } else if (position == 1) {
                ((MenuSocialViewHolder) holder).setData();
                //((MenuSocialViewHolder) holder).hinchaCounter(String.valueOf(ConfigurationManager.getInstance().getConfiguration().getTotalHinchas()));
            }
        } else {
            ((MenuItemViewHolder) holder).setData((DrawerItem) ivSideMenuList.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickMenuListener.onClickMenuItem(((DrawerItem) ivSideMenuList.get(position)).getKey());
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

        private MenuHeaderViewHolder(View itemView) {
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

    public static class MenuSocialViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.btn_instagram)
        ImageView btnInstagram;
        @BindView(R.id.btn_facebook)
        ImageView btnFacebook;
        @BindView(R.id.btn_twitter)
        ImageView btnTwitter;
        @BindView(R.id.btn_youtube)
        ImageView btnYoutube;
        @BindView(R.id.tag_hincha_counter)
        LinearLayout layoutTagView;

        Context context;

        public static MenuSocialViewHolder getInstance(ViewGroup parent) {
            return new MenuSocialViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.side_menu_social, parent, false));
        }

        private MenuSocialViewHolder(View itemView) {
            super(itemView);
            this.context = itemView.getContext();
            ButterKnife.bind(this, itemView);
        }

        public void setData() {
            clickSocialButton(btnInstagram, "https://www.instagram.com/barcelonasc/");
            clickSocialButton(btnFacebook, "https://www.facebook.com/BarcelonaSCweb");
            clickSocialButton(btnTwitter, "https://twitter.com/BarcelonaSCweb");
            clickSocialButton(btnYoutube, "https://www.youtube.com/channel/UCgs5c9UJtczqmG7yOuvu5QA");
            hinchaCounter(String.valueOf(ConfigurationManager.getInstance().getConfiguration().getTotalHinchas()));
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            /*FirebaseManager.getInstance().getNumberHinchas(new FirebaseManager.FireValuesListener() {
                                @Override
                                public void onComplete(String value) {
                                    hinchaCounter(value);
                                }

                                @Override
                                public void onCanceled() {
                                    hinchaCounter("1");
                                }
                            });*/
                            App.get().component().configurationApi().getConfiguration().enqueue(new NetworkCallBack<ConfigurationResponse>() {
                                @Override
                                public void onRequestSuccess(ConfigurationResponse response) {
                                    hinchaCounter(String.valueOf(response.getData().getTotalHinchas()));
                                }

                                @Override
                                public void onRequestFail(String errorMessage, int errorCode) {
                                }
                            });
                            Log.i("HINCHACOUNTER"," ---> ACTUALIZANDO DATA COUNTER");
                        }
                    },
                    300000);
        }

        public void clickSocialButton(ImageView imageView, final String url) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Constant.Key.URL, url);
                    intent.putExtra("view", true);
                    context.startActivity(intent);
                }
            });
        }

        public void hinchaCounter(String number) {
            if(((LinearLayout) layoutTagView).getChildCount() > 0)
                ((LinearLayout) layoutTagView).removeAllViews();
            for (int i = 0; i <= number.toCharArray().length; i++) {
                View tagView = LayoutInflater.from(context).inflate(R.layout.item_hincha_counter, null, false);
                FCMillonariosTextView tagTextView = tagView.findViewById(R.id.tagTextView);
                if (i < number.toCharArray().length) {
                    tagTextView.setText(Character.toString(number.charAt(i)));
                    layoutTagView.addView(tagView);
                }
            }
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

        private MenuItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(DrawerItem data) {
            menuIcon.setImageResource(data.getIcon());

            menuTitle.setText(data.getTitle());
        }

    }

}