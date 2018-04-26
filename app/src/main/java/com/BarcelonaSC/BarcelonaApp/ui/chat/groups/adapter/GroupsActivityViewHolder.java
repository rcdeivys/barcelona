package com.BarcelonaSC.BarcelonaApp.ui.chat.groups.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatmodels.GroupModelView;
import com.BarcelonaSC.BarcelonaApp.utils.CustomTextView;
import com.bumptech.glide.Glide;
import com.crashlytics.android.Crashlytics;

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
    CustomTextView nameGroup;

    @BindView(R.id.cant_members_group_h)
    CustomTextView cantMembersGroup;

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
        if (item.getNameGroup().length()>0) {
            if(howManyAreUpperCase(item.getNameGroup())>=6){
                nameGroup.setTextSize(12);
            }
            nameGroup.setText(item.getNameGroup());
        }else {
            nameGroup.setText(context.getText(R.string.group_no_name));
        }
        cantMembersGroup.setText(""+item.getQuantityGroup()+" "+context.getText(R.string.group_members));
    }

    private int howManyAreUpperCase(String name){
        int countUpperCase = 0;
        for(int i=0;i<name.length();i++){
            int val = (int)name.charAt(i);
            if(val >64 && val < 91){
                countUpperCase++;
            }
        }
        return countUpperCase;
    }


}

