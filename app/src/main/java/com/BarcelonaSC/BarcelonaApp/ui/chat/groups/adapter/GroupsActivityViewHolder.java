package com.BarcelonaSC.BarcelonaApp.ui.chat.groups.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.crashlytics.android.Crashlytics;
import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.ui.chat.groups.GroupModelView;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Pedro Gomez on 02/02/2018.
 */

public class GroupsActivityViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.img_group_h)
    CircleImageView imgGroup;

    @BindView(R.id.name_group_h)
    FCMillonariosTextView nameGroup;

    @BindView(R.id.cant_members_group_h)
    FCMillonariosTextView cantMembersGroup;

    private Context context;

    public static GroupsActivityViewHolder getInstance(ViewGroup parent, Context context) {
        return new GroupsActivityViewHolder(LayoutInflater.from(context).inflate(R.layout.item_group_horizontal, parent, false));
    }

    public GroupsActivityViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = itemView.getContext();
    }

    public void setData(GroupModelView item) {
        imgGroup.setAlpha((float) 1.0);
        if (item.getImageGroup().length()>0) {
            Crashlytics.log(Log.DEBUG,"IMAGEN"," ---> desde url");
            Glide.with(context)
                    .load(item.getImageGroup())
                    .into(imgGroup);
        }else{
            Crashlytics.log(Log.DEBUG,"IMAGEN"," ---> desde drawable");
            Glide.with(context)
                    .load(R.drawable.no_group_perfil)
                    .into(imgGroup);
        }
        if (item.getNameGroup().length()>0)
            nameGroup.setText(item.getNameGroup());
        else
            nameGroup.setText(context.getText(R.string.group_no_name));
        cantMembersGroup.setText(""+item.getQuantityGroup()+" "+context.getText(R.string.group_members));
    }

}

