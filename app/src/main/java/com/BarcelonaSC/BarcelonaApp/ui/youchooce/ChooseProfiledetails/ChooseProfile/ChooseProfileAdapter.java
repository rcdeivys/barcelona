package com.BarcelonaSC.BarcelonaApp.ui.youchooce.ChooseProfiledetails.ChooseProfile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.models.ChooseProfileData;
import com.BarcelonaSC.BarcelonaApp.models.News;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final String TAG = ChooseProfileAdapter.class.getSimpleName();
    private static final String IN_PROGRESS = "En curso";
    private static final String FINALIZED = "Finalizado";
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    @BindView(R.id.player_img)
    ImageView playerImg;
    private Context context;
    private ChooseProfileData playerData;
    private OnItemClickListener onItemClickListener;
    private boolean haveHeader = false;
    private boolean showVotes = false;
    String type;

    public ChooseProfileAdapter(ChooseProfileFragment playerOffSummonedFragment) {
        this.context = playerOffSummonedFragment.getContext();
        playerData = new ChooseProfileData();
        //  playerData.setNoticias(new ArrayList<News>());
        onItemClickListener = playerOffSummonedFragment;
    }

    public ChooseProfileAdapter(ChooseProfileFragment playerOffSummonedFragment, String type) {
        this.context = playerOffSummonedFragment.getContext();
        this.type = type;
        playerData = new ChooseProfileData();
        onItemClickListener = playerOffSummonedFragment;
    }

    public void setData(ChooseProfileData playerData, boolean showVotes) {
        haveHeader = true;
        this.playerData = playerData;
        this.showVotes = showVotes;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position) && haveHeader) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return new VHHeader(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choose_profile_header, parent, false));
        } else {
            return new VHItem(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof VHHeader) {
            VHHeader vhHeader = (VHHeader) holder;
            initHeader(vhHeader);
        } else {
            VHItem vhItem = (VHItem) holder;
            initItem(vhItem, position);
        }
    }

    private void initHeader(VHHeader vhHeader) {

        Glide.with(context)
                .load(playerData.getBanner())
                .apply(new RequestOptions().placeholder(R.drawable.bsc_news_wm).error(R.drawable.bsc_news_wm))
                .into(vhHeader.playerImg);

        vhHeader.tvVotes.setText(String.valueOf(playerData.getVotos()));

        if (showVotes) {
            vhHeader.llVotes.setVisibility(View.VISIBLE);
        } else {
            vhHeader.llVotes.setVisibility(View.INVISIBLE);
        }

    }

    private void initItem(VHItem vhItem, final int position) {
        News recentItem = getItem(position);
        Glide.with(context)
                .load(recentItem.getFoto())
                .apply(new RequestOptions().placeholder(R.drawable.bsc_news_wm).error(R.drawable.bsc_news_wm))
                .into(vhItem.ivNews);
    /*    // Set play image
        if (recentItem.getTipo().matches(Constant.NewsType.VIDEO)) {
            vhItem.ivPlay.setImageResource(R.drawable.ic_play_circle_60dp);
        } else {
            vhItem.ivPlay.setImageDrawable(null);
        }
*/

        vhItem.tvData.setText(Commons.getStringDate(recentItem.getFecha()));
        //     vhItem.tvData.setTypeface(FontsUtil.getOpenSansReularFonts(context));
        vhItem.tvTitle.setText(recentItem.getTitulo());
        //  vhItem.tvTitle.setTypeface(FontsUtil.getHelveticaCondesed2Fonts(context));

        vhItem.llNewsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (haveHeader)
                    onItemClickListener.onClickItem(position - 1);
                else
                    onItemClickListener.onClickItem(position);
            }
        });

    }

    private News getItem(int position) {
        if (haveHeader)
            return playerData.getNoticias().get(position - 1);
        else
            return playerData.getNoticias().get(position);
    }

    @Override
    public int getItemCount() {
        if (haveHeader)
            return playerData.getNoticias().size() + 1;
        else
            return playerData.getNoticias().size();
    }

    public interface OnItemClickListener {
        void onClickItem(int position);

        void onClickHeader();

    }

    class VHItem extends RecyclerView.ViewHolder {

        @BindView(R.id.content_news_item)
        LinearLayout llNewsItem;
        @BindView(R.id.iv_news)
        ImageView ivNews;
        /*    @BindView(R.id.iv_play)
            ImageView ivPlay;*/
        @BindView(R.id.tv_date)
        TextView tvData;
        @BindView(R.id.tv_title)
        TextView tvTitle;


        VHItem(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class VHHeader extends RecyclerView.ViewHolder {

        @BindView(R.id.player_img)
        ImageView playerImg;
        @BindView(R.id.ll_votes)
        LinearLayout llVotes;
        @BindView(R.id.tv_votes)
        TextView tvVotes;


        VHHeader(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}