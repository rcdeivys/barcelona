package com.BarcelonaSC.BarcelonaApp.ui.calendar.viewholder;

import android.util.Log;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

/**
 * Created by Erick on 01/11/2017.
 */

public class MatchParentViewHolder extends GroupViewHolder {
    private String TAG = MatchParentViewHolder.class.getSimpleName();
    String subseccion;
    public TextView tvTittle;
    public ImageView ivArrow;

    public MatchParentViewHolder(View itemView, String subseccion) {
        super(itemView);
        tvTittle = itemView.findViewById(R.id.parent_list_item_crime_title_text_view);
        ivArrow = itemView.findViewById(R.id.parent_list_item_expand_arrow);
    }

    public void setTvTittle(ExpandableGroup tvTittle) {
//        this.tvTittle.setText(tvTittle.getTitle());

        this.tvTittle.setText(tvTittle.getTitle());
        try{
            if(subseccion!=null && subseccion.equals(tvTittle.getTitle().toLowerCase())){
                Log.i(TAG, "setTvTittle: === subseccion: "+subseccion+" titulo: "+tvTittle.getTitle().toLowerCase());
                expand();
            }
        }catch (Exception e){

        }
    }

    @Override
    public void expand() {
        tvTittle.setTextColor(Commons.getColor(R.color.colorAccent));
        animateExpand();
    }

    @Override
    public void collapse() {
        tvTittle.setTextColor(Commons.getColor(R.color.colorUnactiveTextTab));
        animateCollapse();
    }

    private void animateExpand() {
        RotateAnimation rotate =
                new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        ivArrow.setAnimation(rotate);
    }

    private void animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        ivArrow.setAnimation(rotate);
    }
}