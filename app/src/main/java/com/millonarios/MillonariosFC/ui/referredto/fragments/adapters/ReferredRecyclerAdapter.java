package com.millonarios.MillonariosFC.ui.referredto.fragments.adapters;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.app.App;
import com.millonarios.MillonariosFC.utils.Commons;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by RYA-PC on 23/1/2018.
 */

public class ReferredRecyclerAdapter extends RecyclerView.Adapter<ReferredRecyclerAdapter.ReferredViewHolder> {

    public List<ReferredParser> referred;

    public int inactive = 0;

    StatusInactiveListener statusInactiveListener;

    Context context;

    public void setStatusInactiveListener(StatusInactiveListener statusInactiveListener) {
        this.statusInactiveListener = statusInactiveListener;
    }

    public ReferredRecyclerAdapter(Context context, List<ReferredParser> referred) {
        this.context = context;
        this.referred = referred;
    }

    @Override
    public ReferredRecyclerAdapter.ReferredViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReferredViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_referred_item, parent, false));

    }

    @Override
    public void onBindViewHolder(ReferredRecyclerAdapter.ReferredViewHolder holder, int position) {
        holder.setData(referred.get(position), position);
    }

    @Override
    public int getItemCount() {
        return referred.size();
    }

    public interface StatusInactiveListener {
        void onInactiveClickListener();
    }

    public class ReferredViewHolder extends RecyclerView.ViewHolder {

        private Context context;

        private ReferredViewHolder(View itemView) {
            super(itemView);
            this.context = itemView.getContext();
        }

        public void setData(final ReferredParser referredParser, int position) {
            RelativeLayout contentReffered = itemView.findViewById(R.id.content_referred);
            TextView tvPosition = itemView.findViewById(R.id.tv_position);
            CircleImageView ivImage = itemView.findViewById(R.id.iv_image);
            TextView tvName = itemView.findViewById(R.id.tv_name);
            TextView tvApodo = itemView.findViewById(R.id.tv_points);

            tvPosition.setText(String.valueOf(position + 1));
            if (!referredParser.getFoto().isEmpty()) {
                if (!referredParser.getFoto().equals("")) {
                    Glide.with(context)
                            .load(referredParser.getFoto())
                            .apply(new RequestOptions().error(R.drawable.silueta).placeholder(R.drawable.silueta))
                            .into(ivImage);
                } else {
                    ivImage.setImageResource(R.drawable.silueta);
                }
            } else {
                ivImage.setImageResource(R.drawable.silueta);
            }

            if (!referredParser.getStatus().toLowerCase().equals("activo")) {
                tvName.setTextColor(Commons.getColor(R.color.textColorPrimary));
                tvPosition.setTextColor(Commons.getColor(R.color.textColorPrimary));
            } else {
                tvName.setTextColor(Commons.getColor(R.color.white));
                tvPosition.setTextColor(Commons.getColor(R.color.white));
            }

            contentReffered.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!referredParser.getStatus().toLowerCase().equals("activo")) {
                        statusInactiveListener.onInactiveClickListener();
                    }
                }
            });

            String nombre = referredParser.getNombre() + " " + referredParser.getApellido();
            tvName.setText(nombre);
            tvApodo.setText(referredParser.getApodo());
        }
    }

}