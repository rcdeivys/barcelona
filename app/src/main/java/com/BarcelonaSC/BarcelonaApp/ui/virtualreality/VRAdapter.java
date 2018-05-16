package com.BarcelonaSC.BarcelonaApp.ui.virtualreality;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.models.VideoReality;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;
import com.BarcelonaSC.BarcelonaApp.utils.ShareSection;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.inthecheesefactory.thecheeselibrary.widget.AdjustableImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Gianni on 25/07/17.
 * <p>
 * Class to manage videos' ListView adapter
 */
public class VRAdapter extends RecyclerView.Adapter<VRAdapter.VRHolder> {

    private static final String TAG = VRAdapter.class.getSimpleName();

    private OnItemClickListener onItemClickListener;
    private ArrayList<VideoReality> data;
    private Context context;

    public interface OnItemClickListener {
        void onClickItem(int position);
    }

    public VRAdapter(VRFragment vrFragment) {
        this.context = vrFragment.getContext();
        data = new ArrayList<>();
        onItemClickListener = vrFragment;
    }

    public void setData(ArrayList<VideoReality> data) {
        this.data = data;
    }

    @Override
    public VRHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VRHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_videos, parent, false));
    }

    @Override
    public void onBindViewHolder(VRHolder holder, final int position) {

        // Get item
        final VideoReality item = data.get(position);
        // Set news' image
        Glide.with(context)
                .load(item.getFoto())
                .apply(new RequestOptions().placeholder(R.drawable.bsc_news_wm).error(R.drawable.bsc_news_wm))
                .into(holder.ivVideo);

        // Custom typefaces
        Typeface agency_b = Typeface.createFromAsset(context.getAssets(), "fonts/Agencyb/AGENCYB.ttf");

        // Set texts and custom typefaces
        holder.tvTitle.setText(item.getTitulo());
        holder.tvTitle.setTypeface(agency_b);
        holder.playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClickItem(position);
            }
        });
        holder.ivShare.setVisibility(View.GONE);
        holder.ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareSection.shareIndividual(Constant.Key.SHARE_VR, item.getId());
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    class VRHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_video)
        ImageView ivVideo;

        @BindView(R.id.iv_play)
        AdjustableImageView playBtn;

        @BindView(R.id.iv_share)
        AppCompatImageView ivShare;

        @BindView(R.id.tv_title)
        FCMillonariosTextView tvTitle;

        @BindView(R.id.tv_date)
        FCMillonariosTextView tvDate;

        @BindView(R.id.ll_vr_container)
        LinearLayout llVrContainer;

        VRHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}