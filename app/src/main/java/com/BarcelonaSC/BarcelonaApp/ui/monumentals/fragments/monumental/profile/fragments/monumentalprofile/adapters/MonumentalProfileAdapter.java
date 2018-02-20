package com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.profile.fragments.monumentalprofile.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.models.MonumentalItem;
import com.BarcelonaSC.BarcelonaApp.models.News;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.profile.fragments.monumentalprofile.MProfileFragment;
import com.BarcelonaSC.BarcelonaApp.utils.FontsUtil;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MonumentalProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = MonumentalProfileAdapter.class.getSimpleName();
    private static final String IN_PROGRESS = "En curso";
    private static final String FINALIZED = "Finalizado";
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    @BindView(R.id.monumental_img)
    ImageView playerImg;
    private Context context;
    private MonumentalItem monumental;
    private OnItemClickListener onItemClickListener;
    private boolean haveHeader = false;

    SimpleDateFormat formatOut;
    SimpleDateFormat formatIn;

    public MonumentalProfileAdapter(MProfileFragment monumentalProfileFragment) {
        this.context = monumentalProfileFragment.getContext();
        formatOut = new SimpleDateFormat("yyyy-MM-dd");
        formatIn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public void setData(MonumentalItem monumental) {
        haveHeader = true;
        this.monumental = monumental;
        notifyDataSetChanged();
    }

    public void setVote() {
        int votes = monumental.getTotalVotos() + 1;
        monumental.setTotalVotos(votes);
        notifyDataSetChanged();
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
            return new VHHeader(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile_header, parent, false));
        } else {
            return new VHItem(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof VHHeader) {
            VHHeader vhHeader = (VHHeader) holder;
            if (monumental != null)
                initHeader(vhHeader, monumental);
        } else {
            VHItem vhItem = (VHItem) holder;
            initItem(vhItem, position);
        }
    }

    private void initHeader(VHHeader vhHeader, final MonumentalItem monumental) {
        vhHeader.votes.setText(String.valueOf(monumental.getTotalVotos()));
        Glide.with(context).load(monumental.getFoto())
                .apply(new RequestOptions().placeholder(R.drawable.bsc_news_wm).error(R.drawable.bsc_news_wm))
                .into(vhHeader.imgMonumental);
        vhHeader.contentVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onClickVote(monumental);
            }
        });

        vhHeader.monumental_name.setText(monumental.getNombre());
        vhHeader.monumental_name_container.setVisibility(View.GONE);
    }

    private void initItem(VHItem vhItem, final int position) {
        News recentItem = getItem(position);
        Glide.with(context)
                .load(recentItem.getFoto())
                .apply(new RequestOptions().placeholder(R.drawable.bsc_news_wm).error(R.drawable.bsc_news_wm))
                .into(vhItem.ivNews);
        // Set play image
        /*if (recentItem.getTipo().matches(Constant.NewsType.VIDEO)) {
            //vhItem.ivPlay.setImageResource(R.drawable.ic_play_circle_60dp);
            vhItem.ivPlay.setVisibility(View.VISIBLE);
        } else {
            //vhItem.ivPlay.setImageDrawable(null);
        }*/

        try {
            Date date1 = formatIn.parse(recentItem.getFecha());
            String formatOutStr = formatOut.format(date1);
            vhItem.tvData.setText(String.valueOf(formatOutStr));
            //System.out.println(formatOutStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //vhItem.tvData.setTypeface(FontsUtil.getOpenSansReularFonts(context));
        vhItem.tvTitle.setText(recentItem.getTitulo());
        //vhItem.tvTitle.setTypeface(FontsUtil.getHelveticaCondesed2Fonts(context));

        vhItem.llNewsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (haveHeader) {
                    onItemClickListener.onClickItem(monumental.getNewsList().get(position - 1));
                } else {
                    onItemClickListener.onClickItem(monumental.getNewsList().get(position));
                }
            }
        });
    }

    public void update(MonumentalItem monumental) {
        this.monumental = monumental;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (haveHeader)
            return monumental.getNewsList().size() + 1;
        else
            return monumental.getNewsList().size();
    }

    private News getItem(int position) {
        if (haveHeader)
            return monumental.getNewsList().get(position - 1);
        else
            return monumental.getNewsList().get(position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClickItem(News news);

        void onClickHeader();

        void onClickVote(MonumentalItem monumentalItem);
    }

    class VHItem extends RecyclerView.ViewHolder {
        @BindView(R.id.content_news_item)
        LinearLayout llNewsItem;
        @BindView(R.id.iv_news)
        ImageView ivNews;
        @BindView(R.id.iv_play)
        ImageView ivPlay;
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
        @BindView(R.id.monumental_name)
        TextView monumental_name;
        @BindView(R.id.monumental_name_container)
        LinearLayout monumental_name_container;
        @BindView(R.id.monumental_img)
        ImageView imgMonumental;
        @BindView(R.id.votes)
        TextView votes;
        @BindView(R.id.btn_vote)
        TextView btnVote;
        @BindView(R.id.content_vote)
        RelativeLayout contentVote;

        VHHeader(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}