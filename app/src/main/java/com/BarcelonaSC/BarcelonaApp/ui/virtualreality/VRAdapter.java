package com.BarcelonaSC.BarcelonaApp.ui.virtualreality;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.models.VideoReality;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

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
        VideoReality item = data.get(position);
        // Set news' image
        Glide.with(context)
                .load(item.getFoto())
                .apply(new RequestOptions().placeholder(R.drawable.millos_news_wm).error(R.drawable.millos_news_wm))
                .into(holder.ivVideo);

        // Custom typefaces
        Typeface agency_b = Typeface.createFromAsset(context.getAssets(), "fonts/Agencyb/AGENCYB.ttf");

        // Set texts and custom typefaces
        holder.tvTitle.setText(item.getTitulo());
        holder.tvTitle.setTypeface(agency_b);
        holder.llVrContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClickItem(position);
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
    /*
    // Activity context
    private Context context;
    // Json CalendarData list
    private ArrayList<VideoReality> data;
    // Screen's height
    private int height;

    public VRAdapter(Context context, ArrayList<VideoReality> data, int height) {
        super(context, R.layout.item_rv_videos, data);
        this.context = context;
        this.data = data;
        this.height = height;
    }

    @Override
    public @NonNull
    View getView(final int position, View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);

        final ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_rv_videos, parent, false);
            // Initialize ViewHolder
            holder = new ViewHolder();
            // Initialize items
            holder.IV_Video = (ImageView) convertView.findViewById(R.id.IV_Video);
            holder.TV_Title = (TextView) convertView.findViewById(R.id.TV_Title);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Get item
        VideoReality item = data.getPlayByPlay(position);
        // Set news' image
        Picasso.with(context)
                .load(item.getFoto())
                .into(holder.IV_Video, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        holder.IV_Video.setAlpha((float)1.0);

                    }

                    @Override
                    public void onError() {
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                        params.addRule(RelativeLayout.CENTER_IN_PARENT);
                        params.height = height*32/100;
                        holder.IV_Video.setLayoutParams(params);
                        holder.IV_Video.setImageResource(R.drawable.millos_news_wm);
                        holder.IV_Video.setAlpha((float)0.4);
                    }
                });

        // Custom typefaces
        Typeface agency_b = Typeface.createFromAsset(context.getAssets(), "fonts/Agencyb/AGENCYB.ttf");

        // Set texts and custom typefaces
        holder.TV_Title.setText(item.getTitulo());
        holder.TV_Title.setTypeface(agency_b);

        return convertView;
    }

    private class ViewHolder {
        private ImageView IV_Video;
        private TextView TV_Title;
    }*/