package com.millonarios.MillonariosFC.ui.youchooce.vote;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.app.App;
import com.millonarios.MillonariosFC.models.PlayerVote;
import com.millonarios.MillonariosFC.models.RespuestaData;
import com.millonarios.MillonariosFC.ui.youchooce.ranking.RankingFragment;
import com.millonarios.MillonariosFC.utils.SquareImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Carlos on 09/01/2018.
 */

public class VoteAdapter extends RecyclerView.Adapter<VoteAdapter.ViewHolder> {


    private static final String TAG = VoteAdapter.class.getSimpleName();
    private Context context;
    private List<RespuestaData> mRespuestasData;

    private OnItemClickListener onItemClickListener;

    public VoteAdapter(VoteFragment voteFragment) {
        this.context = voteFragment.getContext();


        onItemClickListener = voteFragment;
    }

    public void setData(List<RespuestaData> mRespuestasData) {
        this.mRespuestasData = mRespuestasData;
    }

    @Override
    public VoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choose_vote, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // Custom typefaces

        final RespuestaData item = mRespuestasData.get(position);

        //   holder.tvPosition.setText(String.valueOf(position + 1) + ".");

        Glide.with(context)
                .load(item.getFoto())
                .apply(new RequestOptions().placeholder(R.drawable.logo_millos).error(R.drawable.logo_millos))
                .into(holder.sivChoose);

        if (item.getYaVoto() != null && "1".equals(item.getYaVoto())) {
            holder.ibVote.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.btn_on));

        } else {
            holder.ibVote.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.btn_off));
        }

    /*     String[] splitName = item.getNombre().split(" ");
        holder.tvName.setText(item.getNombre());

        holder.tvVotes.setText(item.getVotos() + " " + App.get().getString(R.string.applause));

     *//*   // Set statistics bar
        PercentRelativeLayout.LayoutParams params = (PercentRelativeLayout.LayoutParams) holder.bar.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo info = params.getPercentLayoutInfo();
        info.widthPercent = item.getPorcentaje() / 35;
        holder.bar.requestLayout();
*//*
        holder.llApplause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onClickItem(position);
            }
        });*/
        if (item.getRespuesta() != null)
            holder.tvName.setText(item.getRespuesta().toUpperCase());
        holder.sivChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClickItem(item.getIdrespuesta());
            }
        });


        holder.ibVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClickPlayerVote(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRespuestasData.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        /*   @BindView(R.id.tvPosition)
           TextView tvPosition;
           @BindView(R.id.tvName)
           TextView tvName;
           @BindView(R.id.civPlayerPhoto)
           CircleImageView civPlayerPhoto;

           @BindView(R.id.llApplause)
           LinearLayout llApplause;
   */
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.sivChoose)
        SquareImageView sivChoose;

        @BindView(R.id.ibVote)
        ImageView ibVote;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnItemClickListener {
        void onClickItem(int id);

        void onClickPlayerVote(int position);

    }

}
