package com.BarcelonaSC.BarcelonaApp.ui.hinchadorado.cancelation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.models.ReasonData;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by quint on 2/24/2018.
 */

public class CancelationAdapter extends RecyclerView.Adapter<CancelationAdapter.CancelationViewHolder> {

    List<ReasonData> reasonData;
    Context context;
    CheckBox lastCheckBox;
    ReasonData lastreasonData;

    CancelationAdapter(Context context) {
        this.context = context;
    }

    @Override
    public CancelationViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_reason_cancelation, viewGroup, false);
        return new CancelationViewHolder(v);
    }

    public void setData(List<ReasonData> reasonData) {
        this.reasonData = reasonData;
    }


    @Override
    public void onBindViewHolder(final CancelationViewHolder holder, int position) {

        final ReasonData reason = reasonData.get(position);

        holder.tvReason.setText(reason.getDescripcion());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (lastCheckBox != holder.checkBox) {
                    if (lastCheckBox != null) {
                        lastCheckBox.setChecked(false);
                        lastreasonData.setCheck(false);
                    }
                    lastreasonData = reason;
                    lastCheckBox = holder.checkBox;
                }
                reason.setCheck(b);

            }
        });
        holder.rlReasonContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.checkBox.setChecked(!holder.checkBox.isChecked());
            }
        });
    }


    @Override
    public int getItemCount() {
        return reasonData.size();
    }

    public static class CancelationViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_reason)
        FCMillonariosTextView tvReason;
        @BindView(R.id.checkBox)
        CheckBox checkBox;
        @BindView(R.id.rl_reason_container)
        RelativeLayout rlReasonContainer;

        CancelationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }
}
