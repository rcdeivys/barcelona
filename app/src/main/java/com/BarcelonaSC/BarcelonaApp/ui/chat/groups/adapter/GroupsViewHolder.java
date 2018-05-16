package com.BarcelonaSC.BarcelonaApp.ui.chat.groups.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatmodels.GroupModelView;
import com.BarcelonaSC.BarcelonaApp.utils.CustomTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.crashlytics.android.Crashlytics;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static java.io.File.separator;

/**
 * Created by Pedro Gomez on 17/01/2018.
 */

public class GroupsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_content)
    LinearLayout item_content;
    @BindView(R.id.civ_photo)
    CircleImageView civPhoto;
    @BindView(R.id.civ_state)
    CircleImageView civIsOnline;
    @BindView(R.id.tv_apodo)
    CustomTextView tvApodo;
    @BindView(R.id.tv_time)
    CustomTextView tvTime;
    @BindView(R.id.tv_message_content)
    TextView tvMessageContent;
    @BindView(R.id.iv_trash)
    ImageView ivTrash;
    private Context context;

    public static GroupsViewHolder getInstance(ViewGroup parent, Context context) {
        return new GroupsViewHolder(LayoutInflater.from(context).inflate(R.layout.item_chat_message, parent, false));
    }

    public GroupsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = itemView.getContext();
    }

    public void setData(GroupModelView item) {

        if (item.getFechaUltimoMensaje() != null) {
            DateTime fireBaseDate = new DateTime(item.getFechaUltimoMensaje());
            DateTime now = new DateTime();
            Period period = new Period(fireBaseDate, now);
            String elapsed = getTimeWithFormat(period);
            tvTime.setText(elapsed);
            tvTime.setVisibility(View.VISIBLE);
        } else {
            tvTime.setVisibility(View.GONE);
        }

        if (item.getUltimoMensaje() != null) {
            tvMessageContent.setText(item.getUltimoMensaje());
            tvMessageContent.setVisibility(View.VISIBLE);
        } else {
            tvMessageContent.setVisibility(View.GONE);
        }

        civIsOnline.setVisibility(View.GONE);
        ivTrash.setVisibility(View.GONE);
        civPhoto.setAlpha((float) 1.0);
        if (item.getImageGroup() != null && item.getImageGroup().length() > 0) {
            Crashlytics.log(Log.DEBUG, "IMAGEN", " ---> desde url");
            Glide.with(context)
                    .load(item.getImageGroup())
                    .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta))
                    .into(civPhoto);
        } else {
            Crashlytics.log(Log.DEBUG, "IMAGEN", " ---> desde drawable");
            Glide.with(context)
                    .load(R.drawable.silueta)
                    .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta))
                    .into(civPhoto);
        }
        if (item.getNameGroup() != null && item.getNameGroup().length() > 0) {
            if (howManyAreUpperCase(item.getNameGroup()) >= 6) {
                tvApodo.setTextSize(14);
            }
            tvApodo.setText(item.getNameGroup());
        } else {
            tvApodo.setText(context.getText(R.string.group_no_name));
        }

    }

    private int howManyAreUpperCase(String name) {
        int countUpperCase = 0;
        for (int i = 0; i < name.length(); i++) {
            int val = (int) name.charAt(i);
            if (val > 64 && val < 91) {
                countUpperCase++;
            }
        }
        return countUpperCase;
    }

    public String getTimeWithFormat(Period period) {
        PeriodFormatter formatter;
        String space = " ";
        if (period.getYears() != 0) {
            formatter = new PeriodFormatterBuilder()
                    .appendYears().appendSuffix(space).appendSuffix("año").appendSeparator(separator)
                    .printZeroNever()
                    .toFormatter();
        } else {
            if (period.getMonths() != 0) {
                formatter = new PeriodFormatterBuilder()
                        .appendMonths().appendSuffix(space).appendSuffix("mes").appendSeparator(separator)
                        .printZeroNever()
                        .toFormatter();
            } else {
                if (period.getDays() != 0) {
                    formatter = new PeriodFormatterBuilder()
                            .appendDays().appendSuffix(space).appendSuffix("días").appendSeparator(separator)
                            .printZeroNever()
                            .toFormatter();
                } else {
                    if (period.getHours() != 0) {
                        formatter = new PeriodFormatterBuilder()
                                .appendHours().appendSuffix(space).appendSuffix("horas").appendSeparator(separator)
                                .printZeroNever()
                                .toFormatter();
                    } else {
                        if (period.getMinutes() != 0) {
                            formatter = new PeriodFormatterBuilder()
                                    .appendMinutes().appendSuffix(space).appendSuffix("min").appendSeparator(separator)
                                    .printZeroNever()
                                    .toFormatter();
                        } else {
                            formatter = new PeriodFormatterBuilder()
                                    .appendSeconds().appendSuffix(space).appendSuffix("seg").appendSeparator(separator)
                                    .printZeroNever()
                                    .toFormatter();
                        }
                    }
                }
            }
        }
        return formatter.print(period.normalizedStandard());
    }


}
