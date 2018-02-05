package com.millonarios.MillonariosFC.ui.home.menu.team.mossapplauded;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.app.App;
import com.millonarios.MillonariosFC.models.PlayersApplause;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Carlos on 14/10/2017.
 */

public class ApplauseAdapter extends RecyclerView.Adapter<ApplauseAdapter.ViewHolder> {


    private static final String TAG = ApplauseAdapter.class.getSimpleName();
    private Context context;
    private List<PlayersApplause> playersApplauses;

    private OnItemClickListener onItemClickListener;

    public ApplauseAdapter(ApplaudedFragment applaudedFragment) {
        this.context = applaudedFragment.getContext();


        onItemClickListener = applaudedFragment;
    }

    public void setData(List<PlayersApplause> playersApplauses) {
        this.playersApplauses = playersApplauses;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_applause, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // Custom typefaces

        PlayersApplause item = playersApplauses.get(position);

        holder.tvPosition.setText(String.valueOf(position + 1 )+ ".");

        Glide.with(context)
                .load(item.getFoto())
                .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta))
                .into(holder.civPlayerPhoto);

        String[] splitName = item.getNombre().split(" ");
        holder.tvName.setText(item.getNombre());

        holder.tvVotes.setText(item.getVotos() + " "+ App.get().getString(R.string.applause));

     /*   // Set statistics bar
        PercentRelativeLayout.LayoutParams params = (PercentRelativeLayout.LayoutParams) holder.bar.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo info = params.getPercentLayoutInfo();
        info.widthPercent = item.getPorcentaje() / 35;
        holder.bar.requestLayout();
*/
        holder.llApplause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onClickItem(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return playersApplauses.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.TV_Position)
        TextView tvPosition;
        @BindView(R.id.TV_Name)
        TextView tvName;
        @BindView(R.id.civ_player_photo)
        CircleImageView civPlayerPhoto;
        @BindView(R.id.TV_Votes)
        TextView tvVotes;
        @BindView(R.id.ll_applause)
        LinearLayout llApplause;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnItemClickListener {
        void onClickItem(int position);

    }

}
