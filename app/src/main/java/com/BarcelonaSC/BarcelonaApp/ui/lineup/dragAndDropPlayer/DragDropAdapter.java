package com.BarcelonaSC.BarcelonaApp.ui.lineup.dragAndDropPlayer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.utils.CircleDragDrop;
import com.BarcelonaSC.BarcelonaApp.models.NominaItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos on 14/11/2017.
 */

public class DragDropAdapter extends RecyclerView.Adapter<DragDropAdapter.TitularViewHolder> {

    List<NominaItem> titulars;


    DragDropAdapter(ArrayList<NominaItem> titulars) {
        this.titulars = titulars;
    }

    @Override
    public TitularViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_dragdrop_player, viewGroup, false);
        return new TitularViewHolder(v);
    }

    public void removePlayerFromList(NominaItem playerPlayByPlay) {
        titulars.remove(playerPlayByPlay);

    }

    @Override
    public void onBindViewHolder(final TitularViewHolder holder, int position) {

        NominaItem player = titulars.get(position);

        holder.tvName.setText(splitName(player.getNombre()));

        holder.civPlayerPhoto.setPlayerPlayByPlay(player);


        Glide.with(App.getAppContext())
                .load(player.getFoto())
                .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta))
                .into(holder.civPlayerPhoto);
    }

    private String splitName(String name) {

        String[] splitName = name.split(" ");

        String firstName = "";
        String lastName = "";

        firstName = splitName[0].charAt(0) + ". ";
        lastName = splitName[splitName.length - 1];
        return firstName + lastName;


    }

    @Override
    public int getItemCount() {
        return titulars.size();
    }

    public static class TitularViewHolder extends RecyclerView.ViewHolder {

        CircleDragDrop civPlayerPhoto;
        TextView tvName;

        TitularViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            civPlayerPhoto = (CircleDragDrop) itemView.findViewById(R.id.civ_player_photo);
        }
    }


}