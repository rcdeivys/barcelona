package com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.adapter;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.ChatActivity;
import com.BarcelonaSC.BarcelonaApp.ui.chat.messages.MessageModelView;
import com.BarcelonaSC.BarcelonaApp.ui.wall.ImageExpandDialog;

import java.util.List;

/**
 * Created by Pedro Gomez on 29/01/2018.
 */

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private List<MessageModelView> messageModelViews;
    private OnItemClickListener onItemClickListener;
    private boolean isgroup;

    public ChatAdapter(boolean isGroup, List<MessageModelView> messageModelViews, Context mContext) {
        this.mContext = mContext;
        this.isgroup = isGroup;
        this.messageModelViews = messageModelViews;
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 2)
            return ChatViewHolder.getInstance(parent, ChatViewHolder.TagMsg.TAG_NO_SELF);
        else
            return ChatViewHolder.getInstance(parent, ChatViewHolder.TagMsg.TAG_SELF);
    }

    @Override
    public int getItemViewType(int position) {
        MessageModelView message = messageModelViews.get(position);
        if (message.isMine()) {
            return 1;
        }
        return 2;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        configureChatViewHolder((ChatViewHolder) holder, position);
    }

    private void configureChatViewHolder(final ChatViewHolder holder, final int position) {
        boolean isNewUser;
        if (position == 0 || !(messageModelViews.get(position).getIdSender()
                .equals(messageModelViews.get(position - 1).getIdSender())))
            isNewUser = true;
        else
            isNewUser = false;
        holder.setData(messageModelViews.get(position), isgroup, isNewUser);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClickItem(messageModelViews.get(position));
            }
        });
        holder.imagenMsj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (messageModelViews.get(position).getTypeMsg() == FirebaseManager.MsgTypes.VIDEO)
                    onItemClickListener.onClickVideo(messageModelViews.get(position).getContent());
                else{
                    ImageExpandDialog imageExpandDialog = new ImageExpandDialog();
                    imageExpandDialog.setImageToExpand(holder.imagenMsj.getDrawable());
                    showDialogFragment(imageExpandDialog);/*
                    onItemClickListener.onClickViewImage(messageModelViews.get(position).getContent());*/
                }

            }
        });
    }


    private void showDialogFragment(DialogFragment dialogFragment) {
        FragmentTransaction ft = ((ChatActivity)mContext).getSupportFragmentManager().beginTransaction();
        ft.add(dialogFragment, dialogFragment.getTag());
        ft.commitAllowingStateLoss();
    }



    @Override
    public int getItemCount() {
        return messageModelViews.size();
    }

    public void updateAll(List<MessageModelView> update) {
        if (messageModelViews.size() > 0)
            messageModelViews.clear();
        messageModelViews.addAll(messageModelViews.size(), update);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClickItem(MessageModelView friend);

        void onClickViewImage(String url);

        void onClickVideo(String url);
    }
}
