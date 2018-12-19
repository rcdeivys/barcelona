package com.BarcelonaSC.BarcelonaApp.ui.youchooce.vote;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.models.RespuestaData;
import com.BarcelonaSC.BarcelonaApp.utils.SquareImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Carlos on 09/01/2018.
 */

public class VoteAdapter extends RecyclerView.Adapter<VoteAdapter.ViewHolder> {

    private static final String TAG = VoteAdapter.class.getSimpleName();
    private Context context;
    private List<RespuestaData> mRespuestasData;
    private boolean canVote = true;

    private OnItemClickListener onItemClickListener;

    public VoteAdapter(VoteFragment voteFragment) {
        this.context = voteFragment.getContext();

        onItemClickListener = voteFragment;
    }

    public void setData(List<RespuestaData> mRespuestasData,boolean canVote) {
        this.mRespuestasData = mRespuestasData;
        this.canVote = canVote;
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
                .apply(new RequestOptions().placeholder(R.drawable.logo_bsc).error(R.drawable.logo_bsc))
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
                if(canVote) {
                    if(item.getYaVoto() != null && !"1".equals(item.getYaVoto())){
                        onItemClickListener.onClickPlayerVote(position,1);
                    }else{
                        onItemClickListener.onClickPlayerVote(position,0);
                    }
                }
                else if(!canVote && (item.getYaVoto() != null && "1".equals(item.getYaVoto()))) {
                    onItemClickListener.onClickPlayerVote(position, 0);
                }else{
                    Toast.makeText(context, "Solo puedes votar una vez", Toast.LENGTH_SHORT).show();
                }
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

        void onClickPlayerVote(int position, int msj);
    }

}