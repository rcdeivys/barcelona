package com.millonarios.MillonariosFC.ui.chat.messages;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.app.App;
import com.millonarios.MillonariosFC.ui.chat.friends.FriendsModelView;
import com.millonarios.MillonariosFC.utils.Commons;
import com.millonarios.MillonariosFC.utils.FCMillonariosTextView;
import com.millonarios.MillonariosFC.utils.PhotoUpload;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static java.io.File.separator;

/**
 * Created by Carlos on 22/01/2018.
 */

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {


    private static final String TAG = MessagesAdapter.class.getSimpleName();

    private Context context;
    private List<MessageModelView> messageModelViews;
    private OnItemClickListener onItemClickListener;


    public MessagesAdapter(Context context, OnItemClickListener onItemClickListener, List<MessageModelView> messageModelViews) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
        this.messageModelViews = messageModelViews;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final MessageModelView messageModelView = messageModelViews.get(position);

        DateTime fireBaseDate = new DateTime(Long.valueOf(messageModelView.getTime()), DateTimeZone.UTC);
        DateTime now = new DateTime();
        Period period = new Period(fireBaseDate, now);
        String elapsed = getTimeWithFormat(period);

        Log.i(TAG, "--->conversion time"+ elapsed);

        holder.tvApodo.setText(messageModelView.getApodo());
        holder.tvTime.setText(elapsed);
        holder.tvMessageContent.setText(messageModelView.getContent());
        PhotoUpload.uploadFoto(messageModelView.getIdSender(), new PhotoUpload.PhotoListener() {
            @Override
            public void onPhotoSucces(String foto) {
                Glide.with(App.get()).load(foto)
                        .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta))
                        .into(holder.civPhoto);
            }

            @Override
            public void onError() {

            }
        });
        Glide.with(context)
                .load(messageModelView.getFoto())
                .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta))
                .into(holder.civPhoto);

        holder.civPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClickMessages(messageModelViews.get(position),MessagesConsts.TAG_ON_CLICK_VIEW_POPUP);
            }
        });

        holder.ivTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClickMessages(messageModelViews.get(position),MessagesConsts.TAG_ON_CLICK_VIEW_TRASH);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClickMessages(messageModelViews.get(position),MessagesConsts.TAG_ON_CLICK_ITEM_CONTENT);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onItemClickListener.onLongClickMessage(messageModelViews.get(position));
                return true;
            }
        });

        if(messageModelView.isPressed()) {
            holder.itemView.setBackground(Commons.getDrawable(R.drawable.divider_bottom_blue));
            holder.ivTrash.setVisibility(View.VISIBLE);
            holder.tvTime.setVisibility(View.INVISIBLE);
        }else{
            holder.tvTime.setVisibility(View.VISIBLE);
            holder.itemView.setBackground(Commons.getDrawable(R.drawable.divider_bottom_white));
            holder.ivTrash.setVisibility(View.INVISIBLE);
        }

        if(messageModelView.getStatus() == FriendsModelView.STATUS.ONLINE) {
            holder.civIsOnline.setImageResource(R.color.green_online);
        }else if(messageModelView.getStatus() == FriendsModelView.STATUS.OFFLINE){
            holder.civIsOnline.setImageResource(R.color.rojo_offline);
        }else if(messageModelView.getStatus() == FriendsModelView.STATUS.BUSY){
            holder.civIsOnline.setImageResource(R.color.background_yellow);
        }

        if(messageModelView.getAmigos().isBloqueado()){
            holder.itemView.setAlpha(Float.parseFloat("0.5"));
        }else{
            holder.itemView.setAlpha(Float.parseFloat("1"));
        }


    }

    public String getTimeWithFormat(Period period){
        PeriodFormatter formatter;
        String space = " ";
        if (period.getYears() != 0) {
            formatter = new PeriodFormatterBuilder()
                    .appendYears().appendSuffix(space).appendSuffix("year").appendSeparator(separator)
                    .printZeroNever()
                    .toFormatter();
        } else {
            if (period.getMonths() != 0){
                formatter = new PeriodFormatterBuilder()
                        .appendMonths().appendSuffix(space).appendSuffix("month").appendSeparator(separator)
                        .printZeroNever()
                        .toFormatter();
            } else {
                if (period.getDays() != 0){
                    formatter = new PeriodFormatterBuilder()
                            .appendDays().appendSuffix(space).appendSuffix("days").appendSeparator(separator)
                            .printZeroNever()
                            .toFormatter();
                } else {
                    if (period.getHours() != 0){
                        formatter = new PeriodFormatterBuilder()
                                .appendHours().appendSuffix(space).appendSuffix("hour").appendSeparator(separator)
                                .printZeroNever()
                                .toFormatter();
                    } else {
                        if (period.getMinutes() != 0){
                            formatter = new PeriodFormatterBuilder()
                                    .appendMinutes().appendSuffix(space).appendSuffix("min").appendSeparator(separator)
                                    .printZeroNever()
                                    .toFormatter();
                        } else {
                            formatter = new PeriodFormatterBuilder()
                                    .appendSeconds().appendSuffix(space).appendSuffix("sec").appendSeparator(separator)
                                    .printZeroNever()
                                    .toFormatter();
                        }
                    }
                }
            }
        }
        return formatter.print(period.normalizedStandard());
    }

    @Override
    public int getItemCount() {
        return messageModelViews.size();
    }

    public void updateAll(List<MessageModelView> update) {
        messageModelViews.clear();
        messageModelViews.addAll(messageModelViews.size(), update);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.civ_photo)
        CircleImageView civPhoto;
        @BindView(R.id.tv_apodo)
        FCMillonariosTextView tvApodo;
        @BindView(R.id.tv_time)
        FCMillonariosTextView tvTime;
        @BindView(R.id.tv_message_content)
        TextView tvMessageContent;
        @BindView(R.id.iv_trash)
        ImageView ivTrash;
        @BindView(R.id.civ_state)
        CircleImageView civIsOnline;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClickMessages(MessageModelView messageModelViews,String TAG);
        void onLongClickMessage(MessageModelView messageModelViews);
    }

}
