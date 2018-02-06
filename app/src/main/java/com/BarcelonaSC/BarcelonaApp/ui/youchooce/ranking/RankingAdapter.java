package com.BarcelonaSC.BarcelonaApp.ui.youchooce.ranking;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.models.RespuestaData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Carlos on 14/10/2017.
 */

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {


    private static final String TAG = RankingAdapter.class.getSimpleName();
    private Context context;
    private List<RespuestaData> mPlayersVotes;

    private OnItemClickListener onItemClickListener;

    public RankingAdapter(RankingFragment rankingFragment) {
        this.context = rankingFragment.getContext();


        onItemClickListener = rankingFragment;
    }

    public void setData(List<RespuestaData> mPlayersVotes) {
        this.mPlayersVotes = mPlayersVotes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ranking_votes, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // Custom typefaces

        final RespuestaData item = mPlayersVotes.get(position);

        holder.tvPosition.setText(String.valueOf(position + 1) + ".");

        Glide.with(context)
                .load(item.getMiniatura())
                .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta))
                .into(holder.civPlayerPhoto);


        holder.tvName.setText(item.getRespuesta());

        holder.tvVotes.setText(item.getVotos() + " " + App.get().getString(R.string.item_ranking_votes_votes));

     /*   // Set statistics bar
        PercentRelativeLayout.LayoutParams params = (PercentRelativeLayout.LayoutParams) holder.bar.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo info = params.getPercentLayoutInfo();
        info.widthPercent = item.getPorcentaje() / 35;
        holder.bar.requestLayout();
*/
        holder.llApplause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onClickItem(item.getIdrespuesta());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mPlayersVotes.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvPosition)
        TextView tvPosition;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.civPlayerPhoto)
        CircleImageView civPlayerPhoto;
        @BindView(R.id.tvVotes)
        TextView tvVotes;
        @BindView(R.id.llApplause)
        LinearLayout llApplause;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnItemClickListener {
        void onClickItem(int id);

    }

}
